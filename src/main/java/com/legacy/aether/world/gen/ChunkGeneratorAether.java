package com.legacy.aether.world.gen;

import com.legacy.aether.block.BlocksAether;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.AbstractChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.Heightmap.Type;

public class ChunkGeneratorAether extends AbstractChunkGenerator<AetherGenSettings>
{

	private NoiseGeneratorOctaves noiseGen1;

	private NoiseGeneratorOctaves perlinNoise1;

	private final AetherGenSettings settings;

	public ChunkGeneratorAether(IWorld worldIn, BiomeProvider biomeProviderIn)
	{
		super(worldIn, biomeProviderIn);

		SharedSeedRandom random = new SharedSeedRandom(this.seed);
		this.noiseGen1 = new NoiseGeneratorOctaves(random, 16);
		this.perlinNoise1 = new NoiseGeneratorOctaves(random, 8);
		this.settings = new AetherGenSettings();
	}

	public void prepareHeights(int x, int z, IChunk primer)
	{
		double[] buffer = new double[3366];

		buffer = this.setupNoiseGenerators(buffer, x * 2, z * 2);

		MutableBlockPos pos = new MutableBlockPos();

		for (int i1 = 0; i1 < 2; ++i1)
		{
			for (int j1 = 0; j1 < 2; ++j1)
			{
				for (int k1 = 0; k1 < 32; ++k1)
				{
					double d1 = buffer[(i1 * 3 + j1) * 33 + k1];
					double d2 = buffer[(i1 * 3 + j1 + 1) * 33 + k1];
					double d3 = buffer[((i1 + 1) * 3 + j1) * 33 + k1];
					double d4 = buffer[((i1 + 1) * 3 + j1 + 1) * 33 + k1];
					double d5 = (buffer[(i1 * 3 + j1) * 33 + k1 + 1] - d1) * 0.25D;
					double d6 = (buffer[(i1 * 3 + j1 + 1) * 33 + k1 + 1] - d2) * 0.25D;
					double d7 = (buffer[((i1 + 1) * 3 + j1) * 33 + k1 + 1] - d3) * 0.25D;
					double d8 = (buffer[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 1] - d4) * 0.25D;

					for (int l1 = 0; l1 < 4; ++l1)
					{
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * 0.125D;
						double d13 = (d4 - d2) * 0.125D;

						for (int i2 = 0; i2 < 8; ++i2)
						{
							double d15 = d10;
							double d16 = (d11 - d10) * 0.125D;

							for (int k2 = 0; k2 < 8; ++k2)
							{
								pos.setPos(i2 + i1 * 8, l1 + k1 * 4, k2 + j1 * 8);

								IBlockState filler = Blocks.AIR.getDefaultState();

								if (d15 > 0.0D)
								{
									filler = BlocksAether.HOLYSTONE.getDefaultState();
								}

								primer.setBlockState(pos, filler, false);

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

	@Override
	public void makeBase(IChunk chunkIn)
	{
		ChunkPos chunkpos = chunkIn.getPos();
		SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
		int i = chunkpos.x;
		int j = chunkpos.z;

		sharedseedrandom.setBaseChunkSeed(i, j);

		Biome[] abiome = this.biomeProvider.getBiomeBlock(i * 16, j * 16, 16, 16);

		chunkIn.setBiomes(abiome);

		this.prepareHeights(i, j, chunkIn);
		this.buildSurface(chunkIn, abiome, sharedseedrandom, this.world.getSeaLevel());

		chunkIn.createHeightMap(new Type[] { Type.WORLD_SURFACE_WG, Type.OCEAN_FLOOR_WG });
		chunkIn.setStatus(ChunkStatus.BASE);
	}

	private double[] setupNoiseGenerators(double[] buffer, int x, int z)
	{
		double d = 1368.824D;
		double d1 = 684.412D;
		double[] pnr = this.perlinNoise1.func_202647_a(x, 0, z, 3, 33, 3, d / 80.0D, d1 / 160.0D, d / 80.0D);
		double[] ar = this.noiseGen1.func_202647_a(x, 0, z, 3, 33, 3, d, d1, d);
		double[] br = this.noiseGen1.func_202647_a(x, 0, z, 3, 33, 3, d, d1, d);
		int id = 0;

		for (int j2 = 0; j2 < 3; ++j2)
		{
			for (int l2 = 0; l2 < 3; ++l2)
			{
				for (int j3 = 0; j3 < 33; ++j3)
				{
					double d10 = ar[id] / 512.0D;
					double d11 = br[id] / 512.0D;
					double d12 = (pnr[id] / 10.0D + 1.0D) / 2.0D;
					double d8;

					if (d12 < 0.0D)
					{
						d8 = d10;
					}
					else if (d12 > 1.0D)
					{
						d8 = d11;
					}
					else
					{
						d8 = d10 + (d11 - d10) * d12;
					}

					d8 -= 8.0D;

					double d14;

					if (j3 > 1)
					{
						d14 = (double) ((float) (j3 - 1) / 31.0F);
						d8 = d8 * (1.0D - d14) + -30.0D * d14;
					}

					if (j3 < 8)
					{
						d14 = (double) ((float) (8 - j3) / 7.0F);
						d8 = d8 * (1.0D - d14) + -30.0D * d14;
					}

					buffer[id] = d8;

					++id;
				}
			}
		}

		return buffer;
	}

	@Override
	public void spawnMobs(WorldGenRegion region)
	{
		int i = region.getMainChunkX();
		int j = region.getMainChunkZ();
		Biome biome = region.getChunk(i, j).getBiomes()[0];
		SharedSeedRandom seed = new SharedSeedRandom();

		seed.setDecorationSeed(region.getSeed(), i << 4, j << 4);

		WorldEntitySpawner.performWorldGenSpawning(region, biome, i, j, seed);
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	{
		Biome biome = this.world.getBiome(pos);

		return biome.getSpawns(creatureType);
	}

	@Override
	public int spawnMobs(World worldIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs)
	{
		return 0;
	}

	@Override
	public double[] generateNoiseRegion(int x, int z)
	{
		return new double[273];
	}

	@Override
	public AetherGenSettings getSettings()
	{
		return this.settings;
	}

	@Override
	public int getGroundHeight()
	{
		return 0;
	}

}