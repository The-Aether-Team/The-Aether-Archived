package com.gildedgames.the_aether.world.biome;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.world.AetherWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class BiomesAether
{
    public static IForgeRegistry<Biome> biomeRegistry;

    public static List<Biome> aetherBiomes = new ArrayList<>();

    public static void initialization()
    {
        register(AetherWorld.aether_biome, "aether_highlands");
    }

    public static void register(Biome biome, String registryName)
    {
        biomeRegistry.register(biome.setRegistryName(Aether.locate(registryName)));
        aetherBiomes.add(biome);
    }
}
