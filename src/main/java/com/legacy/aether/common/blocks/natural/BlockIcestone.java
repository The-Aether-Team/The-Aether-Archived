package com.legacy.aether.common.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockIcestone extends Block
{

	public BlockIcestone() 
	{
		super(Material.ROCK);

		this.setHardness(3F);
		this.setTickRandomly(true);
		this.setSoundType(SoundType.GLASS);
	}

	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		for (int x = pos.getX() - 3; x <= (pos.getX() + 3); ++x)
		{
			for (int y = pos.getY() - 3; y <= (pos.getY() + 3); ++y)
			{
				for (int z = pos.getZ() - 3; z <= (pos.getZ() + 3); ++z)
				{
					BlockPos newPos = new BlockPos(x, y, z);
					Block block = worldIn.getBlockState(newPos).getBlock();

					if (block == Blocks.WATER || block == Blocks.FLOWING_WATER)
					{
						worldIn.setBlockState(newPos, Blocks.ICE.getDefaultState());
					}
					else if (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
					{
						worldIn.setBlockState(newPos, Blocks.OBSIDIAN.getDefaultState());
					}
				}
			}
		}
	}

}