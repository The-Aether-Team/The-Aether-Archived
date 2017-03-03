package com.legacy.aether.server.world.biome.decoration;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.blocks.natural.BlockAercloud;
import com.legacy.aether.server.blocks.util.EnumCloudType;

public class AetherGenClouds extends WorldGenerator
{

	private EnumCloudType type;

    private int numberOfBlocks;

    private boolean flat;

    public AetherGenClouds()
    {
    	super();
    }

    public void setCloudType(EnumCloudType type)
    {
    	this.type = type;
    }

    public void setCloudAmmount(int cloudAmmount)
    {
    	this.numberOfBlocks = cloudAmmount;
    }

    public void setFlat(boolean isFlat)
    {
    	this.flat = isFlat;
    }

    public boolean generate(World world, Random random, BlockPos pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int xTendency = random.nextInt(3) - 1;
        int zTendency = random.nextInt(3) - 1;

        if (this.type == null)
        {
            return false;
        }

        for (int n = 0; n < this.numberOfBlocks; ++n)
        {
            x += random.nextInt(3) - 1 + xTendency;

            if (random.nextBoolean() && !this.flat || this.flat && random.nextInt(10) == 0)
            {
                y += random.nextInt(3) - 1;
            }

            z += random.nextInt(3) - 1 + zTendency;

            for (int x1 = x; x1 < x + random.nextInt(4) + 3 * (this.flat ? 3 : 1); ++x1)
            {
                for (int y1 = y; y1 < y + random.nextInt(1) + 2; ++y1)
                {
                    for (int z1 = z; z1 < z + random.nextInt(4) + 3 * (this.flat ? 3 : 1); ++z1)
                    {
                    	BlockPos newPos = new BlockPos(x1, y1, z1);

                        if (world.getBlockState(newPos).getBlock() == Blocks.AIR)
                        {
                        	if (Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 * (this.flat ? 3 : 1) + random.nextInt(2))
                        	{
                        		this.setBlockAndNotifyAdequately(world, newPos, BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.cloud_type, this.type));
                        	}
                        }
                    }
                }
            }
        }

        return true;
    }

}