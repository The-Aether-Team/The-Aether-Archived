package com.legacy.aether.api.enchantments;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherEnchantmentFuel extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<AetherEnchantmentFuel>
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