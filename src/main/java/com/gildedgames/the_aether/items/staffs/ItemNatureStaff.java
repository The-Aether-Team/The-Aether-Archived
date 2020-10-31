package com.gildedgames.the_aether.items.staffs;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.item.Item;

public class ItemNatureStaff extends Item
{

	public ItemNatureStaff()
	{
		this.setCreativeTab(AetherCreativeTabs.misc);
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
	}

}