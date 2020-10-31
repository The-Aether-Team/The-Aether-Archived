package com.gildedgames.the_aether.api.enchantments;

import com.gildedgames.the_aether.api.AetherRegistryEntry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherEnchantmentFuel extends AetherRegistryEntry<AetherEnchantmentFuel>
{

	public int timeGiven;

	public ItemStack fuelStack;

	public AetherEnchantmentFuel(Block fuelBlock, int timeGiven)
	{
		this(new ItemStack(fuelBlock), timeGiven);
	}

	public AetherEnchantmentFuel(Item fuelItem, int timeGiven)
	{
		this(new ItemStack(fuelItem), timeGiven);
	}

	public AetherEnchantmentFuel(ItemStack fuelStack, int timeGiven)
	{
		this.timeGiven = timeGiven;
		this.fuelStack = fuelStack;

		this.setRegistryName(fuelStack.getItem().getRegistryName().toString() + "_meta_" + (fuelStack.isItemStackDamageable() ? 0 : fuelStack.getMetadata()));
	}

	public int getTimeGiven()
	{
		return this.timeGiven;
	}

	public ItemStack getFuelStack()
	{
		return this.fuelStack;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof AetherEnchantmentFuel)
		{
			AetherEnchantmentFuel fuel = (AetherEnchantmentFuel) obj;

			return this.getFuelStack().getItem() == fuel.getFuelStack().getItem() && this.getFuelStack().getItemDamage() == fuel.getFuelStack().getItemDamage();
		}

		return false;
	}

}