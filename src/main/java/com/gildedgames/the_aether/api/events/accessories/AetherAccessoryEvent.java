package com.gildedgames.the_aether.api.events.accessories;

import com.gildedgames.the_aether.api.accessories.AetherAccessory;
import net.minecraftforge.fml.common.eventhandler.Event;

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