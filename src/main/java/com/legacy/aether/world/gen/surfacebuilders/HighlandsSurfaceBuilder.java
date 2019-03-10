package com.legacy.aether.world.gen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class HighlandsSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig>
{

	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		IBlockState topBlock = config.getTop();
		IBlockState fillerBlock = config.getMiddle();
		MutableBlockPos pos = new MutableBlockPos();

		int int_5 = -1;
		int int_6 = (int) (3.0D + random.nextDouble() * 0.25D);

		for (int int_9 = 128; int_9 >= 0; --int_9)
		{
			pos.setPos(x, int_9, z);

			IBlockState state = chunkIn.getBlockState(pos);

			if (state.isAir(chunkIn, pos))
			{
				int_5 = -1;
			}
			else if (state.getBlock() == defaultBlock.getBlock())
			{
				if (int_5 == -1)
				{
					if (int_6 <= 0)
					{
						topBlock = Blocks.AIR.getDefaultState();
						fillerBlock = defaultBlock;
					}

					int_5 = int_6;

					if (int_9 >= 0)
					{
						chunkIn.setBlockState(pos, topBlock, false);
					}
					else
					{
						chunkIn.setBlockState(pos, fillerBlock, false);
					}
				}
				else if (int_5 > 0)
				{
					--int_5;

					chunkIn.setBlockState(pos, fillerBlock, false);
				}
			}
		}
	}

}