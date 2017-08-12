package com.legacy.aether.common.enchantments;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherEnchantment
{

	private ItemStack result;

	private int timeRequired;

	public AetherEnchantment(ItemStack result, int timeRequired)
	{
		this.result = result;
		this.timeRequired = timeRequired;
	}

	public AetherEnchantment(Item result, int timeRequired)
	{
		this(new ItemStack(result), timeRequired);
	}

	public AetherEnchantment(Block result, int timeRequired)
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
		if (!(object instanceof AetherEnchantment))
		{
			return false;
		}

		AetherEnchantment enchantment = (AetherEnchantment) object;

		return this.getResult() == enchantment.getResult() && this.getTimeRequired() == enchantment.getTimeRequired();
	}

}