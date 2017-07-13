package com.legacy.aether.common.world.biome;

import net.minecraft.world.biome.Biome.BiomeProperties;

public class AetherBiomeProperties extends BiomeProperties 
{

	public AetherBiomeProperties()
	{
		super("Aether Highlands");
		this.setRainfall(0.0F);
		this.setRainDisabled();
		this.setBaseBiome("aether_highlands");
	}

}