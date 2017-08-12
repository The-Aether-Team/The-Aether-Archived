package com.legacy.aether.common.entities.util;

import net.minecraft.util.ResourceLocation;

public class MoaProperties
{

	private int maxJumps, moaChances;

	private float moaSpeed;

	private ResourceLocation location = null;

	public MoaProperties(int maxJumps, int moaChances, float moaSpeed)
	{
		this.maxJumps = maxJumps;
		this.moaChances = moaChances;
		this.moaSpeed = moaSpeed;
	}

	public MoaProperties(int maxJumps, int moaChances, float moaSpeed, ResourceLocation location)
	{
		this(maxJumps, moaChances, moaSpeed);

		this.location = location;
	}

	public int getMaxJumps()
	{
		return this.maxJumps;
	}

	public int getMoaChances()
	{
		return this.moaChances;
	}

	public float getMoaSpeed()
	{
		return this.moaSpeed;
	}

	public boolean hasCustomTexture()
	{
		return this.location != null;
	}

	public ResourceLocation getCustomTexture(boolean isSaddled)
	{
		return this.location;
	}

}