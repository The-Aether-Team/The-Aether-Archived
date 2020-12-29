package com.gildedgames.the_aether.world.biome;

import java.util.ArrayList;
import java.util.Random;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.entities.hostile.EntityAechorPlant;
import com.gildedgames.the_aether.entities.hostile.EntityCockatrice;
import com.gildedgames.the_aether.entities.hostile.EntityWhirlwind;
import com.gildedgames.the_aether.entities.hostile.EntityZephyr;
import com.gildedgames.the_aether.entities.passive.EntityAerwhale;
import com.gildedgames.the_aether.entities.passive.EntitySheepuff;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.entities.passive.mountable.EntityAerbunny;
import com.gildedgames.the_aether.entities.passive.mountable.EntityFlyingCow;
import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import com.gildedgames.the_aether.entities.passive.mountable.EntityPhyg;
import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;
import com.gildedgames.the_aether.world.biome.decoration.AetherGenOakTree;
import com.gildedgames.the_aether.world.biome.decoration.AetherGenSkyrootTree;

public class AetherBiome extends BiomeGenBase {

	@SuppressWarnings("unchecked")
	public AetherBiome() {
		super(AetherConfig.getAetherBiomeID());

		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();

		ArrayList<SpawnListEntry> list = new ArrayList<SpawnListEntry>();

		this.addCreatureEntry(list);

		this.spawnableCreatureList.addAll(list);

		list.clear();

		this.addMobEntry(list);

		this.spawnableMonsterList.addAll(list);

		list.clear();

		this.topBlock = BlocksAether.aether_grass;
		this.fillerBlock = BlocksAether.holystone;

		this.setBiomeName("Aether");
		this.setDisableRain();
		this.setColor(0);
	}

	private void addCreatureEntry(ArrayList<SpawnListEntry> list)
	{
		list.add(new SpawnListEntry(EntityAerwhale.class, 12, 1, 1));
		list.add(new SpawnListEntry(EntityPhyg.class, 12, 4, 4));
		list.add(new SpawnListEntry(EntitySheepuff.class, 10, 4, 4));
		list.add(new SpawnListEntry(EntityFlyingCow.class, 8, 4, 4));
		list.add(new SpawnListEntry(EntityAerbunny.class, 6, 2, 3));
		list.add(new SpawnListEntry(EntityMoa.class, 5, 2, 3));
	}

	private void addMobEntry(ArrayList<SpawnListEntry> list)
	{
		list.add(new SpawnListEntry(EntityCockatrice.class, 100, 4, 4));
		list.add(new SpawnListEntry(EntityZephyr.class, 50, 1, 2));
		list.add(new SpawnListEntry(EntityAechorPlant.class, 20, 2, 3));
		list.add(new SpawnListEntry(EntityWhirlwind.class, 10, 1, 2));
		list.add(new SpawnListEntry(EntitySwet.class, 10, 2, 3));
	}

	@Override
	public void addDefaultFlowers() {
		this.flowers.add(new FlowerEntry(BlocksAether.white_flower, 0, 20));
		this.flowers.add(new FlowerEntry(BlocksAether.purple_flower, 0, 10));
	}

	@Override
	public int getWaterColorMultiplier() {
		return 16777215;
	}

	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0xBCBCFA; // Lavender Blue
	}

	@Override
	public int getBiomeGrassColor(int x, int y, int z) {
		return 0xb1ffcb;
	}

	@Override
	public int getBiomeFoliageColor(int x, int y, int z) {
		return 0xb1ffcb;
	}

	@Override
	public BiomeDecorator createBiomeDecorator() {
		return new AetherBiomeDecorator();
	}

	@Override
	public WorldGenAbstractTree func_150567_a(Random rand) {
		return (WorldGenAbstractTree) (rand.nextInt(20) == 0 ? new AetherGenOakTree() : new AetherGenSkyrootTree(false));
	}

}