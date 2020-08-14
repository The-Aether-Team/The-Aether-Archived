package com.gildedgames.the_aether.items.block;

import net.minecraft.item.ItemStack;

public interface IColoredBlock {

	public int getColorFromItemStack(ItemStack stack, int pass);

}