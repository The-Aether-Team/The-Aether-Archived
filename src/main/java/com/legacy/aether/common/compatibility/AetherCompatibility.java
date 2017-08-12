package com.legacy.aether.common.compatibility;

import net.minecraftforge.common.MinecraftForge;

import com.legacy.aether.common.ServerProxy;
import com.legacy.aether.common.entities.util.MoaColor;
import com.legacy.aether.common.entities.util.MoaProperties;
import com.legacy.aether.common.events.AetherEnchantmentEvent;
import com.legacy.aether.common.events.MoaEggEvent;
import com.legacy.aether.common.registry.AetherRegistry;
import com.legacy.aether.common.registry.AetherRegistryEvents;

public class AetherCompatibility 
{

	private static AetherRegistry REGISTRY = new AetherRegistry();

	public static void postInitialization()
	{
		ServerProxy.registerEvent(new AetherRegistryEvents());

		/* THESE GO FIRST BEFORE ANY OTHER MOA COLOR!!! */
		REGISTRY.registerMoaColor(new MoaColor("Blue", 0x7777FF, new MoaProperties(3, 100, 0.3F)));
		REGISTRY.registerMoaColor(new MoaColor("Orange", -0xC3D78 /* -802168 */, new MoaProperties(2, 50, 0.6F)));
		REGISTRY.registerMoaColor(new MoaColor("White", 0XFFFFFF, new MoaProperties(4, 20, 0.3F)));
		REGISTRY.registerMoaColor(new MoaColor("Black", 0x222222, new MoaProperties(8, 5, 0.3F)));

		MinecraftForge.EVENT_BUS.post(new MoaEggEvent.Register(REGISTRY));
		MinecraftForge.EVENT_BUS.post(new AetherEnchantmentEvent.Register(REGISTRY));
	}

	public static AetherRegistry getAetherRegistry()
	{
		return REGISTRY;
	}

}