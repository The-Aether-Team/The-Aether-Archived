package com.gildedgames.the_aether.entities.util;

public enum EnumSwetType
{
    Gold(0, "gold_swet"), Blue(1, "blue_swet");

    private int meta;

    private String unlocalizedName;

    EnumSwetType(int meta, String unlocalizedName)
    {
        this.meta = meta;
        this.unlocalizedName = unlocalizedName;
    }

    public static EnumSwetType getType(int meta)
    {
        return meta == 1 ? Blue : Gold;
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
