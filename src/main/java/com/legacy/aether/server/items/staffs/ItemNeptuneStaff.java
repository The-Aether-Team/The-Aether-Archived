package com.legacy.aether.server.items.staffs;

import net.minecraft.item.Item;

import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public class ItemNeptuneStaff extends Item
{

	public ItemNeptuneStaff()
	{
		this.setCreativeTab(AetherCreativeTabs.misc);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
	}

}