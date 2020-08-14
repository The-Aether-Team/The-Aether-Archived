package com.gildedgames.the_aether.items.tools;

import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.items.util.EnumAetherToolType;

public class ItemSkyrootTool extends ItemAetherTool
{

	public ItemSkyrootTool(EnumAetherToolType toolType)
	{
		super(ToolMaterial.WOOD, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return repair.getItem() == getItemFromBlock(BlocksAether.skyroot_plank);
	}
}