package com.legacy.aether.blocks.util;

import java.util.Random;

import net.minecraft.block.Block;
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

	@Override
	public void onBlockAdded(World world,  BlockPos pos, IBlockState state)
	{
		world.scheduleUpdate(pos, this, 3);
	}

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		worldIn.scheduleUpdate(pos, this, 3);
    }

	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!this.leveled || this.leveled && world.isBlockIndirectlyGettingPowered(pos) != 0)
		{
			this.floatBlock(world, pos);
		}
	}

	private void floatBlock(World world, BlockPos pos)
	{
		if (canContinue(world, pos.up()) && pos.getY() < world.getHeight())
		{
			EntityFloatingBlock floating = new EntityFloatingBlock(world, pos, world.getBlockState(pos));

			if (!world.isRemote)
			{
				world.spawnEntity(floating);
			}

			world.setBlockToAir(pos);
		}
	}

	public static boolean canContinue(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		Material material = world.getBlockState(pos).getMaterial();

		if (block == Blocks.AIR || block == Blocks.FIRE)
		{
			return true;
		}

		if (material == Material.WATER || material == Material.LAVA)
		{
			return true;
		}

		return false;		
	}

}