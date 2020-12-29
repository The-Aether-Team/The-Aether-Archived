package com.gildedgames.the_aether.entities.passive;

import java.util.Random;

import com.gildedgames.the_aether.world.AetherWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;

public abstract class EntityAetherAnimal extends EntityAnimal
{
	Random random = new Random();

	public EntityAetherAnimal(World worldIn) 
	{
		super(worldIn);
	}

	@Override
    public float getBlockPathWeight(BlockPos pos)
    {
    	return AetherWorld.viableSpawningBlocks.contains(this.world.getBlockState(pos.down()).getBlock()) ? 10.0F : this.world.getLightBrightness(pos) - 0.5F;
    }

	public boolean getCanSpawnHere()
	{
		IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		return AetherWorld.viableSpawningBlocks.contains(this.world.getBlockState(blockpos.down()).getBlock()) && this.world.getLight(blockpos) > 8 && iblockstate.canEntitySpawn(this);
	}

	public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == ItemsAether.blue_berry;
    }

}