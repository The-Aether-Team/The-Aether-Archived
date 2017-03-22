package com.legacy.aether.common.blocks.util;

import net.minecraft.util.IStringSerializable;

public enum EnumLogType implements IStringSerializable
{

	Skyroot(0, "skyroot_log"), Oak(1, "golden_oak_log");

	private int meta;

	private String unlocalizedName;

	EnumLogType(int meta, String unlocalizedName)
	{
		this.meta = meta;
		this.unlocalizedName = unlocalizedName;
	}

	public static EnumLogType getType(int meta)
	{
		return (meta % 2) == 0 ? Skyroot : Oak;
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