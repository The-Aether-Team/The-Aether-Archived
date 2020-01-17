package com.legacy.aether.blocks.util;

import net.minecraft.util.IStringSerializable;

public enum EnumGrassType implements IStringSerializable
{

	Aether(0, "aether_grass"),
	Arctic(1, "arctic_aether_grass"),
	Magnetic(2, "magnetic_aether_grass"),
	Irradiated(3, "irradiated_aether_grass");

	private int meta;

	private String unlocalizedName;

    public static final EnumGrassType[] lookup = EnumGrassType.values();

	EnumGrassType(int meta, String unlocalizedName)
	{
		this.meta = meta;
		this.unlocalizedName = unlocalizedName;
	}

	public static EnumGrassType getType(int meta)
	{
		if (meta < 0 || meta > lookup.length) return EnumGrassType.Aether;
		return lookup[meta];
	}

	public int getMeta() { return this.meta; }

    public String toString() { return this.unlocalizedName; }

    public String getName() { return this.unlocalizedName; }
}