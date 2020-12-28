package com.gildedgames.the_aether.world.biome;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.world.AetherWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class BiomesAether
{
    public static IForgeRegistry<Biome> biomeRegistry;

    public static void initialization()
    {
        register(AetherWorld.aether_biome, "aether_highlands");
        BiomeStorage.addBiome(AetherWorld.aether_biome, 50);
        BiomeDictionary.addTypes(AetherWorld.aether_biome, BiomeDictionary.Type.VOID, BiomeDictionary.Type.COLD, BiomeDictionary.Type.MAGICAL);
    }

    public static void register(Biome biome, String registryName)
    {
        biomeRegistry.register(biome.setRegistryName(Aether.locate(registryName)));
    }
}
