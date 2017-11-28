package com.legacy.aether.blocks.util;

import net.minecraft.util.IStringSerializable;

public enum EnumHolidayType implements IStringSerializable
{

	Holiday_Leaves(0, "holiday_leaves"), Decorated_Leaves(1, "decorated_holiday_leaves");

	private int meta;

	private String unlocalizedName;

	EnumHolidayType(int meta, String unlocalizedName)
	{
		this.meta = meta;
		this.unlocalizedName = unlocalizedName;
	}

	public static EnumHolidayType getType(int meta)
	{
		return meta == 1 ? Decorated_Leaves : Holiday_Leaves;
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