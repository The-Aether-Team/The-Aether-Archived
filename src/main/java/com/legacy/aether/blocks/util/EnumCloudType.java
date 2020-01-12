package com.legacy.aether.blocks.util;

import net.minecraft.util.IStringSerializable;

public enum EnumCloudType implements IStringSerializable
{

	Cold(0, "cold_aercloud"), Blue(1, "blue_aercloud"), Golden(2, "golden_aercloud"), Pink(3, "pink_aercloud"), Green(4, "green_aercloud"), Storm(5, "storm_aercloud"), Purple(6, "purple_aercloud");

	private int meta;

	private String unlocalizedName;

    public static final EnumCloudType[] lookup = new EnumCloudType[values().length];

	EnumCloudType(int meta, String unlocalizedName)
	{
		this.meta = meta;
		this.unlocalizedName = unlocalizedName;
	}

	public static EnumCloudType getType(int meta)
	{
		return meta == 1 ? Blue : meta == 2 ? Golden : meta == 3 ? Pink : meta == 4 ? Green : meta == 5 ? Storm : meta >= 6 ? Purple : Cold;
	}

	public int getMeta()
	{
		return this.meta;
	}

    public String toString()
    {
        return this.unlocalizedName;
    }

    public String getName()
    {
        return this.unlocalizedName;
    }

}