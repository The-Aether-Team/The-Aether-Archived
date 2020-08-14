package com.gildedgames.the_aether.api.moa;

import net.minecraft.util.ResourceLocation;

public class MoaProperties {

	private int maxJumps;

	private float moaSpeed;

	private ResourceLocation location = null;

	public MoaProperties(int maxJumps, float moaSpeed) {
		this.maxJumps = maxJumps;
		this.moaSpeed = moaSpeed;
	}

	public MoaProperties(int maxJumps, float moaSpeed, ResourceLocation location) {
		this(maxJumps, moaSpeed);

		this.location = location;
	}

	public int getMaxJumps() {
		return this.maxJumps;
	}

	public float getMoaSpeed() {
		return this.moaSpeed;
	}

	public boolean hasCustomTexture() {
		return this.location != null;
	}

	public ResourceLocation getCustomTexture(boolean isSaddled, boolean isBeingRidden) {
		return this.location;
	}

}