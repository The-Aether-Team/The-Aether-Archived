package com.legacy.aether.server.world.biome.decoration;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.server.blocks.BlocksAether;

public class AetherGenQuicksoil extends WorldGenerator
{

    public AetherGenQuicksoil()
    {

    }

    public boolean generate(World world, Random random, BlockPos pos)
    {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

		for(int x = pos.getX() - 3; x < pos.getX() + 4; x++)
		{
			for(int z = pos.getZ() - 3; z < pos.getZ() + 4; z++)
			{
				mutablePos.setPos(x, pos.getY(), z);
				if(world.getBlockState(mutablePos).getBlock() == Blocks.AIR && ((x - pos.getX()) * (x - pos.getX()) + (z - pos.getZ()) * (z - pos.getZ())) < 12)
				{
					world.setBlockState(mutablePos, BlocksAether.quicksoil.getDefaultState());
				}
			}
		}

		return true;
    }

}