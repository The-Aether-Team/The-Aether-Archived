package com.legacy.aether.world.gen.components;

import com.legacy.aether.world.biome.decoration.AetherGenOakTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.world.gen.AetherGenUtils;
import com.legacy.aether.world.gen.AetherStructure;

public class ComponentGoldenIsland extends AetherStructure
{
    private BlockPos chunkPos;

    public ComponentGoldenIsland()
    {

    }

	public ComponentGoldenIsland(int chunkX, int chunkZ)
	{
        this.setCoordBaseMode(EnumFacing.NORTH);

        this.boundingBox = new StructureBoundingBox(chunkX, 80, chunkZ, chunkX + 100, 220, chunkZ + 100);
        this.chunkPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
	}

	@Override
	public boolean generate() 
	{
		this.replaceAir = true;
		this.replaceSolid = true;
		this.setStructureOffset(60, 0, 60);
		this.generateBaseIsland();
		return true;
	}

	public void generateBaseIsland()
	{
        int i1 = 0;
        int j1 = 21;

        for(int k1 = -j1; k1 <= j1; k1++)
        {
            for(int l1 = 24; l1 >= -j1; l1--)
            {
                for(int k2 = -j1; k2 <= j1; k2++)
                {
                	IBlockState state = this.getBlockStateWithOffset(k1, l1, k2);
                    if(state == BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic) && ++i1 > 24 / 2)
                    {
                        return;
                    }
                }

            }
        }

