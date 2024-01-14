package com.gildedgames.the_aether.world.biome.decoration;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.world.util.BlockPosUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class AetherGenLargeTree extends WorldGenAbstractTree
{
	public AetherGenLargeTree(IBlockState log, IBlockState leaf, boolean notify)
	{
		super(notify);

		this.leafBlock = leaf;
		this.logBlock = log;
	}

	public boolean branch(World world, Random random, BlockPos pos, int slant)
	{
		BlockPos.MutableBlockPos nPos = BlockPosUtil.toMutable(pos);

		int directionX = random.nextInt(3) - 1;
		int directionZ = random.nextInt(3) - 1;

		for (int n = 0; n < 2; n++)
		{
			BlockPosUtil.add(nPos, directionX, slant, directionZ);

			if (world.getBlockState(nPos).getBlock() == this.leafBlock.getBlock())
			{
				world.setBlockState(nPos, this.logBlock, 2);
			}
		}

		return true;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		BlockPos.MutableBlockPos nPos = BlockPosUtil.toMutable(pos);

		int height = 11;
		boolean flag = true;
		if (pos.getY() < 1 || pos.getY() + height + 1 > world.getActualHeight())
		{
			return false;
		}
		for (int i1 = pos.getY(); i1 <= pos.getY() + 1 + height; i1++)
		{
			byte byte0 = 1;

			if (i1 == pos.getY())
			{
				byte0 = 0;
			}
			if (i1 >= (pos.getY() + 1 + height) - 2)
			{
				byte0 = 2;
			}
			for (int i2 = pos.getX() - byte0; i2 <= pos.getX() + byte0 && flag; i2++)
			{
				for (int l2 = pos.getZ() - byte0; l2 <= pos.getZ() + byte0 && flag; l2++)
				{
					if (i1 >= 0 && i1 < world.getActualHeight())
					{
						nPos.setPos(i2, i1, l2);

						Block block = world.getBlockState(nPos).getBlock();

						if (block != Blocks.AIR && block != this.leafBlock)
						{
							flag = false;
						}
					}
					else
					{
						flag = false;
					}
				}

			}

		}

		if (!flag)
		{
			return false;
		}

		for (int y = pos.getY(); y < pos.getY() + height; y++)
		{
			nPos.setPos(pos.getX(), y, pos.getZ());

			if (!this.isReplaceable(world, nPos))
			{
				return false;
			}
		}

		//Can plant here check, not on clouds etc...
		nPos.setPos(pos.getX(), pos.getY() - 1, pos.getZ());

		if (world.getBlockState(nPos).getBlock() != BlocksAether.aether_grass &&
				world.getBlockState(nPos).getBlock() != BlocksAether.aether_dirt)
		{
			return false;
		}

		//make a big ball of leaves
		for (int x = pos.getX() - 4; x < pos.getX() + 6; x++)
		{
			for (int y = pos.getY() + 5; y < pos.getY() + 13; y++)
			{
				for (int z = pos.getZ() - 4; z < pos.getZ() + 6; z++)
				{
					nPos.setPos(x, y, z);

					if ((x - pos.getX()) * (x - pos.getX()) + (y - pos.getY() - 8) * (y - pos.getY() - 8) + (z - pos.getZ()) * (z - pos.getZ()) < 16 + random.nextInt(5) && world.getBlockState(nPos).getBlock() == Blocks.AIR)
					{
						world.setBlockState(nPos, this.leafBlock);
					}
				}
			}
		}

		for (int y = 0; y < height; y++)
		{
			nPos.setPos(pos.getX(), pos.getY() + y, pos.getZ());

			if (y < (height - 2))
			{
				if (this.isReplaceable(world, nPos))
				{
					world.setBlockState(nPos, this.logBlock, 2);
				}
			}

			if (y > 5)
			{
				for (int i = 0; i < 3; i++)
				{
					this.branch(world, random, nPos, 0);
				}
			}
		}

		return true;
	}

	private IBlockState leafBlock;

	private IBlockState logBlock;
}
