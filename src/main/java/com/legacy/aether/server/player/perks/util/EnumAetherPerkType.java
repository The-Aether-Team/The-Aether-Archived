package com.legacy.aether.server.player.perks.util;

public enum EnumAetherPerkType
{
	Halo(0), Moa(1);

	private int perkID;

	EnumAetherPerkType(int id)
	{
		this.perkID = id;
	}

	public int getPerkID()
	{
		return this.perkID;
	}

	public static EnumAetherPerkType getPerkByID(int id)
	{
		return id == 0 ? Halo : Moa;
	}
}