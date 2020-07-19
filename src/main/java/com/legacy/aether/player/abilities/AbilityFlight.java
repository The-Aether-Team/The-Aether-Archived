package com.legacy.aether.player.abilities;

import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.api.player.util.IAetherAbility;

public class AbilityFlight implements IAetherAbility {

	private int flightCount;

	private int maxFlightCount = 52;

	private double flightMod = 1.0D;

	private double maxFlightMod = 15.0D;

	private final IPlayerAether player;

	public AbilityFlight(IPlayerAether player) {
		this.player = player;
	}

	@Override
	public boolean shouldExecute() {
		return this.player.getAccessoryInventory().isWearingValkyrieSet();
	}

	@Override
	public void onUpdate() {
		if (this.player.isJumping()) {
			if (this.flightMod >= this.maxFlightMod) {
				this.flightMod = this.maxFlightMod;
			}

			if (this.flightCount > 2) {
				if (this.flightCount < this.maxFlightCount) {
					this.flightMod += 0.25D;
					this.player.getEntity().motionY = 0.025D * this.flightMod;
					this.flightCount++;
				}
			} else {
				this.flightCount++;
			}

		} else {
			this.flightMod = 1.0D;
		}

		if (this.player.getEntity().onGround) {
			this.flightCount = 0;
			this.flightMod = 1.0D;
		}
	}

}