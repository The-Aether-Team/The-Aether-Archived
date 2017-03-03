package com.legacy.aether.server.world.biome.decoration;

import java.util.Random;

import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.world.dungeon.GoldenDungeon;

public class AetherGenGoldenIsland extends WorldGenerator
{

    public AetherGenGoldenIsland()
    {
    }

    public boolean generate(World world, Random random, BlockPos pos)
    {
        return generateIsland(world, random, pos.getX(), pos.getY(), pos.getZ(), 24);
    }

    public boolean generateIsland(World world, Random random, int i, int j, int k, int l)
    {
        if(j - l <= 0)
        {
            j = l + 1;
        }

        if(j + l >= 116)
        {
            j = 116 - l - 1;
        }

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        int i1 = 0;
        int j1 = 21;

        for(int k1 = -j1; k1 <= j1; k1++)
        {
            for(int l1 = l; l1 >= -j1; l1--)
            {
                for(int k2 = -j1; k2 <= j1; k2++)
                {
                    if(!BlocksAether.isGood(world.getBlockState(mutablePos.setPos(k1 + i, l1 + j, k2 + k))) && ++i1 > l / 2)
                    {
                        return false;
                    }
                }

            }
        }

        for(int i2 = -l; i2 <= l; i2++)
        {
            for(int l2 = l; l2 >= -l; l2--)
            {
                for(int i3 = -l; i3 <= l; i3++)
                {
                    int k3 = MathHelper.floor_double(i2 * (1 + l2 / 240) / 0.8D);//MathHelper.floor_double(((double)i2 * (1.0D + (double)l2 / ((double)l * 10D))) / (double)f);
                    int i4 = l2;

                    if(l2 > 15)
                    {
                        i4 = MathHelper.floor_double((double)i4 * 1.375D);
                        i4 -= 6;
                    } 
                    else if(l2 < -15)
                    {
                        i4 = MathHelper.floor_double((double)i4 * 1.3500000238418579D);
                        i4 += 6;
                    }

                    int k4 = MathHelper.floor_double(i3 * (1 + l2 / 240) / 0.8D);// MathHelper.floor_double(((double)i3 * (1.0D + (double)l2 / ((double)l * 10D))) / (double)f);

                    if(Math.sqrt(k3 * k3 + i4 * i4 + k4 * k4) <= 24.0D)
                    {
                        if(BlocksAether.isGood(world.getBlockState(mutablePos.setPos(i2 + i, l2 + j + 1, i3 + k))) && l2 > 4)
                        {
                            world.setBlockState(new BlockPos.MutableBlockPos(i2 + i, l2 + j, i3 + k), BlocksAether.aether_grass.getDefaultState());
                            world.setBlockState(new BlockPos.MutableBlockPos(i2 + i, (l2 + j) - 1, i3 + k), BlocksAether.aether_dirt.getDefaultState());
                            world.setBlockState(new BlockPos.MutableBlockPos(i2 + i, (l2 + j) - (1 + random.nextInt(2)), i3 + k), BlocksAether.aether_dirt.getDefaultState());

                            if(l2 >= l / 2)
                            {
                                int j5 = random.nextInt(48);

                                if(j5 < 2)
                                {
                                    new AetherGenOakTree().generate(world, random, new BlockPos.MutableBlockPos(i2 + i, l2 + j + 1, i3 + k));
                                }
                                else if(j5 == 3)
                                {
                                    if(random.nextInt(2) == 0)
                                    {
                                        new WorldGenLakes(Blocks.FLOWING_WATER).generate(world, random, new BlockPos.MutableBlockPos((i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j, (i3 + k + random.nextInt(3)) - random.nextInt(3)));
                                    }
                                }
                                else if(j5 == 4)
                                {
                                    if(random.nextInt(2) == 0)
                                    {
                                    	new WorldGenFlowers(Blocks.RED_FLOWER, EnumFlowerType.POPPY).generate(world, random, new BlockPos.MutableBlockPos((i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j + 1, (i3 + k + random.nextInt(3)) - random.nextInt(3)));
                                    }
                                    else
                                    {
                                    	new WorldGenFlowers(Blocks.YELLOW_FLOWER, EnumFlowerType.DANDELION).generate(world, random, new BlockPos.MutableBlockPos((i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j + 1, (i3 + k + random.nextInt(3)) - random.nextInt(3)));
                                    }
                                }
                            }
                        }
                        else if(BlocksAether.isGood(world.getBlockState(mutablePos.setPos(i2 + i, l2 + j, i3 + k))))
                        {
                            world.setBlockState(new BlockPos.MutableBlockPos(i2 + i, l2 + j, i3 + k), BlocksAether.holystone.getDefaultState(), 2);
                        }
                    }
                }

            }

        }

        int j2 = 8 + random.nextInt(5);
        float f1 = 0.01745329F;
        for(int j3 = 0; j3 < j2; j3++)
        {
            float f2 = random.nextFloat() * 360F;
            float f3 = ((random.nextFloat() * 0.125F) + 0.7F) * (float)l;
            int l4 = i + MathHelper.floor_double(Math.cos(f1 * f2) * (double)f3);
            int k5 = j - MathHelper.floor_double((double)l * (double)random.nextFloat() * 0.29999999999999999D);
            int i6 = k + MathHelper.floor_double(-Math.sin(f1 * f2) * (double)f3);
            func_100008_b(world, random, l4, k5, i6, 8, true);
        }

        new GoldenDungeon().generate(world, random, i, j, k, j1);

		int l3 = MathHelper.floor_double((double)l * 0.75D);

		for(int j4 = 0; j4 < l3; j4++)
		{
			int i5 = (i + random.nextInt(l)) - random.nextInt(l);
			int l5 = (j + random.nextInt(l)) - random.nextInt(l);
			int j6 = (k + random.nextInt(l)) - random.nextInt(l);
			new AetherGenCave(Blocks.AIR, 24 + l3 / 3).generate(world, random, new BlockPos.MutableBlockPos(i5, l5, j6));
		}

        return true;
    }

    public boolean func_100008_b(World world, Random random, int i, int j, int k, int l, boolean flag)
    {
        if(j - l <= 0)
        {
            j = l + 1;
        }
        if(j + l >= 127)
        {
            j = 127 - l - 1;
        }

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        float f = 1.0F;

        for(int i1 = -l; i1 <= l; i1++)
        {
            for(int k1 = l; k1 >= -l; k1--)
            {
                for(int i2 = -l; i2 <= l; i2++)
                {
                    int k2 = MathHelper.floor_double((double)i1 / (double)f);
                    int i3 = k1;

                    if(k1 > 5)
                    {
                        i3 = MathHelper.floor_double((double)i3 * 1.375D);
                        i3 -= 2;
                    }
                    else if(k1 < -5)
                    {
                        i3 = MathHelper.floor_double((double)i3 * 1.3500000238418579D);
                        i3 += 2;
                    }

                    int k3 = MathHelper.floor_double((double)i2 / (double)f);

                    if(Math.sqrt(k2 * k2 + i3 * i3 + k3 * k3) <= 8.0D)
                    {
                        if(BlocksAether.isGood(world.getBlockState(mutablePos.setPos(i1 + i, k1 + j + 1, i2 + k))) && k1 > 1)
                        {
                            world.setBlockState(new BlockPos.MutableBlockPos(i1 + i, k1 + j, i2 + k), BlocksAether.aether_grass.getDefaultState());
                            world.setBlockState(new BlockPos.MutableBlockPos(i1 + i, (k1 + j) - 1, i2 + k), BlocksAether.aether_dirt.getDefaultState());
                            world.setBlockState(new BlockPos.MutableBlockPos(i1 + i, (k1 + j) - (1 + random.nextInt(2)), i2 + k), BlocksAether.aether_dirt.getDefaultState());

                            if(k1 >= 4)
                            {
                                int l3 = random.nextInt(64);

                                if(l3 == 0)
                                {
                                    new AetherGenOakTree().generate(world, random, new BlockPos.MutableBlockPos(i1 + i, k1 + j + 1, i2 + k));
                                }
                                else if(l3 == 5)
                                {
                                    if(random.nextInt(3) == 0)
                                    {
                                        new WorldGenLakes(Blocks.FLOWING_WATER).generate(world, random, new BlockPos.MutableBlockPos((i1 + i + random.nextInt(3)) - random.nextInt(3), k1 + j, (i2 + k + random.nextInt(3)) - random.nextInt(3)));
                                    }
                                }
                            }
                        } 
                        else if(BlocksAether.isGood(world.getBlockState(mutablePos.setPos(i1 + i, k1 + j, i2 + k))))
                        {
                            world.setBlockState(new BlockPos.MutableBlockPos(i1 + i, k1 + j, i2 + k), BlocksAether.holystone.getDefaultState(), 2);
                        }
                    }
                }

            }

        }

        return true;
    }

}