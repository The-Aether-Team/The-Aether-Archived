package com.legacy.aether.common.registry.objects;

import net.minecraft.item.ItemStack;

public class AetherEnchantment
{

	private ItemStack input, result;

	private int timeRequired;

	public AetherEnchantment(ItemStack input, ItemStack result, int timeRequired)
	{
		this.input = input;
		this.result = result;
		this.timeRequired = timeRequired;

	}

	public ItemStack getEnchantmentInput()
	{
		return input;
	}

	public ItemStack getEnchantedResult()
	{
		return result;
	}

	public int getTimeRequired()
	{
		return timeRequired;
	}

}