package com.legacy.aether.api.events.moas;

import com.legacy.aether.api.moa.AetherMoaType;

import cpw.mods.fml.common.eventhandler.Event;

public class MoaEvent extends Event
{

	private AetherMoaType moaType;

	public MoaEvent(AetherMoaType moaType)
	{
		this.moaType = moaType;
	}

	public AetherMoaType getMoaType()
	{
		return this.moaType;
	}

}