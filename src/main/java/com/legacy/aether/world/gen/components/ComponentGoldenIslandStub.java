package com.legacy.aether.world.gen.components;

import com.legacy.aether.blocks.natural.BlockAetherGrass;
import com.legacy.aether.world.biome.decoration.AetherGenOakTree;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.world.gen.AetherGenUtils;
import com.legacy.aether.world.gen.AetherStructure;

public class ComponentGoldenIslandStub extends AetherStructure
{
    private BlockPos chunkPos;

	private int x, y, z;

	private int l;

    public ComponentGoldenIslandStub()
    {

    }

	public ComponentGoldenIslandStub(int chunkX, int chunkZ, int x, int y, int z, int l)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.l = l;

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
        float f = 1.0F;

        for(int i1 = -l; i1 <= l; i1++)
        {
            for(int k1 = l; k1 >= -l; k1--)
            {
                for(int i2 = -l; i2 <= l; i2++)
                {
                    int k2 = MathHelper.floor((double)i1 / (double)f);
                    int i3 = k1;

                    if(k1 > 5)
                    {
                        i3 = MathHelper.floor((double)i3 * 1.375D);
                        i3 -= 2;
                    }
                    else if(k1 < -5)
                    {
                        i3 = MathHelper.floor((double)i3 * 1.3500000238418579D);
                        i3 += 2;
                    }

                    int k3 = MathHelper.floor((double)i2 / (double)f);

                    if(Math.sqrt(k2 * k2 + i3 * i3 + k3 * k3) <= 8.0D)
                    {
                        if(BlocksAether.isGood(this.getBlockStateWithOffset(i1 + x, k1 + y + 1, i2 + z)) && k1 > 1)
                        {
                        	this.setBlockWithOffset(i1 + x, k1 + y, i2 + z, BlocksAether.aether_grass.getDefaultState().withProperty(BlockAetherGrass.dungeon_block, Boolean.TRUE));
                        	this.setBlockWithOffset(i1 + x, (k1 + y) - 1, i2 + z, BlocksAether.aether_dirt.getDefaultState());
                        	this.setBlockWithOffset(i1 + x, (k1 + y) - (1 + this.random.nextInt(2)), i2 + z, BlocksAether.aether_dirt.getDefaultState());

                            if(k1 >= 4)
                            {
                                int l3 = this.random.nextInt(64);

                                if(l3 == 0)
                                {
                                    //AetherGenUtils.generateGoldenOakTree(this, i1 + x, k1 + y + 1, i2 + z);
                                }
                                else if(l3 == 5)
                                {
                                    if(this.random.nextInt(3) == 0)
                                    {
                                        //new WorldGenLakes(Blocks.FLOWING_WATER).generate(world, random, new BlockPos.MutableBlockPos((i1 + i + random.nextInt(3)) - random.nextInt(3), k1 + j, (i2 + k + random.nextInt(3)) - random.nextInt(3)));
                                    }
                                }
                            }
                        } 
                        else if(BlocksAether.isGood(this.getBlockStateWithOffset(i1 + x, k1 + y, i2 + z)))
                        {
                        	this.setBlockWithOffset(i1 + x, k1 + y, i2 + z, BlocksAether.holystone.getDefaultState());
                        }
                    }
                }

            }
        }

		return true;
	}

}