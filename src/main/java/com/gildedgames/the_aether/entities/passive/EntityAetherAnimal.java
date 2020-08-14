package com.gildedgames.the_aether.entities.passive;

import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EntityAetherAnimal extends EntityAnimal {

	Random random = new Random();

	public EntityAetherAnimal(World worldIn) {
		super(worldIn);
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		return this.worldObj.getBlock(x, y - 1, z) == BlocksAether.aether_grass ? 10.0F : this.worldObj.getLightBrightness(x, y, z) - 0.5F;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == ItemsAether.blueberry;
	}

}