package com.gildedgames.the_aether.world.biome.decoration;

import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class AetherGenQuicksoil extends WorldGenerator
{

    public AetherGenQuicksoil()
    {
    	super();
    }

    public boolean generate(World world, Random random, BlockPos pos)
    {
		for(int x = pos.getX() - 3; x < pos.getX() + 4; x++)
		{
			for(int z = pos.getZ() - 3; z < pos.getZ() + 4; z++)
			{
				BlockPos newPos = new BlockPos(x, pos.getY(), z);

				if(world.getBlockState(newPos).getBlock() == Blocks.AIR && ((x - pos.getX()) * (x - pos.getX()) + (z - pos.getZ()) * (z - pos.getZ())) < 12)
				{
					this.setBlockAndNotifyAdequately(world, newPos, BlocksAether.quicksoil.getDefaultState());
				}
			}
		}

		return true;
    }

}