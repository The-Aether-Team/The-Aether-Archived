package com.legacy.aether.server.world.biome.decoration;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.blocks.natural.BlockAetherDirt;
import com.legacy.aether.server.blocks.natural.BlockAetherLeaves;
import com.legacy.aether.server.blocks.natural.BlockAetherLog;
import com.legacy.aether.server.blocks.util.EnumLeafType;
import com.legacy.aether.server.blocks.util.EnumLogType;

public class AetherGenSkyrootTree extends WorldGenAbstractTree
{

    public AetherGenSkyrootTree()
    {
    	super(true);
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos)
    {
        int l = random.nextInt(3) + 4;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        IBlockState j1 = world.getBlockState(mutablePos.setPos(pos.getX(), pos.getY() - 1, pos.getZ()));

        if(j1.getBlock() != BlocksAether.aether_grass && j1.getBlock() != BlocksAether.aether_dirt)
        {
            return false;
        }

        world.setBlockState(pos.down(), BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.double_drop, Boolean.valueOf(true)));

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
                    mutablePos.setPos(k3, k1, i4);
                    if((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !world.getBlockState(mutablePos).isOpaqueCube())
                    {
                        world.setBlockState(mutablePos, BlocksAether.aether_leaves.getDefaultState().withProperty(BlockAetherLeaves.leaf_type, EnumLeafType.Green));
                    }
                }

            }
        }

        for(int l1 = 0; l1 < l; l1++)
        {
        	mutablePos.setPos(pos.getX(), pos.getY() + l1, pos.getZ());
            IBlockState k2 = world.getBlockState(mutablePos);

            if(k2.getBlock() == Blocks.AIR || k2 == BlocksAether.aether_leaves.getDefaultState().withProperty(BlockAetherLeaves.leaf_type, EnumLeafType.Green))
            {
                world.setBlockState(mutablePos, BlocksAether.aether_log.getDefaultState().withProperty(BlockAetherLog.wood_type, EnumLogType.Skyroot).withProperty(BlockAetherLog.double_drop, Boolean.valueOf(true)), 2);
            }
        }

        return true;
    }

}