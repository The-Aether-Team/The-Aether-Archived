package com.legacy.aether.server.blocks;

import java.util.Random;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.server.blocks.natural.BlockAetherFlower;

public class BlockAetherSapling extends BlockAetherFlower implements IGrowable
{

	private Object treeGenObject = null;

	public BlockAetherSapling(Object treeGen)
	{
		super();
		float f = 0.4F;
		this.FLOWER_AABB = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.treeGenObject = treeGen;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (world.isRemote)
		{
			return;
		}

		super.updateTick(world, pos, state, random);

		if (world.getLight(pos.up()) >= 9 && random.nextInt(30) == 0)
		{
			this.grow(world, random, pos, state);
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
		return worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state)
	{
		if (world.isRemote)
		{
			return;
		}

		world.setBlockState(pos, Blocks.AIR.getDefaultState());

		if (!((WorldGenerator) this.treeGenObject).generate(world, rand, pos))
		{
			world.setBlockState(pos, this.getDefaultState());
		}
	}

}