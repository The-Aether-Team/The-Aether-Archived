package com.gildedgames.the_aether.items.tools;

import java.util.List;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.effects.EffectInebriation;
import com.gildedgames.the_aether.entities.effects.PotionInebriation;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.util.EnumSkyrootBucketType;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSkyrootBucket extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon waterBucket;

	@SideOnly(Side.CLIENT)
	private IIcon poisonBucket;

	@SideOnly(Side.CLIENT)
	private IIcon remedyBucket;

	@SideOnly(Side.CLIENT)
	private IIcon milkBucket;

	public ItemSkyrootBucket() {
		super();

		this.setHasSubtypes(true);
		this.setContainerItem(this);
		this.setCreativeTab(AetherCreativeTabs.misc);
		this.setTextureName(Aether.find("misc/buckets/skyroot_bucket"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		super.registerIcons(registry);

		this.waterBucket = registry.registerIcon(Aether.find("misc/buckets/skyroot_water_bucket"));
		this.poisonBucket = registry.registerIcon(Aether.find("misc/buckets/skyroot_poison_bucket"));
		this.remedyBucket = registry.registerIcon(Aether.find("misc/buckets/skyroot_remedy_bucket"));
		this.milkBucket = registry.registerIcon(Aether.find("misc/buckets/skyroot_milk_bucket"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return meta == 1 ? this.waterBucket : meta == 2 ? this.poisonBucket : meta == 3 ? this.remedyBucket : meta == 4 ? this.milkBucket : this.itemIcon;
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (int meta = 0; meta < EnumSkyrootBucketType.values().length; ++meta) {
			subItems.add(new ItemStack(this, 1, meta));
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return stack.getItemDamage() == 3 ? EnumRarity.rare : super.getRarity(stack);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return EnumSkyrootBucketType.getType(stack.getItemDamage()) == EnumSkyrootBucketType.Empty ? 16 : 1;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		int meta = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumSkyrootBucketType.getType(meta).toString();
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
		int meta = stack.getItemDamage();

		if (world.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Blocks.cauldron && !world.isRemote)
		{
			BlockCauldron cauldron = (BlockCauldron) world.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);

			int waterLevel = BlockCauldron.func_150027_b(world.getBlockMetadata(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ));

			if (EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Water)
			{
				if (waterLevel < 3)
				{
					if (!player.capabilities.isCreativeMode)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Empty.meta));
					}

					cauldron.func_150024_a(world, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, 3);
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heldItem, World world, EntityPlayer player) {
		int meta = heldItem.getItemDamage();

		/* Remedy and Poison Bucket checker */
		if (EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Water && EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Empty) {
			player.setItemInUse(heldItem, this.getMaxItemUseDuration(heldItem));

			return heldItem;
		}

		boolean flag = EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Empty;
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, flag);

		if (movingobjectposition == null)
		{
			return heldItem;
		}
		else
		{
			FillBucketEvent event = new FillBucketEvent(player, heldItem, world, movingobjectposition);
			if (MinecraftForge.EVENT_BUS.post(event))
			{
				return heldItem;
			}

			if (event.getResult() == Event.Result.ALLOW)
			{
				if (player.capabilities.isCreativeMode)
				{
					return heldItem;
				}

				if (--heldItem.stackSize <= 0)
				{
					return event.result;
				}

				if (!player.inventory.addItemStackToInventory(event.result))
				{
					player.dropPlayerItemWithRandomChoice(event.result, false);
				}

				return heldItem;
			}
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
			{
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				if (!world.canMineBlock(player, i, j, k))
				{
					return heldItem;
				}

				if (flag)
				{
					if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, heldItem))
					{
						return heldItem;
					}

					Material material = world.getBlock(i, j, k).getMaterial();
					int l = world.getBlockMetadata(i, j, k);

					if (material == Material.water && l == 0)
					{
						world.setBlockToAir(i, j, k);
						return this.fillBucket(heldItem, player, ItemsAether.skyroot_bucket);
					}
				}
				else
				{
					if (EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Empty)
					{
						return new ItemStack(ItemsAether.skyroot_bucket);
					}

					if (movingobjectposition.sideHit == 0)
					{
						--j;
					}

					if (movingobjectposition.sideHit == 1)
					{
						++j;
					}

					if (movingobjectposition.sideHit == 2)
					{
						--k;
					}

					if (movingobjectposition.sideHit == 3)
					{
						++k;
					}

					if (movingobjectposition.sideHit == 4)
					{
						--i;
					}

					if (movingobjectposition.sideHit == 5)
					{
						++i;
					}

					if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, heldItem))
					{
						return heldItem;
					}

					if (this.tryPlaceContainedLiquid(player, world, heldItem, i, j, k) && !player.capabilities.isCreativeMode)
					{
						return new ItemStack(ItemsAether.skyroot_bucket);
					}
				}
			}

			return heldItem;
		}
	}

	private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket) {
		if (player.capabilities.isCreativeMode) {
			return emptyBuckets;
		} else {
			ItemStack result = new ItemStack(fullBucket, 1, 1);
			--emptyBuckets.stackSize;

			if (emptyBuckets.stackSize <= 0) {
				return result;
			} else {
				if (!player.inventory.addItemStackToInventory(result)) {
					player.dropPlayerItemWithRandomChoice(result, false);
				}
			}

			return emptyBuckets;
		}
	}


	public boolean tryPlaceContainedLiquid(EntityPlayer player, World world, ItemStack stack, int x, int y, int z) {
		if (EnumSkyrootBucketType.getType(stack.getItemDamage()) != EnumSkyrootBucketType.Water) {
			return false;
		} else {
			Material material = world.getBlock(x, y, z).getMaterial();
			boolean flag = !material.isSolid();

			if (!world.isAirBlock(x, y, z) && !flag) {
				return false;
			} else {
				if (world.provider.isHellWorld) {
					world.playSoundEffect((double) x, (double) y, (double) z, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

					for (int l = 0; l < 8; ++l) {
						world.spawnParticle("largesmoke", (double) x + Math.random(), (double) y + Math.random(), (double) z + Math.random(), 0.0D, 0.0D, 0.0D);
					}
				} else {
					if (!world.isRemote && flag && !material.isLiquid()) {
						world.func_147480_a(x, y, z, true);
					}

					world.setBlock(x, y, z, Blocks.flowing_water, 0, 11);
				}

				return true;
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World worldIn, EntityPlayer entityLiving) {
		return this.onBucketUsed(stack, worldIn, (EntityPlayer) entityLiving);
	}

	public ItemStack onBucketUsed(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		PlayerAether player = PlayerAether.get(entityplayer);
		int meta = itemstack.getItemDamage();

		if (!entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
		}

		if (EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Poison) {
			entityplayer.addPotionEffect(new EffectInebriation(PotionInebriation.inebriation.id, 500, 0));
		} else if (EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Remedy) {
			player.setCured(200);
			if (!world.isRemote)
			{
				entityplayer.curePotionEffects(new ItemStack(ItemsAether.skyroot_bucket, EnumSkyrootBucketType.Remedy.meta));
			}
		} else if (EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Milk) {
			if (!world.isRemote) {
				entityplayer.curePotionEffects(new ItemStack(Items.milk_bucket));
			}
		}

		return itemstack.stackSize <= 0 ? new ItemStack(this, 1, 0) : itemstack;
	}

	public int getMaxItemUseDuration(ItemStack itemstack) {
		int meta = itemstack.getItemDamage();

		if (EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Water) {
			return 32;
		} else {
			return 0;
		}
	}

	public EnumAction getItemUseAction(ItemStack itemstack) {
		int meta = itemstack.getItemDamage();

		if (EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Water) {
			return EnumAction.drink;
		} else {
			return EnumAction.none;
		}
	}

}