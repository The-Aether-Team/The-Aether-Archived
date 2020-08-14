package com.gildedgames.the_aether.api.events.accessories;

import com.gildedgames.the_aether.api.accessories.AetherAccessory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class ValidAccessoryEvent extends AetherAccessoryEvent
{

	public ValidAccessoryEvent(EntityPlayer player, AetherAccessory accessory)
	{
		super(accessory);
	}

}