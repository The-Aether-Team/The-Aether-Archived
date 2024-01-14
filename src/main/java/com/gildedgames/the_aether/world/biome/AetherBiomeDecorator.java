package com.gildedgames.the_aether.world.biome;

import java.util.Calendar;
import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.util.EnumCloudType;
import com.gildedgames.the_aether.world.biome.decoration.*;
import com.gildedgames.the_aether.world.biome.decoration.*;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.gildedgames.the_aether.AetherConfig;

public class AetherBiomeDecorator extends BiomeDecorator
{

	public World world;

	public Random rand;

	public Biome aetherBiome;

	public AetherGenClouds clouds = new AetherGenClouds();

	public AetherGenFoilage foilage = new AetherGenFoilage();

	public WorldGenMinable aetherDirtGen = new WorldGenMinable(BlocksAether.aether_dirt.getDefaultState(), 32, (stateIn) -> stateIn.getBlock() == BlocksAether.holystone);

	public WorldGenMinable icestoneGen = new WorldGenMinable(BlocksAether.icestone.getDefaultState(), 16, (stateIn) -> stateIn.getBlock() == BlocksAether.holystone);

	public WorldGenMinable ambrosiumGen = new WorldGenMinable(BlocksAether.ambrosium_ore.getDefaultState(), 16, (stateIn) -> stateIn.getBlock() == BlocksAether.holystone);

	public WorldGenMinable zaniteGen = new WorldGenMinable(BlocksAether.zanite_ore.getDefaultState(), 8, (stateIn) -> stateIn.getBlock() == BlocksAether.holystone);

	public WorldGenMinable gravititeGen = new WorldGenMinable(BlocksAether.gravitite_ore.getDefaultState(), 6, (stateIn) -> stateIn.getBlock() == BlocksAether.holystone);

	public AetherGenSkyrootTree skyroot_tree = new AetherGenSkyrootTree(false);

	public AetherGenDungeonOakTree golden_oak_tree_dungeon = new AetherGenDungeonOakTree();

	public AetherGenQuicksoil quicksoil_patches = new AetherGenQuicksoil();

	public AetherGenFloatingIsland crystal_island = new AetherGenFloatingIsland();

	public AetherGenLiquids liquid_overhang = new AetherGenLiquids();

	public AetherGenHolidayTree holiday_tree = new AetherGenHolidayTree();

	public AetherGenLakes aether_lakes = new AetherGenLakes();

	public WorldGenDoublePlant doubleGrass = new WorldGenDoublePlant();

	public AetherBiomeDecorator()
	{
		super();
	}

	@Override
	public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
	{
		this.doubleGrass.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
		if (this.decorating)
		{
			throw new RuntimeException("Already decorating");
		}
		else
		{
			this.chunkPos = pos;
			this.world = worldIn;
			this.rand = random;
			this.aetherBiome = biome;
			this.genDecorations(biome, worldIn, random);
			this.decorating = false;
		}
	}

