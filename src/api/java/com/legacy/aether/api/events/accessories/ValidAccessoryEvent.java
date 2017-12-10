package com.legacy.aether.api.events.accessories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import com.legacy.aether.api.accessories.AetherAccessory;

@Cancelable
public class ValidAccessoryEvent extends AetherAccessoryEvent
{

	public ValidAccessoryEvent(EntityPlayer player, AetherAccessory accessory) 
	{
		super(accessory);
	}

}