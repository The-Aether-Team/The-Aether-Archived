package com.legacy.aether.common.registry.objects;

import net.minecraft.item.ItemStack;

public class AetherFreezable
{

	private ItemStack input, result;

	private int timeRequired;

	public AetherFreezable(ItemStack input, ItemStack result, int timeRequired)
	{
		this.input = input;
		this.result = result;
		this.timeRequired = timeRequired;

	}

	public ItemStack getFreezableInput()
	{
		return input;
	}

	public ItemStack getFrozenResult()
	{
		return result;
	}

	public int getTimeRequired()
	{
		return timeRequired;
	}

}