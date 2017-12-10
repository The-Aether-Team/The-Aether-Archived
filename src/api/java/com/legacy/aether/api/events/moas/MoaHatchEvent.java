package com.legacy.aether.api.events.moas;

import net.minecraft.tileentity.TileEntity;

import com.legacy.aether.api.moa.AetherMoaType;

public class MoaHatchEvent extends MoaEvent
{

	private TileEntity incubator;

	public MoaHatchEvent(AetherMoaType moaType, TileEntity incubator)
	{
		super(moaType);

		this.incubator = incubator;
	}

	public TileEntity getTileEntity()
	{
		return this.incubator;
	}

}