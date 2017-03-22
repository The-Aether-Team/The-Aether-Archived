package com.legacy.aether.common.world.biome.decoration;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.common.blocks.BlocksAether;

public class AetherGenLiquids extends WorldGenerator
{

    public AetherGenLiquids()
    {
    	super();
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        if (worldIn.getBlockState(position.up()).getBlock() != BlocksAether.holystone)
        {
            return false;
        }
        else if (worldIn.getBlockState(position.down()).getBlock() != BlocksAether.holystone)
        {
            return false;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(position);

            if (!iblockstate.getBlock().isAir(iblockstate, worldIn, position) && iblockstate.getBlock() != BlocksAether.holystone)
            {
                return false;
            }
            else
            {
                int i = 0;

                if (worldIn.getBlockState(position.west()).getBlock() == BlocksAether.holystone)
                {
                    ++i;
                }

                if (worldIn.getBlockState(position.east()).getBlock() == BlocksAether.holystone)
                {
                    ++i;
                }

                if (worldIn.getBlockState(position.north()).getBlock() == BlocksAether.holystone)
                {
                    ++i;
                }

                if (worldIn.getBlockState(position.south()).getBlock() == BlocksAether.holystone)
                {
                    ++i;
                }

                int j = 0;

                if (worldIn.isAirBlock(position.west()))
                {
                    ++j;
                }

                if (worldIn.isAirBlock(position.east()))
                {
                    ++j;
                }

                if (worldIn.isAirBlock(position.north()))
                {
                    ++j;
                }

                if (worldIn.isAirBlock(position.south()))
                {
                    ++j;
                }

                if (i == 3 && j == 1)
                {
                    IBlockState iblockstate1 = Blocks.FLOWING_WATER.getDefaultState();
                    this.setBlockAndNotifyAdequately(worldIn, position, iblockstate1);
                    worldIn.immediateBlockTick(position, iblockstate1, rand);
                }

                return true;
            }
        }
    }
}