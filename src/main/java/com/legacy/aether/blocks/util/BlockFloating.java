package com.legacy.aether.blocks.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.entities.block.EntityFloatingBlock;

public class BlockFloating extends Block
{

	private boolean leveled;

	public BlockFloating(Material material, boolean leveled)
	{
		super(material);

		this.leveled = leveled;

		this.setTickRandomly(true);
	}

	public BlockFloating(Material material)
	{
		super(material);
	}

	public static boolean canFallInto(World world, BlockPos pos)
	{
		if (world.isAirBlock(pos))
		{
			return true;
		}

		IBlockState state = world.getBlockState(pos);

		Material material = state.getMaterial();

		return state.getBlock() == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if ((!this.leveled || (this.leveled && world.isBlockPowered(pos))) && !world.isRemote)
		{
			this.checkFallable(world, pos);
		}
	}

	private void checkFallable(World world, BlockPos pos)
	{
		boolean floatInstantly = BlockSand.fallInstantly;

		if (canFallInto(world, pos.up()) && pos.getY() >= 0)
		{
			byte b0 = 32;

			if (!floatInstantly && world.isAreaLoaded(pos.add(-b0, -b0, -b0), pos.add(b0, b0, b0)))
			{
				if (!world.isRemote)
				{
					EntityFloatingBlock entity = new EntityFloatingBlock(world,
							pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, world.getBlockState(pos));
					world.spawnEntity(entity);
				}
			}
			else
			{
				world.setBlockToAir(pos);

				BlockPos bottomPos = pos.down();

				while (canFallInto(world, bottomPos) && bottomPos.getY() > 0)
				{
					bottomPos = bottomPos.down();
				}

				if (bottomPos.getY() > 0)
				{
					world.setBlockState(bottomPos.up(), this.getDefaultState());
				}
			}
		}
	}

	@Override
	public int tickRate(World worldIn)
	{
		return 2;
	}
}