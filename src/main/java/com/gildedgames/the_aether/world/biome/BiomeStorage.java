package com.gildedgames.the_aether.world.biome;

import com.gildedgames.the_aether.AetherLogger;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    public static File configDirectory = new File(Loader.instance().getConfigDir() + "/aether-dimension");
    public static File biomesFile = new File(configDirectory + "/biomes.txt");

    public static void handleBiomeConfig()
    {
        generateBiomeConfig();
        readBiomeConfig();
    }

    public static void generateBiomeConfig()
    {
        try
        {
            if (!configDirectory.exists())
            {
                configDirectory.mkdirs();
            }

            if (!biomesFile.exists())
            {
                biomesFile.createNewFile();

                FileWriter biomeWriter = new FileWriter(biomesFile);
                biomeWriter.write("#Format is modId:biomeRegistryName=biomeWeight");
                biomeWriter.write(System.lineSeparator());
                biomeWriter.write("#Example being minecraft:forest=100");
                biomeWriter.close();
            }
        }
        catch (IOException ignore) { }
    }

    public static void readBiomeConfig()
    {
        try
        {
            Scanner biomeReader = new Scanner(biomesFile);

            while (biomeReader.hasNextLine())
            {
                String entry = biomeReader.nextLine();

                if (entry.contains("=") && !entry.contains("#"))
                {
                    String[] entryValues = entry.split("=");

                    if (entryValues.length >= 2)
                    {
                        try
                        {
                            String biomeName = entryValues[0];
                            int biomeWeight = Integer.parseInt(entryValues[1]);

                            for (Map.Entry<ResourceLocation, Biome> biomeEntry : ForgeRegistries.BIOMES.getEntries())
                            {
                                ResourceLocation biomeEntryRegistryName = biomeEntry.getKey();
                                Biome biomeEntryClass = biomeEntry.getValue();

                                if (biomeEntryRegistryName.toString().equals(biomeName))
                                {
                                    addBiome(biomeEntryClass, biomeWeight);
                                    AetherLogger.print("Biome " + biomeName + " added to Aether dimension with weight " + biomeWeight);
                                }
                            }
                        }
                        catch (NullPointerException | NumberFormatException exception)
                        {
                            AetherLogger.printError("Error with biomes.txt config found at: " + Arrays.toString(entryValues));
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException ignore) { }
    }
}
