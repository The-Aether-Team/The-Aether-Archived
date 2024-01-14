package com.gildedgames.the_aether.world.biome.decoration;

import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.world.AetherWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class AetherGenSkyrootTree extends WorldGenAbstractTree
{
    private final IBlockState leaves;

    public AetherGenSkyrootTree(boolean notify)
    {
    	this(BlocksAether.aether_leaves.getDefaultState(), notify);
    }

    public AetherGenSkyrootTree(IBlockState leaves, boolean notify)
    {
        super(notify);
        this.leaves = leaves;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos)
    {
        int l = random.nextInt(3) + 4;

        IBlockState j1 = world.getBlockState(pos.down());

        if (!AetherWorld.viableSoilBlocks.contains(j1.getBlock()))
        {
            return false;
        }

        this.setBlockAndNotifyAdequately(world, pos.down(), BlocksAether.aether_dirt.getDefaultState());

        for(int k1 = (pos.getY() - 3) + l; k1 <= pos.getY() + l; k1++)
        {
            int j2 = k1 - (pos.getY() + l);
            int i3 = 1 - j2 / 2;

            for(int k3 = pos.getX() - i3; k3 <= pos.getX() + i3; k3++)
            {
                int l3 = k3 - pos.getX();

                for(int i4 = pos.getZ() - i3; i4 <= pos.getZ() + i3; i4++)
                {
                    int j4 = i4 - pos.getZ();

                    BlockPos newPos = new BlockPos(k3, k1, i4);

                    if((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !world.getBlockState(newPos).isOpaqueCube())
                    {
                    	this.setBlockAndNotifyAdequately(world, newPos, this.leaves);
                    }
                }

            }
        }

        for(int l1 = 0; l1 < l; l1++)
        {
        	BlockPos newPos = new BlockPos(pos.up(l1));
            IBlockState k2 = world.getBlockState(newPos);

            this.setBlockAndNotifyAdequately(world, newPos, BlocksAether.aether_log.getDefaultState());
        }

        return true;
    }

}