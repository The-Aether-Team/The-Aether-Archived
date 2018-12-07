package com.legacy.aether.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.world.dungeon.BronzeDungeon;
import com.legacy.aether.world.dungeon.util.AetherDungeon;
import com.legacy.aether.world.gen.MapGenGoldenDungeon;
import com.legacy.aether.world.gen.MapGenLargeColdAercloud;
import com.legacy.aether.world.gen.MapGenQuicksoil;
import com.legacy.aether.world.gen.MapGenSilverDungeon;

public class ChunkProviderAether implements IChunkProvider
{

	private Random rand;

	private World worldObj;

	private NoiseGeneratorOctaves noiseGen1, perlinNoise1;

	private double buffer[];

	double pnr[], ar[], br[];

    protected AetherDungeon dungeon_bronze = new BronzeDungeon();

    private MapGenQuicksoil quicksoilGen = new MapGenQuicksoil();

    private MapGenSilverDungeon silverDungeonStructure = new MapGenSilverDungeon();

    private MapGenGoldenDungeon goldenDungeonStructure = new MapGenGoldenDungeon();

    private MapGenLargeColdAercloud largeColdAercloudStructure = new MapGenLargeColdAercloud();

	public ChunkProviderAether(World world, long seed)
	{
		this.worldObj = world;

		this.rand = new Random(seed);

		this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
		this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
	}

	public void setBlocksInChunk(int x, int z, Block[] blocks)
    {
        this.buffer = this.setupNoiseGenerators(this.buffer, x * 2, z * 2);

        for(int i1 = 0; i1 < 2; i1++)
        {
            for(int j1 = 0; j1 < 2; j1++)
            {
                for(int k1 = 0; k1 < 32; k1++)
                {
                    double d1 = this.buffer[(i1 * 3 + j1) * 33 + k1];
                    double d2 = this.buffer[(i1 * 3 + (j1 + 1)) * 33 + k1];
                    double d3 = this.buffer[((i1 + 1) * 3 + j1) * 33 + k1];
                    double d4 = this.buffer[((i1 + 1) * 3 + (j1 + 1)) * 33 + k1];

                    double d5 = (this.buffer[(i1 * 3 + j1) * 33 + (k1 + 1)] - d1) * 0.25D;
                    double d6 = (this.buffer[(i1 * 3 + (j1 + 1)) * 33 + (k1 + 1)] - d2) * 0.25D;
                    double d7 = (this.buffer[((i1 + 1) * 3 + j1) * 33 + (k1 + 1)] - d3) * 0.25D;
                    double d8 = (this.buffer[((i1 + 1) * 3 + (j1 + 1)) * 33 + (k1 + 1)] - d4) * 0.25D;

                    for(int l1 = 0; l1 < 4; l1++)
                    {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.125D;
                        double d13 = (d4 - d2) * 0.125D;

                        for(int i2 = 0; i2 < 8; i2++)
                        {
                            int j2 = i2 + i1 * 8 << 11 | 0 + j1 * 8 << 7 | k1 * 4 + l1;
                            char c = '\200';
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.125D;

                            for(int k2 = 0; k2 < 8; k2++)
                            {
                                Block filler = Blocks.air;

                                if(d15 > 0.0D)
                                {
                                	filler = BlocksAether.holystone;
                                }

                                blocks[j2] = filler;
                                j2 += c;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }

                }

            }

        }

    }

	public void buildSurfaces(int i, int j, Block[] blocks)
    {
        for(int k = 0; k < 16; k++)
        {
            for(int l = 0; l < 16; l++)
            {
                int j1 = -1;
                int i1 = (int)(3.0D + this.rand.nextDouble() * 0.25D);

                Block top = BlocksAether.aether_grass;
                Block filler = BlocksAether.aether_dirt;

                for (int k1 = 127; k1 >= 0; k1--)
				{
                    int l1 = (l * 16 + k) * 128 + k1;

					Block block = blocks[l1];

					if (block == Blocks.air)
					{
						j1 = -1;
					}
					else if (block == BlocksAether.holystone)
					{
						if (j1 == -1)
						{
							if (i1 <= 0)
							{
								top = Blocks.air;
								filler = BlocksAether.holystone;
							}

							j1 = i1;

							if (k1 >= 0)
							{
								blocks[l1] = top;
							}
							else
							{
								blocks[l1] = filler;
							}
						}
						else if (j1 > 0)
						{
							--j1;
							blocks[l1] = filler;
						}
					}
				}
            }
        }
    }

    private double[] setupNoiseGenerators(double buffer[], int x, int z)
    {
        if(buffer == null)
        {
        	buffer = new double[3366];
        }

        double d = 1368.824D;
        double d1 = 684.41200000000003D;

        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, x, 0, z, 3, 33, 3, d / 80D, d1 / 160D, d / 80D);
        this.ar = this.noiseGen1.generateNoiseOctaves(this.ar, x, 0, z, 3, 33, 3, d, d1, d);
        this.br = this.noiseGen1.generateNoiseOctaves(this.br, x, 0, z, 3, 33, 3, d, d1, d);

        int id = 0;

        for(int j2 = 0; j2 < 3; j2++)
        {
            for(int l2 = 0; l2 < 3; l2++)
            {
                for(int j3 = 0; j3 < 33; j3++)
                {
                	double d8;

                    double d10 = this.ar[id] / 512D;
                    double d11 = this.br[id] / 512D;
                    double d12 = (this.pnr[id] / 10D + 1.0D) / 2D;

                    if(d12 < 0.0D)
                    {
                        d8 = d10;
                    } 
                    else if(d12 > 1.0D)
                    {
                        d8 = d11;
                    }
                    else
                    {
                        d8 = d10 + (d11 - d10) * d12;
                    }

                    d8 -= 8D;

                    if(j3 > 33 - 32)
                    {
                        double d13 = (float)(j3 - (33 - 32)) / ((float)32 - 1.0F);
                        d8 = d8 * (1.0D - d13) + -30D * d13;
                    }

                    if(j3 < 8)
                    {
                        double d14 = (float)(8 - j3) / ((float)8 - 1.0F);
                        d8 = d8 * (1.0D - d14) + -30D * d14;
                    }

                    buffer[id] = d8;

                    id++;
                }

            }

        }

        return buffer;
    }

	@Override
	public Chunk provideChunk(int x, int z) 
	{
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        Block[] ablock = new Block[32768];

        this.setBlocksInChunk(x, z, ablock);
        this.buildSurfaces(x, z, ablock);

        this.quicksoilGen.func_151539_a(this, this.worldObj, x, z, ablock);

        this.largeColdAercloudStructure.func_151539_a(this, this.worldObj, x, z, ablock);

        this.silverDungeonStructure.func_151539_a(this, this.worldObj, x, z, ablock);
        this.goldenDungeonStructure.func_151539_a(this, this.worldObj, x, z, ablock);

        Chunk chunk = new Chunk(this.worldObj, ablock, x, z);
        chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) 
	{
		return this.worldObj.getBiomeGenForCoords(x, z).getSpawnableList(creatureType);
	}

	@Override
	public void recreateStructures(int x, int z)
	{
        this.largeColdAercloudStructure.func_151539_a(this, this.worldObj, x, z, (Block[])null);
        this.silverDungeonStructure.func_151539_a(this, this.worldObj, x, z, (Block[])null);
        this.goldenDungeonStructure.func_151539_a(this, this.worldObj, x, z, (Block[])null);
	}

	@Override
	public ChunkPosition func_147416_a(World worldIn, String structureName, int x, int y, int z) //getNearestStructurePos
	{
		return null;
	}

	@Override
	public void populate(IChunkProvider provider, int chunkX, int chunkZ)
	{
		int x = chunkX * 16;
		int z = chunkZ * 16;

		BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(x + 16, z + 16);

        this.rand.setSeed(this.worldObj.getSeed());
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)x * k + (long)z * l ^ this.worldObj.getSeed());

