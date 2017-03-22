package com.legacy.aether.common.player.abilities;

import com.legacy.aether.common.player.PlayerAether;

public class AbilityFlight extends Ability
{

	private int flightCount;

	private int maxFlightCount = 52;

	private double flightMod = 1.0D;

	private double maxFlightMod = 15.0D;

	public AbilityFlight(PlayerAether player)
	{
		super(player);
	}

	@Override
	public boolean isEnabled()
	{
		return super.isEnabled() && this.playerAether.isWearingValkyrieSet();
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
					this.player.motionY = 0.025D * this.flightMod;
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

		this.player.fallDistance = -1F;

		if (this.player.onGround)
		{
			this.flightCount = 0;
			this.flightMod = 1.0D;
		}
	}

}