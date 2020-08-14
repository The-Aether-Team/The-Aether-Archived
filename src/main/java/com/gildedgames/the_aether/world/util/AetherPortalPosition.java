package com.gildedgames.the_aether.world.util;

import net.minecraft.util.ChunkCoordinates;

public class AetherPortalPosition extends ChunkCoordinates {

	public long lastUpdateTime;

	public AetherPortalPosition(int x, int y, int z, long lastUpdateTime) {
		super(x, y, z);

		this.lastUpdateTime = lastUpdateTime;
	}

}