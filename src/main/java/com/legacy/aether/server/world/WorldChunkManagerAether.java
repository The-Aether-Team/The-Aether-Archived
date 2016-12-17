package com.legacy.aether.server.world;

import net.minecraft.world.biome.BiomeProviderSingle;

public class WorldChunkManagerAether extends BiomeProviderSingle
{

	public WorldChunkManagerAether()
	{
		super(AetherWorld.aether_biome);
	}

}