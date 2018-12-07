package com.legacy.aether.items.accessories;

import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.legacy.aether.api.accessories.AccessoryType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAccessoryDyed extends ItemAccessory {

	public ItemAccessoryDyed(AccessoryType type) {
		super(type);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, int x, int y, int z, int facing, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem();

		if (worldIn.getBlock(x, y, z) == Blocks.cauldron) {
			if (heldItem != null && heldItem.getItem() instanceof ItemAccessoryDyed) {
				int j1 = BlockCauldron.func_150027_b(worldIn.getBlockMetadata(x, y, z));

				if (j1 > 0) {
					((ItemAccessoryDyed) heldItem.getItem()).removeColor(heldItem);

					Blocks.cauldron.func_150024_a(worldIn, x, y, z, j1 - 1);

					return true;
				}
			}
		}

		return super.onItemUse(stack, playerIn, worldIn, x, y, z, facing, hitX, hitY, hitZ);
	}

	public int getStackColor(ItemStack stack, int meta) {
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound == null) {
			return super.getColor();
		}

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

		return nbttagcompound1 == null ? super.getColor() : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : super.getColor());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int meta) {
		return this.getStackColor(stack, meta);
	}

	public void removeColor(ItemStack stack) {
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound != null) {
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if (nbttagcompound1.hasKey("color")) {
				nbttagcompound1.removeTag("color");
			}
		}
	}

	public void setColorTag(ItemStack stack, int color) {
		NBTTagCompound nbttagcompound = stack.getTagCompound();

		if (nbttagcompound == null) {
			nbttagcompound = new NBTTagCompound();
			stack.setTagCompound(nbttagcompound);
		}

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

		if (!nbttagcompound.hasKey("display", 10)) {
			nbttagcompound.setTag("display", nbttagcompound1);
		}

		nbttagcompound1.setInteger("color", color);
	}

}