package com.legacy.aether.world.gen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import com.legacy.aether.world.gen.components.ComponentLargeColdAercloud;

public class MapGenLargeColdAercloud extends MapGenStructure
{

    public MapGenLargeColdAercloud()
    {

    }

	@Override
	public String getStructureName()
	{
		return "aether_legacy:cold_aercloud";
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
		return this.rand.nextInt(50) == 0;
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
            this.components.add(new ComponentLargeColdAercloud(random, (chunkX << 4) + 2, 0, (chunkZ << 4) + 2));

            this.updateBoundingBox();
        }

    }

}