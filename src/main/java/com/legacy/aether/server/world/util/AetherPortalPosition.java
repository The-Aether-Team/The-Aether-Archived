package com.legacy.aether.server.world.util;

import net.minecraft.util.math.BlockPos;

public class AetherPortalPosition extends BlockPos
{

	public long lastUpdateTime;

	public AetherPortalPosition(BlockPos pos, long lastUpdateTime)
	{
		super(pos.getX(), pos.getY(), pos.getZ());

		this.lastUpdateTime = lastUpdateTime;
	}

}