        for(int i2 = -24; i2 <= 24; i2++)
        {
            for(int l2 = 24; l2 >= -24; l2--)
            {
                for(int i3 = -24; i3 <= 24; i3++)
                {
                    int k3 = MathHelper.floor(i2 * (1 + l2 / 240) / 0.8D);
                    int i4 = l2;

                    if(l2 > 15)
                    {
                        i4 = MathHelper.floor((double)i4 * 1.375D);
                        i4 -= 6;
                    } 
                    else if(l2 < -15)
                    {
                        i4 = MathHelper.floor((double)i4 * 1.3500000238418579D);
                        i4 += 6;
                    }

                    int k4 = MathHelper.floor(i3 * (1 + l2 / 240) / 0.8D);

                    if(Math.sqrt(k3 * k3 + i4 * i4 + k4 * k4) <= 24.0D)
                    {
                        if(BlocksAether.isGood(this.getBlockStateWithOffset(i2, l2 + 1, i3)) && l2 > 4)
                        {
                        	this.setBlockWithOffset(i2, l2, i3, BlocksAether.aether_grass.getDefaultState());
                        	this.setBlockWithOffset(i2, l2 - 1, i3, BlocksAether.aether_dirt.getDefaultState());
                        	this.setBlockWithOffset(i2, l2 - (1 + this.random.nextInt(2)), i3, BlocksAether.aether_dirt.getDefaultState());

                            if(l2 >= 24 / 2)
                            {
                                int j5 = this.random.nextInt(48);

                                if(j5 < 2)
                                {
//                                    WorldGenerator oakTree = new AetherGenOakTree();
//                                    oakTree.generate(this.worldObj, this.random, this.worldObj.getHeight(new BlockPos(this.getActualX(i2, i3), 0, this.getActualZ(i2, i3))));
                                    AetherGenUtils.generateGoldenOakTree(this, i2, l2 + 1, i3);
                                }
                                else if(j5 == 3)
                                {
                                    if(this.random.nextInt(2) == 0)
                                    {
                                        //new WorldGenLakes(Blocks.FLOWING_WATER).generate(world, random, new BlockPos.MutableBlockPos((i2 + i + random.nextInt(3)) - random.nextInt(3), l2 + j, (i3 + k + random.nextInt(3)) - random.nextInt(3)));
                                    }
                                }
                                else if(j5 == 4)
                                {
                                    if(this.random.nextInt(2) == 0)
                                    {
                                    	AetherGenUtils.generateFlower(this, Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), EnumFlowerType.POPPY), (i2 + this.random.nextInt(3)) - this.random.nextInt(3), l2 + 1, (i3 + this.random.nextInt(3)) - this.random.nextInt(3));
                                    }
                                    else
                                    {
                                    	AetherGenUtils.generateFlower(this, Blocks.YELLOW_FLOWER.getDefaultState().withProperty(Blocks.YELLOW_FLOWER.getTypeProperty(), EnumFlowerType.DANDELION), (i2 + this.random.nextInt(3)) - this.random.nextInt(3), l2 + 1, (i3 + this.random.nextInt(3)) - this.random.nextInt(3));
                                    }
                                }
                            }
                        }
                        else if(BlocksAether.isGood(this.getBlockStateWithOffset(i2, l2, i3)))
                        {
                        	this.setBlockWithOffset(i2, l2, i3, BlocksAether.holystone.getDefaultState());
                        }
                    }
                }

            }

        }

		int l3 = MathHelper.floor(24.0D * 0.75D);

		for(int j4 = 0; j4 < l3; j4++)
		{
			int i5 = this.random.nextInt(24) - this.random.nextInt(24);
			int l5 = this.random.nextInt(24) - this.random.nextInt(24);
			int j6 = this.random.nextInt(24) - this.random.nextInt(24);

			this.generateCaves(i5, l5, j6, 24 + l3 / 3);
		}
	}

	public void generateCaves(int x, int y, int z, int size)
	{
        float f = this.random.nextFloat() * 3.141593F;

        double d = (float)(x + 8) + (MathHelper.sin(f) * (float)size) / 8F;
        double d1 = (float)(x + 8) - (MathHelper.sin(f) * (float)size) / 8F;
        double d2 = (float)(z + 8) + (MathHelper.cos(f) * (float)size) / 8F;
        double d3 = (float)(z + 8) - (MathHelper.cos(f) * (float)size) / 8F;
        double d4 = y + this.random.nextInt(3) + 2;
        double d5 = y + this.random.nextInt(3) + 2;

        for(int l = 0; l <= size; l++)
        {
            double d6 = d + ((d1 - d) * (double)l) / (double)size;
            double d7 = d4 + ((d5 - d4) * (double)l) / (double)size;
            double d8 = d2 + ((d3 - d2) * (double)l) / (double)size;
            double d9 = (this.random.nextDouble() * (double)size) / 16D;
            double d10 = (double)(MathHelper.sin(((float)l * 3.141593F) / (float)size) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin(((float)l * 3.141593F) / (float)size) + 1.0F) * d9 + 1.0D;
            int i1 = (int)(d6 - d10 / 2D);
            int j1 = (int)(d7 - d11 / 2D);
            int k1 = (int)(d8 - d10 / 2D);
            int l1 = (int)(d6 + d10 / 2D);
            int i2 = (int)(d7 + d11 / 2D);
            int j2 = (int)(d8 + d10 / 2D);

            for(int k2 = i1; k2 <= l1; k2++)
            {
                double d12 = (((double)k2 + 0.5D) - d6) / (d10 / 2D);
                if(d12 * d12 < 1.0D)
                {
                    for(int l2 = j1; l2 <= i2; l2++)
                    {
                        double d13 = (((double)l2 + 0.5D) - d7) / (d11 / 2D);
                        if(d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for(int i3 = k1; i3 <= j2; i3++)
                            {
                                double d14 = (((double)i3 + 0.5D) - d8) / (d10 / 2D);

                                Block block = this.getBlockStateWithOffset(k2, l2, i3).getBlock();

                                if(d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && (block == BlocksAether.mossy_holystone || block == BlocksAether.holystone || block == BlocksAether.aether_grass || block == BlocksAether.aether_dirt))
                                {
                                	this.setBlockWithOffset(k2, l2, i3, Blocks.AIR.getDefaultState());
                                }
                            }

                        }
                    }

                }
            }

        }
	}

}