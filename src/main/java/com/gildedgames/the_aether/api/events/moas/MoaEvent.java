package com.gildedgames.the_aether.api.events.moas;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.gildedgames.the_aether.api.moa.AetherMoaType;

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