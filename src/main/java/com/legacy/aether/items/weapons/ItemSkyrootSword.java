package com.legacy.aether.items.weapons;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemSkyrootSword extends ItemSword
{

	public ItemSkyrootSword() 
	{
		super(ToolMaterial.WOOD);
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
    	if (tab == AetherCreativeTabs.weapons)
    	{
            items.add(new ItemStack(this));
    	}
    }

	@Override
	public boolean getIsRepairable(ItemStack repairingItem, ItemStack material)
	{
		return material.getItem() == Item.getItemFromBlock(BlocksAether.aether_log) || material.getItem() == Item.getItemFromBlock(BlocksAether.skyroot_plank);
	}

}