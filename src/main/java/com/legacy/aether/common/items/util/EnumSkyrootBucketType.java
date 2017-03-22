package com.legacy.aether.common.items.util;

public enum EnumSkyrootBucketType
{
	Empty(0, "empty"), Water(1, "water"), Poison(2, "poison"), Remedy(3, "remedy");

	public int meta;

	public String name;

	EnumSkyrootBucketType(int meta, String name)
	{
		this.meta = meta;
		this.name = name;
	}

	public static EnumSkyrootBucketType getType(int meta)
	{
		return meta == 1 ? Water :  meta == 2 ? Poison : meta == 3 ? Remedy : Empty;
	}

	public int getMeta()
	{
		return this.meta;
	}

    public String toString()
    {
        return this.name;
    }

}