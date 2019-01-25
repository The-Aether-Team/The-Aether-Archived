package com.legacy.aether.player.capability;

import com.legacy.aether.CommonProxy;
import com.legacy.aether.player.PlayerAetherEvents;
import com.legacy.aether.player.perks.AetherRankings;

public class PlayerAetherManager
{

	public static void initialization()
	{
		AetherRankings.initialization();

		CommonProxy.registerEvent(new PlayerAetherEvents());
	}

}