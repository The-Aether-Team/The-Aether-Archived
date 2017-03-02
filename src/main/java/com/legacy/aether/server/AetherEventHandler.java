package com.legacy.aether.server;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.blocks.portal.BlockAetherPortal;
import com.legacy.aether.server.items.ItemsAether;
import com.legacy.aether.server.items.weapons.ItemSkyrootSword;
import com.legacy.aether.server.registry.achievements.AchievementsAether;

public class AetherEventHandler 
{

	@SubscribeEvent
	public void checkBlockBannedEvent(RightClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		ItemStack currentStack = event.getItemStack();

		if (player.dimension == AetherConfig.getAetherDimensionID())
		{
			if (currentStack != null && (currentStack.getItem() == Items.FLINT_AND_STEEL || currentStack.getItem() == Item.getItemFromBlock(Blocks.TORCH) || currentStack.getItem() == Items.FIRE_CHARGE))
			{
				for (int i = 0; i < 10; ++i)
				{
					event.getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, event.getHitVec().xCoord, event.getHitVec().yCoord, event.getHitVec().zCoord, 0.0D, 0.0D, 0.0D, new int [] {});
				}

				event.setCanceled(true);

			}
			else if (event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.BED)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event)
	{
		ItemStack stack = event.getEmptyBucket();

		if (stack == null)
		{
			return;
		}

		World worldObj = event.getWorld();
		RayTraceResult target = event.getTarget();
		EntityPlayer player = event.getEntityPlayer();

		boolean isWater = stack.getItem() == Items.WATER_BUCKET || stack.getItem() == ItemsAether.skyroot_bucket && stack.getMetadata() == 1;
		boolean isLava = stack.getItem() == Items.LAVA_BUCKET;

		boolean validDimension = (player.dimension == 0 || player.dimension == AetherConfig.getAetherDimensionID());

		if (target != null && target.typeOfHit == Type.BLOCK && validDimension)
		{
			BlockPos hitPos = target.getBlockPos().offset(target.sideHit);

			if (isWater)
			{
				if (((BlockAetherPortal) BlocksAether.aether_portal).trySpawnPortal(worldObj, hitPos))
				{
					if (!player.capabilities.isCreativeMode)
					{
						if (stack.getItem() == ItemsAether.skyroot_bucket || stack.getItemDamage() == 1)
						{
							event.setFilledBucket(new ItemStack(ItemsAether.skyroot_bucket));
						}

						if (stack.getItem() == Items.WATER_BUCKET)
						{
							event.setFilledBucket(new ItemStack(Items.BUCKET));
						}
					}

					event.setResult(Result.ALLOW);
				}
			}

			if (isLava && player.dimension == AetherConfig.getAetherDimensionID())
			{
				if (player.capabilities.isCreativeMode && player.isSneaking())
				{
					return;
				}

				if (worldObj.isAirBlock(hitPos))
				{
					worldObj.setBlockState(hitPos, BlocksAether.aerogel.getDefaultState());

					if (!player.capabilities.isCreativeMode)
					{
						event.setFilledBucket(new ItemStack(Items.BUCKET));
					}
				}

				event.setResult(Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
	public void onItemPickedUp(EntityItemPickupEvent event)
	{
		if (event.getItem().getEntityItem() != null && event.getItem().getEntityItem().getItem() == Item.getItemFromBlock(BlocksAether.aether_log))
		{
			event.getEntityPlayer().addStat(AchievementList.MINE_WOOD);
		}
	}

	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event)
	{
		EntityPlayer player = event.player;
		ItemStack stack = event.crafting;

		if (stack.getItem() == ItemsAether.holystone_pickaxe)
		{
			player.addStat(AchievementList.BUILD_BETTER_PICKAXE);
		}

		if (stack.getItem() == ItemsAether.skyroot_pickaxe)
		{
			player.addStat(AchievementList.BUILD_PICKAXE);
		}

		if (stack.getItem() == ItemsAether.skyroot_sword)
		{
			player.addStat(AchievementList.BUILD_SWORD);
		}

		if (stack.getItem() == Item.getItemFromBlock(BlocksAether.enchanter))
		{
			player.addStat(AchievementsAether.enchanter);
		}

		if (isGravititeTool(stack.getItem()))
		{
			player.addStat(AchievementsAether.grav_tools);
		}
	}

	@SubscribeEvent
	public void onEntityDropLoot(LivingDropsEvent event)
	{
		if (event.getSource() instanceof EntityDamageSource)
		{
			EntityLivingBase entity = event.getEntityLiving();
			EntityDamageSource source = (EntityDamageSource) event.getSource();

			if (source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) source.getEntity();
				ItemStack currentItem = player.inventory.getCurrentItem();

				if (currentItem != null && currentItem.getItem() instanceof ItemSkyrootSword && !(entity instanceof EntityPlayer) && !(entity instanceof EntityWither))
				{
					for (EntityItem items : event.getDrops())
					{
						EntityItem item = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, items.getEntityItem());
						entity.worldObj.spawnEntityInWorld(item);
					}
				}
			}
		}
	}

	public boolean isGravititeTool(Item stackID)
	{
		return stackID == ItemsAether.gravitite_shovel || stackID == ItemsAether.gravitite_axe || stackID == ItemsAether.gravitite_pickaxe;
	}

}