package com.gildedgames.the_aether.items.tools;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.util.EnumAetherToolType;

public class ItemValkyrieTool extends ItemAetherTool 
{

	public ItemValkyrieTool(EnumAetherToolType toolType) 
	{
		super(ToolMaterial.DIAMOND, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }
}