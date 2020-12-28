package com.gildedgames.the_aether.world.biome;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class BiomeStorage
{
    private static final List<WeightedBiomeEntry> spawnableBiomes = new java.util.ArrayList<>();

    public static List<WeightedBiomeEntry> getSpawnableBiomes()
    {
        return spawnableBiomes;
    }

    public static void addBiome(Biome biome, int weight)
    {
        spawnableBiomes.add(new WeightedBiomeEntry(biome, weight));
    }

    public static class WeightedBiomeEntry extends WeightedRandom.Item
    {
        public final Biome biome;

        WeightedBiomeEntry(Biome biome, int weight)
        {
            super(weight);
            this.biome = biome;
        }
    }
}
