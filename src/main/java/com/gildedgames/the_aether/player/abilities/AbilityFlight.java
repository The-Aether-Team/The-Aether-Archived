package com.gildedgames.the_aether.player.abilities;

import com.gildedgames.the_aether.api.player.util.IAetherAbility;
import com.gildedgames.the_aether.player.PlayerAether;

public class AbilityFlight implements IAetherAbility
{

	private int flightCount;

	private int maxFlightCount = 52;

	private double flightMod = 1.0D;

	private double maxFlightMod = 15.0D;

	private PlayerAether playerAether;

	public AbilityFlight(PlayerAether player)
	{
		this.playerAether = player;
	}

	@Override
	public boolean shouldExecute()
	{
		return this.playerAether.getAccessoryInventory().isWearingValkyrieSet();
	}

	@Override
	public void onUpdate()
	{
		if (this.playerAether.isJumping())
		{
			if (this.flightMod >= this.maxFlightMod)
			{
				this.flightMod = this.maxFlightMod;
			}

			if (this.flightCount > 2)
			{
				if (this.flightCount < this.maxFlightCount)
				{
					this.flightMod += 0.25D;
					this.playerAether.getEntity().motionY = 0.025D * this.flightMod;
					this.flightCount++;
				}
			}
			else
			{
				this.flightCount++;
			}

		}
		else
		{
			this.flightMod = 1.0D;
		}

		if (this.playerAether.getEntity().onGround)
		{
			this.flightCount = 0;
			this.flightMod = 1.0D;
		}
	}

}