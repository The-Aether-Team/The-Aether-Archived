package com.legacy.aether.server.items.food;

import net.minecraft.item.ItemFood;

import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public class ItemAetherFood extends ItemFood
{
	public ItemAetherFood(int healAmmount) 
	{
		super(healAmmount, false);
		this.setCreativeTab(AetherCreativeTabs.food);
	}

	public ItemAetherFood(int healAmmount, float saturationAmmount) 
	{
		super(healAmmount, saturationAmmount, false);
		this.setCreativeTab(AetherCreativeTabs.food);
	}

}