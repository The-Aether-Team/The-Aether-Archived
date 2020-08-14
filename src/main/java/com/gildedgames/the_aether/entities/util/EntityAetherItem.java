package com.gildedgames.the_aether.entities.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAetherItem extends EntityItem {

	public EntityAetherItem(World world) {
		super(world);
	}

	public EntityAetherItem(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityAetherItem(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
	}

	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}

}