		this.largeColdAercloudStructure.generateStructuresInChunk(this.worldObj, this.rand, chunkX, chunkZ);
        this.silverDungeonStructure.generateStructuresInChunk(this.worldObj, this.rand, chunkX, chunkZ);
        this.goldenDungeonStructure.generateStructuresInChunk(this.worldObj, this.rand, chunkX, chunkZ);

		biome.decorate(this.worldObj, this.rand, x, z);

		if (this.rand.nextInt(10) == 0)
        {
	        this.dungeon_bronze.generate(this.worldObj, this.rand, x + this.rand.nextInt(16), this.rand.nextInt(64) + 32, z + this.rand.nextInt(16));
        }

		SpawnerAnimals.performWorldGenSpawning(this.worldObj, biome, x + 8, z + 8, 16, 16, this.rand);
	}

	@Override
	public Chunk loadChunk(int chunkX, int chunkZ)
	{
		return this.provideChunk(chunkX, chunkZ);
	}

	@Override
	public boolean chunkExists(int chunkX, int chunkZ) 
	{
		return true;
	}

	@Override
	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) 
	{
		return true;
	}

	@Override
	public boolean unloadQueuedChunks() 
	{
		return true;
	}

	@Override
	public boolean canSave() 
	{
		return true;
	}

	@Override
	public String makeString()
	{
		return "AetherRandomLevelSource";
	}

	@Override
	public int getLoadedChunkCount() 
	{
		return 0;
	}

	@Override
	public void saveExtraData() 
	{

	}

}