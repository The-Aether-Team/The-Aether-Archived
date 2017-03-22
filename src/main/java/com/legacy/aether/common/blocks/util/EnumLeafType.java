package com.legacy.aether.common.blocks.util;

import net.minecraft.util.IStringSerializable;

public enum EnumLeafType implements IStringSerializable
{

	Green(0, "green_leaves"), Golden(1, "golden_oak_leaves");

	private int meta;

	private String unlocalizedName;

	EnumLeafType(int meta, String unlocalizedName)
	{
		this.meta = meta;
		this.unlocalizedName = unlocalizedName;
	}

	public static EnumLeafType getType(int meta)
	{
		return meta == 1 ? Golden : Green;
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