package com.legacy.aether.blocks.util;

import net.minecraft.util.IStringSerializable;

public enum EnumTallGrassType implements IStringSerializable
{

	Short(0, "short_tallgrass"), Normal(1, "normal_tallgrass"), Long(2, "long_tallgrass");

	private int meta;

	private String unlocalizedName;

    private static final int maxValues = values().length;

	EnumTallGrassType(int meta, String unlocalizedName)
	{
		this.meta = meta;
		this.unlocalizedName = unlocalizedName;
	}

	public static EnumTallGrassType getType(int meta)
	{
		return meta == 0 ? Short : meta == 1 ? Normal : Long;
	}

	public int getMeta() { return this.meta; }

    public String toString() { return this.unlocalizedName; }

    public String getName() { return this.unlocalizedName; }

    public static int getMaxValues() { return maxValues; }
}