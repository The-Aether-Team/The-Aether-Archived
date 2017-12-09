package com.legacy.aether.blocks.util;

import net.minecraft.util.IStringSerializable;

public enum EnumCrystalType implements IStringSerializable
{

	Crystal(0, "crystal_leaves"), Crystal_Fruited(1, "crystal_fruit_leaves");

	private int meta;

	private String unlocalizedName;

	EnumCrystalType(int meta, String unlocalizedName)
	{
		this.meta = meta;
		this.unlocalizedName = unlocalizedName;
	}

	public static EnumCrystalType getType(int meta)
	{
		return meta == 1 ? Crystal_Fruited : Crystal;
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