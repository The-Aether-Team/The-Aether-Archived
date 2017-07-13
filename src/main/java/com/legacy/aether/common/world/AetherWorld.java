package com.legacy.aether.common.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;

import com.legacy.aether.common.AetherConfig;
import com.legacy.aether.common.world.biome.AetherBiome;

public class AetherWorld
{

	public static Biome aether_biome;

	public static DimensionType aether_dimension_type;

	public static void initialization()
	{
		aether_biome = new AetherBiome();

		Biome.registerBiome(AetherConfig.getAetherBiomeID(), "aether_legacy:aether_highlands", aether_biome);

		aether_dimension_type = DimensionType.register("AetherI", "_aetherI", AetherConfig.getAetherDimensionID(), AetherWorldProvider.class, false);

		DimensionManager.registerDimension(AetherConfig.getAetherDimensionID(), aether_dimension_type);
	}

}