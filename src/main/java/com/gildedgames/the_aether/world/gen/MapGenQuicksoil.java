package com.gildedgames.the_aether.world.gen;

import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public class MapGenQuicksoil extends MapGenBase
{

    private void generate(ChunkPrimer chunkPrimer, int posX, int posY, int posZ)
    {
		for(int x = posX - 3; x < posX + 4; x++)
		{
			for(int z = posZ - 3; z < posZ + 4; z++)
			{
				if(chunkPrimer.getBlockState(x, posY, z).getBlock() == Blocks.AIR && ((x - posX) * (x - posX) + (z - posZ) * (z - posZ)) < 12)
				{
					chunkPrimer.setBlockState(x, posY, z, BlocksAether.quicksoil.getDefaultState());
				}
			}
		}
    }

	@Override
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer chunkPrimerIn)
    {
		if (this.rand.nextInt(10) == 0)
		{
			for (int x = 3; x < 12; x++)
			{
				for (int z = 3; z < 12; z++)
				{
					for (int n = 3; n < 48; n++)
					{
						if (chunkPrimerIn.getBlockState(x, n, z).getBlock() == Blocks.AIR && chunkPrimerIn.getBlockState(x, n + 1, z).getBlock() == BlocksAether.aether_grass && chunkPrimerIn.getBlockState(x, n + 2, z).getBlock() == Blocks.AIR)
						{
							this.generate(chunkPrimerIn, x, n, z);
							n += 128;
						}
					}
				}
			}
		}
    }

}