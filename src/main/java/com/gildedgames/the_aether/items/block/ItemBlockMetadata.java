package com.gildedgames.the_aether.items.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMetadata extends ItemBlock {

	public ItemBlockMetadata(Block block) {
		super(block);

		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		Block block = Block.getBlockFromItem(stack.getItem());

		if (block instanceof IColoredBlock) {
			return ((IColoredBlock) block).getColorFromItemStack(stack, pass);
		}

		return super.getColorFromItemStack(stack, pass);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		Block block = Block.getBlockFromItem(stack.getItem());

		if (block instanceof INamedBlock) {
			return "tile." + ((INamedBlock) block).getUnlocalizedName(stack);
		}

		return super.getUnlocalizedName(stack);
	}

}