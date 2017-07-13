package com.legacy.aether.common.blocks.util;

import net.minecraft.util.IStringSerializable;

public enum EnumStoneType implements IStringSerializable
{
	Carved(0, "carved_stone"), Sentry(1, "sentry_stone"), 
	Angelic(2, "angelic_stone"), Light_angelic(3, "light_angelic_stone"), 
	Hellfire(4, "hellfire_stone"), Light_hellfire(5, "light_hellfire_stone");

	private int meta;

	private String name;

    public static final EnumStoneType[] lookup = new EnumStoneType[values().length];

	EnumStoneType(int id, String name)
	{
		this.meta = id;
		this.name = name;
	}

	public static EnumStoneType getType(int meta)
	{
		return meta == 1 ? Sentry : (meta == 2 ? Angelic : (meta == 3 ? Light_angelic : (meta == 4 ? Hellfire : meta == 5 ? Light_hellfire : Carved)));
	}

	public int getMeta()
	{
		return this.meta;
	}

    public String toString()
    {
        return this.name;
    }

    public String getName()
    {
        return this.name;
    }

}