package com.legacy.aether.world.biome;

import java.util.Random;

import com.legacy.aether.world.biome.decoration.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.blocks.BlocksAether;

public class AetherBiomeDecorator extends BiomeDecorator {

	public World world;

	public Random rand;

	public BiomeGenBase aetherBiome;

	public AetherGenFoilage foilage = new AetherGenFoilage();

	public AetherGenMinable ores = new AetherGenMinable();

	public AetherGenSkyrootTree skyroot_tree = new AetherGenSkyrootTree(false);

	public AetherGenDungeonOakTree golden_oak_tree_dungeon = new AetherGenDungeonOakTree();

	public AetherGenQuicksoil quicksoil_patches = new AetherGenQuicksoil();

	public AetherGenFloatingIsland crystal_island = new AetherGenFloatingIsland();

	public AetherGenLiquids liquid_overhang = new AetherGenLiquids();

	public AetherGenHolidayTree holiday_tree = new AetherGenHolidayTree();

	public AetherGenLakes aether_lakes = new AetherGenLakes();

	public AetherGenClouds clouds = new AetherGenClouds();

	private final WorldGenDoublePlant double_grass = new WorldGenDoublePlant();

	public AetherBiomeDecorator() {
		super();
	}

	@Override
	public void decorateChunk(World worldIn, Random random, BiomeGenBase biome, int x, int z) {
		if (this.world != null) {
			System.out.println("Already decorating");
		} else {
			this.world = worldIn;
			this.rand = random;
			this.chunk_X = x;
			this.chunk_Z = z;
			this.aetherBiome = biome;
			this.genDecorations(biome);
			this.world = null;
			this.rand = null;
		}
	}

	@Override
	protected void genDecorations(BiomeGenBase biome) {
		this.generateClouds(2, 4, 50, this.nextInt(64) + 96);
		this.generateClouds(1, 8, 26, this.nextInt(64) + 32);
		this.generateClouds(0, 16, 14, this.nextInt(64) + 64);

		if (this.shouldSpawn(37)) {
			this.crystal_island.generate(this.world, this.rand, this.chunk_X + 8, this.nextInt(64) + 32, this.chunk_Z + 8);
		}

		if (this.shouldSpawn(3)) {
			this.spawnOre(BlocksAether.aether_dirt, 32, 20, 128);
		}

		this.generateFoilage(BlocksAether.white_flower);
		this.generateFoilage(BlocksAether.purple_flower);

		this.spawnOre(BlocksAether.icestone, 16, 10, 128);
		this.spawnOre(BlocksAether.ambrosium_ore, 16, 15, 128);
		this.spawnOre(BlocksAether.zanite_ore, 8, 15, 64);
		this.spawnOre(BlocksAether.gravitite_ore, 6, 8, 32);

		this.generateFoilage(BlocksAether.berry_bush);

		for (int i3 = 0; i3 < 3; ++i3) {
			int x = this.chunk_X + this.nextInt(16) + 8;
			int z = this.chunk_Z + this.nextInt(16) + 8;
			int y = this.world.getHeightValue(x, z);

			this.getTree().generate(this.world, this.rand, x, y, z);
		}

		if (AetherConfig.shouldLoadHolidayContent()) {
			if (this.shouldSpawn(15)) {
				int x = this.chunk_X + 8;
				int z = this.chunk_Z + 8;
				int y = this.world.getHeightValue(x, z);
				this.holiday_tree.generate(this.world, this.rand, x, y, z);
			}
		}

		for (int i = 0; i < 25; i++)
		{
			int x = this.chunk_X + this.nextInt(16);
			int z = this.chunk_Z + this.nextInt(16);
			int y = this.world.getHeightValue(x, z);
			this.golden_oak_tree_dungeon.generate(this.world, this.rand, x, y, z);
		}

		if (AetherConfig.tallgrassEnabled()) {
			for (int i3 = 0; i3 < 10; ++i3) {
				int j7 = this.chunk_X + this.rand.nextInt(16) + 8;
				int i11 = this.chunk_Z + this.rand.nextInt(16) + 8;
				int k14 = this.world.getHeight() * 2;

				if (k14 > 0) {
					int l17 = this.rand.nextInt(k14);
					this.aetherBiome.getRandomWorldGenForGrass(this.rand).generate(this.world, this.rand, j7, l17, i11);
				}
			}

			if (net.minecraftforge.event.terraingen.TerrainGen.decorate(this.world, this.rand, this.chunk_X, this.chunk_Z, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
				for (int i = 0; i < 7; ++i) {
					int j = this.chunk_X + this.rand.nextInt(16) + 8;
					int k = this.chunk_Z + this.rand.nextInt(16) + 8;
					int l = this.rand.nextInt(this.world.getHeight() + 32);

					this.double_grass.func_150548_a(2);
					this.double_grass.generate(this.world, rand, j, l, k);
				}
		}

		if (this.shouldSpawn(10)) {
			(new WorldGenLakes(Blocks.water)).generate(this.world, this.rand, this.chunk_X + this.rand.nextInt(16) + 8, this.rand.nextInt(256), this.chunk_Z + this.rand.nextInt(16) + 8);
		}
	}

	public int nextInt(int max) {
		return this.rand.nextInt(max);
	}

	public boolean shouldSpawn(int chance) {
		return this.nextInt(chance) == 0;
	}

	public WorldGenerator getTree() {
		return this.shouldSpawn(30) ? new AetherGenOakTree() : new AetherGenSkyrootTree(true);
	}

	public void generateFoilage(Block block) {
		this.foilage.setPlantBlock(block);

		for (int n = 0; n < 2; n++) {
			this.foilage.generate(this.world, this.rand, this.chunk_X + this.nextInt(16) + 8, this.nextInt(128), this.chunk_Z + this.nextInt(16) + 8);
		}
	}

	public void generateClouds(int meta, int amount, int chance, int y) {
		if (this.shouldSpawn(chance)) {
			this.clouds.setCloudMeta(meta);
			this.clouds.setCloudAmount(amount);

			this.clouds.generate(this.world, this.rand, this.chunk_X + this.nextInt(16), this.nextInt(y), this.chunk_Z + this.nextInt(16));
		}
	}

	public void spawnOre(Block state, int size, int chance, int y) {
		this.ores.setSize(size);
		this.ores.setBlock(state);

		for (int chances = 0; chances < chance; chances++) {
			this.ores.generate(this.world, this.rand, this.chunk_X + this.nextInt(16), this.nextInt(y), this.chunk_Z + this.nextInt(16));
		}
	}

}