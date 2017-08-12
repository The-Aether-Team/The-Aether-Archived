package com.legacy.aether.common.freezables;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherFreezable 
{

	private ItemStack result;

	private int timeRequired;

	public AetherFreezable(ItemStack result, int timeRequired)
	{
		this.result = result;
		this.timeRequired = timeRequired;
	}

	public AetherFreezable(Item result, int timeRequired)
	{
		this(new ItemStack(result), timeRequired);
	}

	public AetherFreezable(Block result, int timeRequired)
	{
		this(new ItemStack(result), timeRequired);
	}

	public ItemStack getResult()
	{
		return this.result;
	}

	public int getTimeRequired()
	{
		return this.timeRequired;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof AetherFreezable))
		{
			return false;
		}

		AetherFreezable freezable = (AetherFreezable) object;

		return this.getResult() == freezable.getResult() && this.getTimeRequired() == freezable.getTimeRequired();
	}

}