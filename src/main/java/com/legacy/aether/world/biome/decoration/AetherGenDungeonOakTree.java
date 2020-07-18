package com.legacy.aether.world.biome.decoration;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.natural.BlockAetherGrass;
import com.legacy.aether.blocks.natural.BlockAetherLeaves;
import com.legacy.aether.blocks.natural.BlockAetherLog;
import com.legacy.aether.blocks.util.EnumLeafType;
import com.legacy.aether.blocks.util.EnumLogType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class AetherGenDungeonOakTree extends WorldGenAbstractTree
{

    public AetherGenDungeonOakTree()
    {
        super(true);
    }

    public boolean branch(World world, Random random, int x, int y, int z, int slant)
    {
        int directionX = random.nextInt(3) - 1;
        int directionY = slant;
        int directionZ = random.nextInt(3) - 1;
        int i = x;
        int k = z;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(x, y, z);

        for(int n = 0; n < random.nextInt(2); n++)
        {
            x += directionX;
            y += directionY;
            z += directionZ;
            i -= directionX;
            k -= directionZ;

            if(world.getBlockState(mutablePos) == BlocksAether.aether_leaves.getDefaultState().withProperty(BlockAetherLeaves.leaf_type, EnumLeafType.Golden))
            {
                IBlockState state = BlocksAether.aether_log.getDefaultState().withProperty(BlockAetherLog.wood_type, EnumLogType.Oak).withProperty(BlockAetherLog.double_drop, Boolean.TRUE);
                world.setBlockState(mutablePos, state);
                world.setBlockState(new BlockPos(i, y, k), state);
            }
        }

        return true;
    }

    @Override
    public boolean generate(World world, Random random, BlockPos pos)
    {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        if(world.getBlockState(mutablePos.setPos(pos.getX(), pos.getY() - 1, pos.getZ())) != BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.dungeon_block, Boolean.TRUE))
        {
            return false;
        }

        System.out.println(true);

        int height = 9;

        for(int x1 = pos.getX() - 3; x1 < pos.getX() + 4; x1++)
        {
            for(int y1 = pos.getY() + 5; y1 < pos.getY() + 12; y1++)
            {
                for(int z1 = pos.getZ() - 3; z1 < pos.getZ() + 4; z1++)
                {
                    if((x1 - pos.getX()) * (x1 - pos.getX()) + (y1 - pos.getY() - 8) * (y1 - pos.getY() - 8) + (z1 - pos.getZ()) * (z1 - pos.getZ()) < 12 + random.nextInt(5) && world.isAirBlock(mutablePos.setPos(x1, y1, z1)))
                    {
                        mutablePos.setPos(x1, y1, z1);
                        world.setBlockState(mutablePos,  BlocksAether.aether_leaves.getDefaultState().withProperty(BlockAetherLeaves.leaf_type, EnumLeafType.Golden));
                    }
                }
            }
        }

        for(int n = 0; n < height; n++)
        {
            if(n > 4)
            {
                if(random.nextInt(3) > 0)
                {
                    branch(world, random, pos.getX(), pos.getY() + n, pos.getZ(), n / 4 - 1);
                }
            }

            IBlockState state = BlocksAether.aether_log.getDefaultState().withProperty(BlockAetherLog.wood_type, EnumLogType.Oak).withProperty(BlockAetherLog.double_drop, Boolean.TRUE);
            world.setBlockState(pos.add(0, n, 0), state);
        }

        return true;
    }

}