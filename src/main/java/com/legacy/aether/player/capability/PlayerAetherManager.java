package com.legacy.aether.player.capability;

import com.legacy.aether.CommonProxy;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.player.PlayerAetherEvents;
import com.legacy.aether.player.perks.AetherRankings;

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

		CommonProxy.registerEvent(new PlayerAetherEvents());
	}

}