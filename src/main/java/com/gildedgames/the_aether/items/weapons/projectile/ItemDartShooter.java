package com.gildedgames.the_aether.items.weapons.projectile;

import java.util.List;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartBase;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartEnchanted;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartGolden;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartPoison;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.util.EnumDartShooterType;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDartShooter extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon goldenIcon;

	@SideOnly(Side.CLIENT)
	private IIcon poisonIcon;

	@SideOnly(Side.CLIENT)
	private IIcon enchantedIcon;

	public ItemDartShooter() {
		super();

		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		this.goldenIcon = registry.registerIcon(Aether.find("projectile/golden_dart_shooter"));
		this.poisonIcon = registry.registerIcon(Aether.find("projectile/poison_dart_shooter"));
		this.enchantedIcon = registry.registerIcon(Aether.find("projectile/enchanted_dart_shooter"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return meta == 1 ? this.poisonIcon : meta == 2 ? this.enchantedIcon : this.goldenIcon;
	}

	@Override
	public boolean isFull3D() {
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return stack.getItemDamage() == 2 ? EnumRarity.rare : super.getRarity(stack);
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (int var4 = 0; var4 < EnumDartShooterType.values().length; ++var4) {
			subItems.add(new ItemStack(this, 1, var4));
		}
	}

	private int consumeItem(EntityPlayer player, Item itemID, int maxDamage) {
		IInventory inv = player.inventory;

		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);

			if (stack == null) {
				continue;
			}

			int damage = stack.getItemDamage();

			if (maxDamage != 3) {
				if (stack.getItem() == itemID && stack.getItemDamage() == maxDamage) {
					if (!player.capabilities.isCreativeMode) {
						--stack.stackSize;
					}

					if (stack.stackSize == 0) {
						stack = null;
					}

					inv.setInventorySlotContents(i, stack);

					return damage;
				}
			}
			if (maxDamage == 3 && stack.getItem() == itemID) {
				if (!player.capabilities.isCreativeMode) {
					--stack.stackSize;
				}

				if (stack.stackSize == 0) {
					stack = null;
				}

				inv.setInventorySlotContents(i, stack);

				return 3;
			}
		}

		return -1;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return this.getUnlocalizedName() + "_" + EnumDartShooterType.getType(itemstack.getItemDamage()).toString();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heldItem, World world, EntityPlayer entityplayer) {
		int consume;

		if (!(entityplayer.capabilities.isCreativeMode)) {
			consume = this.consumeItem(entityplayer, ItemsAether.dart, heldItem.getItemDamage());
		} else {
			consume = heldItem.getItemDamage();
		}

		if (consume != -1) {
			world.playSoundEffect(entityplayer.posX, entityplayer.posY, entityplayer.posZ, "aether_legacy:projectile.dart_shooter.shoot", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));

			EntityDartBase dart = null;

			if (consume == 1) {
				dart = new EntityDartPoison(world, entityplayer, 1.0F);
			} else if (consume == 2) {
				dart = new EntityDartEnchanted(world, entityplayer, 1.0F);
			} else if (consume == 0) {
				dart = new EntityDartGolden(world, entityplayer, 1.0F);
			}

			if (!world.isRemote) {
				world.spawnEntityInWorld(dart);
				if (dart != null)
				{
					dart.setGravityVelocity(0.99F);
				}

				if (!(entityplayer.capabilities.isCreativeMode)) {
					dart.canBePickedUp = 1;
				}
				if ((entityplayer.capabilities.isCreativeMode)) {
					dart.canBePickedUp = 2;
				}
			}
		}

		return heldItem;
	}

}