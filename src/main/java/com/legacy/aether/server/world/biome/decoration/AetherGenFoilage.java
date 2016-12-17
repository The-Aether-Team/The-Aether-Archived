package com.legacy.aether.server.world.biome.decoration;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.server.blocks.natural.BlockAetherFlower;

public class AetherGenFoilage extends WorldGenerator
{

    private IBlockState plantState;

    public AetherGenFoilage()
    {

    }

    public void setPlantBlock(IBlockState state)
    {
    	this.plantState = state;
    }

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
	    BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();

        for(int l = 0; l < 64; l++)
        {
            int i1 = (pos.getX() + random.nextInt(8)) - random.nextInt(8);
            int j1 = (pos.getY() + random.nextInt(4)) - random.nextInt(4);
            int k1 = (pos.getZ() + random.nextInt(8)) - random.nextInt(8);
            mutableblockpos.setPos(i1, j1, k1);

            if(world.isAirBlock(mutableblockpos) && ((BlockAetherFlower)plantState.getBlock()).canBlockStay(world, mutableblockpos, null))
            {
            	world.setBlockState(mutableblockpos, plantState);
            }
        }

		return true;
	}
}