package com.gildedgames.the_aether.blocks;

import java.util.Random;

import com.gildedgames.the_aether.blocks.natural.BlockAetherFlower;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockAetherSapling extends BlockAetherFlower implements IGrowable
{

	public WorldGenerator treeGenObject = null;

	public BlockAetherSapling(WorldGenerator treeGen)
	{
		super();
		float f = 0.4F;
		this.FLOWER_AABB = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.treeGenObject = treeGen;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		

		if (!world.isRemote)
		{
			super.updateTick(world, pos, state, random);
			
			if (!world.isAreaLoaded(pos, 1)) return;
			if (world.getLight(pos.up()) >= 9 && random.nextInt(30) == 0)
			{
				this.growTree(world, pos, random);
			}
		}
	}

	public void growTree(World world, BlockPos pos, Random rand)
	{
		world.setBlockState(pos, Blocks.AIR.getDefaultState());

		if (!this.treeGenObject.generate(world, world.rand, pos))
		{
			world.setBlockState(pos, this.getDefaultState());
		}
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		if (worldIn.rand.nextFloat() < 0.45D)
        {
            this.growTree(worldIn, pos, rand);
        }
		
	}

}