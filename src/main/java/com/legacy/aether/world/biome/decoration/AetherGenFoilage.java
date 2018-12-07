package com.legacy.aether.world.biome.decoration;

import java.util.Random;

import com.legacy.aether.blocks.natural.BlockAetherFlower;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class AetherGenFoilage extends WorldGenerator
{

    private Block plantState;

    public AetherGenFoilage()
    {
    	super();
    }

    public void setPlantBlock(Block state)
    {
    	this.plantState = state;
    }

	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
        for(int l = 0; l < 64; l++)
        {
            int i1 = (x + random.nextInt(8)) - random.nextInt(8);
            int j1 = (y + random.nextInt(4)) - random.nextInt(4);
            int k1 = (z + random.nextInt(8)) - random.nextInt(8);

            if(world.isAirBlock(i1, j1, k1) && ((BlockAetherFlower)this.plantState).canBlockStay(world, i1, j1, k1))
            {
            	this.setBlockAndNotifyAdequately(world, i1, j1, k1, this.plantState, 0);
            }
        }

		return true;
	}

}