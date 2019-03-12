package com.legacy.aether.entity.passive;

import com.legacy.aether.block.BlocksAether;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityAetherAnimal extends EntityAnimal
{

	public EntityAetherAnimal(EntityType<?> type, World worldIn)
	{
		super(type, worldIn);

		this.spawnableBlock = BlocksAether.AETHER_GRASS;
	}

	@Override
	public float getBlockPathWeight(BlockPos pos)
	{
		return this.world.getBlockState(pos.down()).getBlock() == this.spawnableBlock ? 10.0F : this.world.getBrightness(pos) - 0.5F;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack)
	{
		return false;
	}

}