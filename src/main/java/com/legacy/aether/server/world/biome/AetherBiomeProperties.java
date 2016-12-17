package com.legacy.aether.server.world.biome;

import net.minecraft.world.biome.Biome.BiomeProperties;

public class AetherBiomeProperties extends BiomeProperties 
{

	public AetherBiomeProperties()
	{
		super("Aether Highlands");
		this.setRainDisabled();
		this.setBaseBiome("aether_highlands");
	}

}