	@Override
	protected void genDecorations(Biome biomeGenBaseIn, World worldIn, Random random)
	{
		ChunkPos pos = new ChunkPos(this.chunkPos);

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldIn, random, pos));

		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Pre(worldIn, random, this.chunkPos));

		if (this.shouldSpawn(3) && TerrainGen.generateOre(worldIn, random, this.aetherDirtGen, this.chunkPos, GenerateMinable.EventType.CUSTOM))
		{
			this.spawnOre(this.aetherDirtGen, 20, 128);
		}

		if (TerrainGen.generateOre(worldIn, random, this.icestoneGen, this.chunkPos, GenerateMinable.EventType.CUSTOM))
		{
			this.spawnOre(this.icestoneGen, 10, 128);
		}

		if (TerrainGen.generateOre(worldIn, random, this.ambrosiumGen, this.chunkPos, GenerateMinable.EventType.CUSTOM))
		{
			this.spawnOre(this.ambrosiumGen, 15, 128);
		}

		if (TerrainGen.generateOre(worldIn, random, this.zaniteGen, this.chunkPos, GenerateMinable.EventType.CUSTOM))
		{
			this.spawnOre(this.zaniteGen, 12, 128);
		}

		if (TerrainGen.generateOre(worldIn, random, this.gravititeGen, this.chunkPos, GenerateMinable.EventType.CUSTOM))
		{
			this.spawnOre(this.gravititeGen, 8, 128);
		}

		MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(worldIn, random, this.chunkPos));

		if (TerrainGen.decorate(worldIn, random, pos, EventType.CUSTOM))
		{
			if (AetherConfig.world_gen.pink_aerclouds || Loader.isModLoaded("lost_aether"))
			{
				this.generateClouds(EnumCloudType.Pink, 1, 50, this.nextInt(64) + 110);
			}

			this.generateClouds(EnumCloudType.Golden, 4, 50, this.nextInt(64) + 96);
			this.generateClouds(EnumCloudType.Blue, 8, 26, this.nextInt(64) + 32);
			this.generateClouds(EnumCloudType.Cold, 16, 14, this.nextInt(64) + 64);
			this.generateClouds(EnumCloudType.Green, 4, 70, this.nextInt(64) + 32);
			this.generateClouds(EnumCloudType.Purple, 4, 70, this.nextInt(64) + 32);
		}

		if (TerrainGen.decorate(worldIn, random, pos, EventType.FLOWERS))
		{
			this.generateFoilage(BlocksAether.white_flower.getDefaultState());
			this.generateFoilage(BlocksAether.purple_flower.getDefaultState());
			this.generateFoilage(BlocksAether.berry_bush.getDefaultState());
		}

		if (TerrainGen.decorate(worldIn, random, pos, EventType.TREE))
		{
			if (this.shouldSpawn(2))
			{
				this.getTree().generate(this.world, this.rand, this.world.getHeight(this.chunkPos.add(this.nextInt(16) + 8, 0, this.nextInt(16) + 8)));
			}

			if (this.shouldSpawn(1))
			{
				this.skyroot_tree.generate(this.world, this.rand, this.world.getHeight(this.chunkPos.add(this.nextInt(8) + 8, 0, this.nextInt(8) + 8)));
			}

			if (this.shouldSpawn(37))
			{
				this.crystal_island.generate(this.world, this.rand, this.chunkPos.add(8, this.nextInt(64) + 32, 8));
			}

			for (int i = 0; i < 20; i++)
			{
				this.golden_oak_tree_dungeon.generate(this.world, this.rand, this.world.getHeight(this.chunkPos.add(this.nextInt(16), 0, this.nextInt(16))));
			}

			if ((AetherConfig.world_gen.christmas_time) || (AetherConfig.world_gen.seasonal_christmas &&
					(Calendar.getInstance().get(Calendar.MONTH) + 1 == 12 || Calendar.getInstance().get(Calendar.MONTH) + 1 == 1)))
			{
				if (this.shouldSpawn(15))
				{
					this.holiday_tree.generate(this.world, this.rand, this.world.getHeight(this.chunkPos));
				}
			}
		}

		if (AetherConfig.world_gen.tallgrass_enabled)
		{
			if (TerrainGen.decorate(worldIn, random, pos, EventType.GRASS))
			{
				for (int i3 = 0; i3 < 10; ++i3)
				{
					int j7 = random.nextInt(16) + 8;
					int i11 = random.nextInt(16) + 8;
					int k14 = worldIn.getHeight(this.chunkPos.add(j7, 0, i11)).getY() * 2;

					if (k14 > 0)
					{
						int l17 = random.nextInt(k14);

						biomeGenBaseIn.getRandomWorldGenForGrass(random).generate(worldIn, random, this.chunkPos.add(j7, l17, i11));
					}
				}

				for (int i = 0; i < 7; ++i)
				{
					int j = random.nextInt(16) + 8;
					int k = random.nextInt(16) + 8;
					int l = random.nextInt(worldIn.getHeight(this.chunkPos.add(j, 0, k)).getY() + 32);

					this.doubleGrass.generate(worldIn, random, this.chunkPos.add(j, l, k));
				}
			}
		}

		if (TerrainGen.decorate(worldIn, random, pos, EventType.LAKE_WATER))
		{
			if (this.shouldSpawn(10))
			{
				(new WorldGenLakes(Blocks.WATER)).generate(this.world, this.rand, this.chunkPos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(256), this.rand.nextInt(16) + 8));
			}
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldIn, random, pos));
	}

	public int nextInt(int max)
	{
		return this.rand.nextInt(max);
	}

	public boolean shouldSpawn(int chance)
	{
		return this.nextInt(chance) == 0;
	}

	public WorldGenerator getTree()
	{
		return this.shouldSpawn(13) ? new AetherGenOakTree() : new AetherGenSkyrootTree(true);
	}

	public void generateFoilage(IBlockState block)
	{
		this.foilage.setPlantBlock(block);

		for (int n = 0; n < 2; n++)
		{
			foilage.generate(this.world, this.rand, this.chunkPos.add(this.nextInt(16) + 8, this.nextInt(128), this.nextInt(16) + 8));
		}
	}

	public void generateClouds(EnumCloudType type, int amount, int chance, int y)
	{
		if (this.shouldSpawn(chance))
		{
			this.clouds.setAmount(amount);
			this.clouds.setCloudType(type);

			this.clouds.generate(this.world, this.rand, this.chunkPos.add(0, y, 0));
		}
	}

	public void spawnOre(WorldGenMinable gen, int chance, int y)
	{
		for (int chances = 0; chances < chance; chances++)
		{
			gen.generate(this.world, this.rand, this.chunkPos.add(this.nextInt(16), this.nextInt(y), this.nextInt(16)));
		}
	}

}