package com.gildedgames.the_aether.items.weapons;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;

public class ItemSkyrootSword extends ItemSword
{

	public ItemSkyrootSword() 
	{
		super(ToolMaterial.WOOD);
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
    	if (tab == AetherCreativeTabs.weapons || tab == CreativeTabs.SEARCH)
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