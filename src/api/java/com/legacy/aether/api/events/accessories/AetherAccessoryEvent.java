package com.legacy.aether.api.events.accessories;

import net.minecraftforge.fml.common.eventhandler.Event;

import com.legacy.aether.api.accessories.AetherAccessory;

public class AetherAccessoryEvent extends Event
{

	private AetherAccessory accessory;

	public AetherAccessoryEvent(AetherAccessory accessory)
	{
		this.accessory = accessory;
	}

	public AetherAccessory getAetherAccessory()
	{
		return this.accessory;
	}

}