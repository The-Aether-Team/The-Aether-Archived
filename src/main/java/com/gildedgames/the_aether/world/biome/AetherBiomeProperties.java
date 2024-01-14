package com.gildedgames.the_aether.world.biome;

import net.minecraft.world.biome.Biome.BiomeProperties;

public class AetherBiomeProperties extends BiomeProperties
{

	public AetherBiomeProperties()
	{
		super("Aether");
		this.setRainfall(0.0F);
		this.setRainDisabled();
		this.setBaseBiome("aether_highlands");
		this.setWaterColor(0x70DB70);
	}

}