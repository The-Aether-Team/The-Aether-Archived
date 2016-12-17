package com.legacy.aether.server.items.weapons;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public class ItemSkyrootSword extends ItemSword
{

	public ItemSkyrootSword() 
	{
		super(ToolMaterial.WOOD);
		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public boolean getIsRepairable(ItemStack repairingItem, ItemStack material)
	{
		return material.getItem() == Item.getItemFromBlock(BlocksAether.aether_log) || material.getItem() == Item.getItemFromBlock(BlocksAether.skyroot_plank);
	}

}