 package com.gildedgames.the_aether.world.biome;

import java.util.ArrayList;
import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.entities.hostile.EntityAechorPlant;
import com.gildedgames.the_aether.entities.hostile.EntityCockatrice;
import com.gildedgames.the_aether.entities.hostile.EntityWhirlwind;
import com.gildedgames.the_aether.entities.hostile.EntityZephyr;
import com.gildedgames.the_aether.entities.passive.EntityAerwhale;
import com.gildedgames.the_aether.entities.passive.EntitySheepuff;
import com.gildedgames.the_aether.world.biome.decoration.AetherGenOakTree;
import com.gildedgames.the_aether.world.biome.decoration.AetherGenSkyrootTree;
import com.gildedgames.the_aether.entities.passive.mountable.EntityAerbunny;
import com.gildedgames.the_aether.entities.passive.mountable.EntityFlyingCow;
import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import com.gildedgames.the_aether.entities.passive.mountable.EntityPhyg;
import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

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

		this.topBlock = BlocksAether.aether_grass.getDefaultState();
		this.fillerBlock = BlocksAether.holystone.getDefaultState();
	}

	private void addCreatureEntry(ArrayList<SpawnListEntry> list)
	{
		list.add(new SpawnListEntry(EntitySwet.class, 30, 3, 4));
		list.add(new SpawnListEntry(EntityAechorPlant.class, 29, 1, 1));
		list.add(new SpawnListEntry(EntityPhyg.class, 12, 4, 4));
		list.add(new SpawnListEntry(EntityAerbunny.class, 11, 3, 3));
		list.add(new SpawnListEntry(EntitySheepuff.class, 10, 4, 4));
		list.add(new SpawnListEntry(EntityMoa.class, 10, 3, 3));
		list.add(new SpawnListEntry(EntityFlyingCow.class, 10, 4, 4));
	}

	private void addMobEntry(ArrayList<SpawnListEntry> list)
	{
		list.add(new SpawnListEntry(EntityWhirlwind.class, 8, 2, 2));
		list.add(new SpawnListEntry(EntityCockatrice.class, 4, 4, 4));
		list.add(new SpawnListEntry(EntityZephyr.class, 3, 1, 1));
		list.add(new SpawnListEntry(EntityAerwhale.class, 2, 1, 1));
	}

	@Override
    public int getSkyColorByTemp(float currentTemperature)
    {
    	return 0xBCBCFA; // Lavender Blue
    }
	
	@Override
    public int getGrassColorAtPos(BlockPos pos)
    {
        return 0xb1ffcb;
    }
	
	@Override
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return 0xb1ffcb;
    }

	@Override
    public boolean canRain()
    {
    	return false;
    }

	@Override
    public BiomeDecorator createBiomeDecorator()
    {
    	return new AetherBiomeDecorator();
    }

	@Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return (WorldGenAbstractTree)(rand.nextInt(20) == 0 ? new AetherGenOakTree() : new AetherGenSkyrootTree(false));
    }

}