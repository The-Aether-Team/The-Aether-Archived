package com.legacy.aether.common.items.staffs;

import net.minecraft.item.Item;

import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

public class ItemNatureStaff extends Item
{

	public ItemNatureStaff()
	{
		this.setCreativeTab(AetherCreativeTabs.misc);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
	}

}