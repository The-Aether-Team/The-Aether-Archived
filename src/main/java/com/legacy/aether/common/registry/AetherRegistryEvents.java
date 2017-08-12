package com.legacy.aether.common.registry;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.legacy.aether.common.events.AetherEnchantmentEvent;
import com.legacy.aether.common.events.AetherFreezerEvent;

public class AetherRegistryEvents 
{

	@SubscribeEvent
	public void onRegisterEnchantments(AetherEnchantmentEvent.Register event)
	{
		event.registerEnchantmentHandler(new AetherRegistryHandler());
	}

	@SubscribeEvent
	public void onRegisterFreezables(AetherFreezerEvent.Register event)
	{
		event.registerFreezableHandler(new AetherRegistryHandler());
	}

}