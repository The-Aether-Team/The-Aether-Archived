package com.legacy.aether.common.items.weapons.projectile;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.projectile.darts.EntityDartBase;
import com.legacy.aether.common.entities.projectile.darts.EntityDartEnchanted;
import com.legacy.aether.common.entities.projectile.darts.EntityDartGolden;
import com.legacy.aether.common.entities.projectile.darts.EntityDartPoison;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.items.util.EnumDartShooterType;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.common.registry.sounds.SoundsAether;

public class ItemDartShooter extends Item
{

	public ItemDartShooter()
	{
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public boolean isFull3D()
	{
		return false;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < EnumDartShooterType.values().length; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}

	private int consumeItem(EntityPlayer player, Item itemID, int maxDamage)
	{
		IInventory inv = player.inventory;

		for (int i = 0; i < inv.getSizeInventory(); i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if (stack == null)
			{
				continue;
			}
			int damage = stack.getItemDamage();

			if (maxDamage != 3)
			{
				if (stack.getItem() == itemID && stack.getItemDamage() == maxDamage)
				{
					if (!player.capabilities.isCreativeMode)
					{
						stack.stackSize--;
					}

					if (stack.stackSize == 0)
					{
						stack = null;
					}

					inv.setInventorySlotContents(i, stack);
					return damage;
				}
			}
			if (maxDamage == 3 && stack.getItem() == itemID)
			{
				if (!player.capabilities.isCreativeMode)
				{
					stack.stackSize--;
				}

				if (stack.stackSize == 0)
				{
					stack = null;
				}

				inv.setInventorySlotContents(i, stack);
				return 3;
			}
		}

		return -1;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return this.getUnlocalizedName() + "_" + EnumDartShooterType.getType(itemstack.getItemDamage()).toString();
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer, EnumHand hand)
	{
		int consume;

		if (!(entityplayer.capabilities.isCreativeMode))
		{
			consume = this.consumeItem(entityplayer, ItemsAether.dart, itemstack.getItemDamage());
		}
		else
		{
			consume = itemstack.getItemDamage();
		}

		if (consume != -1)
		{
			world.playSound(entityplayer, entityplayer.getPosition(), SoundsAether.dart_shooter_shoot, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));//world.playSoundAtEntity(entityplayer, "random.drr", 2.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));

			EntityDartBase dart = null;

			if (consume == 1)
			{
				dart = new EntityDartPoison(world, entityplayer);
			}
			else if (consume == 2)
			{
				dart = new EntityDartEnchanted(world, entityplayer);
			}
			else if (consume == 0)
			{
				dart = new EntityDartGolden(world, entityplayer);
			}

			dart.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 1.0F, 1.0F);

			if (!world.isRemote)
			{
				world.spawnEntityInWorld(dart);

				if (!(entityplayer.capabilities.isCreativeMode))
				{
					dart.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
				}
				if ((entityplayer.capabilities.isCreativeMode))
				{
					dart.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
				}
			}
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}

}