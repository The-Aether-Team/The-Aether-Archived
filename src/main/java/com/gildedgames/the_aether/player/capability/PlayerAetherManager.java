package com.gildedgames.the_aether.player.capability;

import com.gildedgames.the_aether.CommonProxy;
import com.gildedgames.the_aether.player.PlayerAetherEvents;
import com.gildedgames.the_aether.player.perks.AetherRankings;

public class PlayerAetherManager
{

	public static void initialization()
	{
		AetherRankings.initialization();

		CommonProxy.registerEvent(new PlayerAetherEvents());
	}

}