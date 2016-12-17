package com.legacy.aether.server.player.capability;

import com.legacy.aether.server.ServerProxy;
import com.legacy.aether.server.player.PlayerAether;
import com.legacy.aether.server.player.PlayerAetherEvents;
import com.legacy.aether.server.player.perks.AetherRankings;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class PlayerAetherManager
{

	@CapabilityInject(PlayerAether.class)
	public static Capability<PlayerAether> AETHER_PLAYER = null;

	public static void initialization()
	{
		CapabilityManager.INSTANCE.register(PlayerAether.class, new PlayerAetherStorage(), PlayerAether.class);

		AetherRankings.initialization();

		ServerProxy.registerEvent(new PlayerAetherEvents());
	}

}