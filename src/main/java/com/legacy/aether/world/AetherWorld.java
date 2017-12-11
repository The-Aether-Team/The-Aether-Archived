package com.legacy.aether.world;

import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.world.biome.AetherBiome;

public class AetherWorld
{

	public static Biome aether_biome = new AetherBiome();

	public static DimensionType aether_dimension_type;

	public static void initialization()
	{
		aether_dimension_type = DimensionType.register("AetherI", "_aetherI", AetherConfig.getAetherDimensionID(), AetherWorldProvider.class, false);

		DimensionManager.registerDimension(AetherConfig.getAetherDimensionID(), aether_dimension_type);
	}

}