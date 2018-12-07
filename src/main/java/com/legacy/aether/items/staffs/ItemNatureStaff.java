package com.legacy.aether.items.staffs;

import net.minecraft.item.Item;

import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemNatureStaff extends Item {

	public ItemNatureStaff() {
		this.setFull3D();
		this.setMaxDamage(100);
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

}