package com.legacy.aether.events.moas;

import com.legacy.aether.api.moa.AetherMoaType;
import com.legacy.aether.tile_entities.TileEntityIncubator;

public class MoaHatchEvent extends MoaEvent
{

	private TileEntityIncubator incubator;

	public MoaHatchEvent(AetherMoaType moaType, TileEntityIncubator incubator)
	{
		super(moaType);

		this.incubator = incubator;
	}

	public TileEntityIncubator getTileEntity()
	{
		return this.incubator;
	}

}