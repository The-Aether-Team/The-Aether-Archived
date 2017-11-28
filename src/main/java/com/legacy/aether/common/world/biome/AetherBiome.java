 package com.legacy.aether.common.world.biome;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.entities.hostile.EntityAechorPlant;
import com.legacy.aether.common.entities.hostile.EntityCockatrice;
import com.legacy.aether.common.entities.hostile.EntityWhirlwind;
import com.legacy.aether.common.entities.hostile.EntityZephyr;
import com.legacy.aether.common.entities.passive.EntityAerwhale;
import com.legacy.aether.common.entities.passive.EntitySheepuff;
import com.legacy.aether.common.entities.passive.mountable.EntityAerbunny;
import com.legacy.aether.common.entities.passive.mountable.EntityFlyingCow;
import com.legacy.aether.common.entities.passive.mountable.EntityMoa;
import com.legacy.aether.common.entities.passive.mountable.EntityPhyg;
import com.legacy.aether.common.entities.passive.mountable.EntitySwet;
import com.legacy.aether.common.world.biome.decoration.AetherGenOakTree;
import com.legacy.aether.common.world.biome.decoration.AetherGenSkyrootTree;

public class AetherBiome extends Biome
{

	public AetherBiome()
	{
		super(new AetherBiomeProperties());
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

		this.topBlock = BlocksAether.aether_grass.getDefaultState();
		this.fillerBlock = BlocksAether.holystone.getDefaultState();
	}

	private void addCreatureEntry(ArrayList<SpawnListEntry> list)
	{
		list.add(new SpawnListEntry(EntitySwet.class, 10, 4, 4));
		list.add(new SpawnListEntry(EntityAechorPlant.class, 8, 3, 3));
		list.add(new SpawnListEntry(EntitySheepuff.class, 10, 4, 4));
		list.add(new SpawnListEntry(EntityPhyg.class, 12, 4, 4));
		list.add(new SpawnListEntry(EntityMoa.class, 10, 3, 3));
		list.add(new SpawnListEntry(EntityFlyingCow.class, 10, 4, 4));
		list.add(new SpawnListEntry(EntityAerbunny.class, 11, 3, 3));
		list.add(new SpawnListEntry(EntityWhirlwind.class, 8, 2, 2));
	}

	private void addMobEntry(ArrayList<SpawnListEntry> list)
	{
		list.add(new SpawnListEntry(EntityCockatrice.class, 3, 4, 4));
		list.add(new SpawnListEntry(EntityAerwhale.class, 8, 3, 3));
		list.add(new SpawnListEntry(EntityZephyr.class, 5, 1, 1));
	}

	@Override
    public int getSkyColorByTemp(float currentTemperature)
    {
    	return 0xC0C0FF; // Lavender Blue
    }

	@Override
    public boolean canRain()
    {
    	return false;
    }

    public BiomeDecorator createBiomeDecorator()
    {
    	return new AetherBiomeDecorator();
    }

    public WorldGenAbstractTree genBigTreeChance(Random rand)
    {
        return (WorldGenAbstractTree)(rand.nextInt(20) == 0 ? new AetherGenOakTree() : new AetherGenSkyrootTree(false));
    }

}