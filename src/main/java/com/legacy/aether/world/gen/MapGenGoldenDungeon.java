package com.legacy.aether.world.gen;

import java.util.Random;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

import com.google.common.collect.Sets;
import com.legacy.aether.world.gen.components.ComponentGoldenDungeon;
import com.legacy.aether.world.gen.components.ComponentGoldenIsland;
import com.legacy.aether.world.gen.components.ComponentGoldenIslandStub;

public class MapGenGoldenDungeon extends MapGenStructure
{

    public MapGenGoldenDungeon()
    {
    }

	@Override
	public String getStructureName()
	{
		return "aether_legacy:golden_dungeon";
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
        int i = chunkX >> 4;
        int j = chunkZ >> 4;
        this.rand.setSeed((long)(i ^ j << 4) ^ this.world.getSeed());

        if (this.rand.nextInt(6) != 0)
        {
            return false;
        }
        else if (chunkX != (i << 4) + 4 + this.rand.nextInt(8))
        {
            return false;
        }
        else
        {
            return chunkZ == (j << 4) + 4 + this.rand.nextInt(8);
        }
    }

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) 
	{
		return new Start(this.world, this.rand, chunkX, chunkZ);
	}

    public static class Start extends StructureStart
    {
        private final Set<ChunkPos> processed = Sets.<ChunkPos>newHashSet();
        private boolean wasCreated;

        private int dungeonDirection;
    	private int stubIslandCount;

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

            ComponentGoldenIsland dungeon = new ComponentGoldenIsland((chunkX << 4) + 2, (chunkZ << 4) + 2);

            this.dungeonDirection = random.nextInt(4);
            this.stubIslandCount = 8 + random.nextInt(5);

            this.components.add(dungeon);

            for (int stubIslands = 0; stubIslands < this.stubIslandCount; ++stubIslands)
            {
                float f1 = 0.01745329F;
                float f2 = random.nextFloat() * 360F;
                float f3 = ((random.nextFloat() * 0.125F) + 0.7F) * 24.0F;
                int l4 = MathHelper.floor(Math.cos(f1 * f2) * (double)f3);
                int k5 = -MathHelper.floor(24.0D * (double)random.nextFloat() * 0.29999999999999999D);
                int i6 = MathHelper.floor(-Math.sin(f1 * f2) * (double)f3);

                //this.generateStubIsland(l4, k5, i6, 8);
            	this.components.add(new ComponentGoldenIslandStub((chunkX << 4) + 2, (chunkZ << 4) + 2, l4, k5, i6, 8));
            }

            this.components.add(new ComponentGoldenDungeon((chunkX << 4) + 2, (chunkZ << 4) + 2, this.dungeonDirection));

            this.updateBoundingBox();
            this.wasCreated = true;
        }

        @Override
        public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb)
        {
            if (!this.wasCreated)
            {
                this.components.clear();
                this.create(worldIn, rand, this.getChunkPosX(), this.getChunkPosZ());
            }

            super.generateStructure(worldIn, rand, structurebb);
        }

        @Override
        public boolean isValidForPostProcess(ChunkPos pair)
        {
            return this.processed.contains(pair) ? false : super.isValidForPostProcess(pair);
        }

        @Override
        public void notifyPostProcessAt(ChunkPos pair)
        {
            super.notifyPostProcessAt(pair);
            this.processed.add(pair);
        }

        @Override
        public void writeToNBT(NBTTagCompound tagCompound)
        {
            super.writeToNBT(tagCompound);

            NBTTagList nbttaglist = new NBTTagList();

            for (ChunkPos chunkpos : this.processed)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger("X", chunkpos.x);
                nbttagcompound.setInteger("Z", chunkpos.z);
                nbttaglist.appendTag(nbttagcompound);
            }

            tagCompound.setTag("Processed", nbttaglist);

            tagCompound.setInteger("stubIslandCount", this.stubIslandCount);
            tagCompound.setInteger("dungeonDirection", this.dungeonDirection);
        }

        @Override
        public void readFromNBT(NBTTagCompound tagCompound)
        {
            super.readFromNBT(tagCompound);

            if (tagCompound.hasKey("Processed", 9))
            {
                NBTTagList nbttaglist = tagCompound.getTagList("Processed", 10);

                for (int i = 0; i < nbttaglist.tagCount(); ++i)
                {
                    NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                    this.processed.add(new ChunkPos(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Z")));
                }
            }

            this.stubIslandCount = tagCompound.getInteger("stubIslandCount");
            this.dungeonDirection = tagCompound.getInteger("dungeonDirection");
        }

    }

}