package com.legacy.aether.blocks.util;

import net.minecraft.util.IStringSerializable;

public enum EnumLeafType implements IStringSerializable
{

	Green(0, "green_leaves"),
	Golden(1, "golden_oak_leaves"),
	Blue(2, "blue_leaves"),
	DarkBlue(3, "dark_blue_leaves"),
	Purple(4, "purple_leaves")	;

	public static final EnumLeafType[] lookup = EnumLeafType.values();

	private int meta;

	private String unlocalizedName;

	EnumLeafType(int meta, String unlocalizedName)
	{
		this.meta = meta;
		this.unlocalizedName = unlocalizedName;
	}

	public static EnumLeafType getType(int meta)
	{
		return lookup[meta];
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