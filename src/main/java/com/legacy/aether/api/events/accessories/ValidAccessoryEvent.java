package com.legacy.aether.api.events.accessories;

import net.minecraft.entity.player.EntityPlayer;

import com.legacy.aether.api.accessories.AetherAccessory;

import cpw.mods.fml.common.eventhandler.Cancelable;

@Cancelable
public class ValidAccessoryEvent extends AetherAccessoryEvent
{

	public ValidAccessoryEvent(EntityPlayer player, AetherAccessory accessory) 
	{
		super(accessory);
	}

}