 package com.legacy.aether.server.world.biome;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.entities.hostile.EntityAechorPlant;
import com.legacy.aether.server.entities.hostile.EntityCockatrice;
import com.legacy.aether.server.entities.hostile.EntityZephyr;
import com.legacy.aether.server.entities.passive.EntityAerwhale;
import com.legacy.aether.server.entities.passive.EntitySheepuff;
import com.legacy.aether.server.entities.passive.mountable.EntityAerbunny;
import com.legacy.aether.server.entities.passive.mountable.EntityFlyingCow;
import com.legacy.aether.server.entities.passive.mountable.EntityMoa;
import com.legacy.aether.server.entities.passive.mountable.EntityPhyg;
import com.legacy.aether.server.entities.passive.mountable.EntitySwet;
import com.legacy.aether.server.world.biome.decoration.AetherGenOakTree;
import com.legacy.aether.server.world.biome.decoration.AetherGenSkyrootTree;

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

		list.clear();

		this.addFlyingEntry(list);

		this.spawnableCaveCreatureList.addAll(list);

		this.topBlock = BlocksAether.aether_grass.getDefaultState();
		this.fillerBlock = BlocksAether.holystone.getDefaultState();
	}

	private void addCreatureEntry(ArrayList<SpawnListEntry> list)
	{
		list.add(new SpawnListEntry(EntitySwet.class, 20, 4, 4));
		list.add(new SpawnListEntry(EntityAechorPlant.class, 76, 3, 3));
		list.add(new SpawnListEntry(EntitySheepuff.class, 30, 4, 4));
		list.add(new SpawnListEntry(EntityPhyg.class, 39, 4, 4));
		list.add(new SpawnListEntry(EntityMoa.class, 40, 3, 3));
		list.add(new SpawnListEntry(EntityFlyingCow.class, 46, 4, 4));
		list.add(new SpawnListEntry(EntityAerbunny.class, 36, 3, 3));
	}

	private void addMobEntry(ArrayList<SpawnListEntry> list)
	{
		list.add(new SpawnListEntry(EntityCockatrice.class, 60, 1, 2));
	}

	private void addFlyingEntry(ArrayList<SpawnListEntry> list)
	{
		list.add(new SpawnListEntry(EntityAerwhale.class, 3, 1, 3));
		list.add(new SpawnListEntry(EntityZephyr.class, 5, 0, 1));
	}

    public boolean canRain()
    {
    	return false;
    }

    public Biome.TempCategory getTempCategory()
    {
    	return TempCategory.COLD;
    }

    public BiomeDecorator createBiomeDecorator()
    {
    	return new AetherBiomeDecorator();
    }

    public WorldGenAbstractTree genBigTreeChance(Random rand)
    {
        return (WorldGenAbstractTree)(rand.nextInt(20) == 0 ? new AetherGenOakTree() : new AetherGenSkyrootTree());
    }

}