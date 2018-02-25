package com.legacy.aether.world.gen;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import com.legacy.aether.world.gen.components.ComponentBlueAercloud;

public class MapGenBlueAercloud extends MapGenStructure
{

    public MapGenBlueAercloud()
    {
    }

	@Override
	public String getStructureName()
	{
		return "aether_legacy:blue_aercloud";
	}

	@Override
    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        this.world = worldIn;

        int j = pos.getX() >> 4;
        int k = pos.getZ() >> 4;

        for (int l = 0; l <= 1000; ++l)
        {
            for (int i1 = -l; i1 <= l; ++i1)
            {
                boolean flag = i1 == -l || i1 == l;

                for (int j1 = -l; j1 <= l; ++j1)
                {
                    boolean flag1 = j1 == -l || j1 == l;

                    if (flag || flag1)
                    {
                        int k1 = j + i1;
                        int l1 = k + j1;

                        if (this.canSpawnStructureAtCoords(k1, l1) && (!findUnexplored || !worldIn.isChunkGeneratedAt(k1, l1)))
                        {
                            return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
                        }
                    }
                }
            }
        }

        return null;
    }

	@Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
		return this.rand.nextInt(26) == 0;
    }

    public synchronized boolean generateStructure(World worldIn, Random randomIn, ChunkPos chunkCoord)
    {
        return super.generateStructure(worldIn, randomIn, chunkCoord);
    }

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) 
	{
		return new Start(this.world, this.rand, chunkX, chunkZ);
	}

    public static class Start extends StructureStart
    {
    	private int xTendency, zTendency;

        public Start()
        {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            this.create(worldIn, random, chunkX, chunkZ);
        }

        private void create(World worldIn, Random random, int chunkX, int chunkZ)
        {
            random.setSeed(worldIn.getSeed());
            long i = random.nextLong();
            long j = random.nextLong();
            long k = (long)chunkX * i;
            long l = (long)chunkZ * j;
            random.setSeed(k ^ l ^ worldIn.getSeed());

            this.xTendency = random.nextInt(3) - 1;
            this.zTendency = random.nextInt(3) - 1;

            this.components.add(new ComponentBlueAercloud((chunkX << 4) + 2, random.nextInt(64) + 32, (chunkZ << 4) + 2, this.xTendency, this.zTendency));
            this.updateBoundingBox();
        }

        @Override
        public void writeToNBT(NBTTagCompound tagCompound)
        {
            super.writeToNBT(tagCompound);

            tagCompound.setInteger("xTendency", this.xTendency);
            tagCompound.setInteger("zTendency", this.zTendency);
        }

        @Override
        public void readFromNBT(NBTTagCompound tagCompound)
        {
            super.readFromNBT(tagCompound);

            this.xTendency = tagCompound.getInteger("zTendency");
            this.zTendency = tagCompound.getInteger("zTendency");
        }

    }

}