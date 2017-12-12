package com.legacy.aether.api.freezables;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherFreezableFuel extends net.minecraftforge.fml.common.registry.IForgeRegistryEntry.Impl<AetherFreezableFuel>
{

	public int timeGiven;

	public ItemStack fuelStack;

	public AetherFreezableFuel(Block fuelBlock, int timeGiven)
	{
		this(new ItemStack(fuelBlock), timeGiven);
	}

	public AetherFreezableFuel(Item fuelItem, int timeGiven)
	{
		this(new ItemStack(fuelItem), timeGiven);
	}

	public AetherFreezableFuel(ItemStack fuelStack, int timeGiven)
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
		if (obj instanceof AetherFreezableFuel)
		{
			AetherFreezableFuel fuel = (AetherFreezableFuel) obj;

			return this.getFuelStack().getItem() == fuel.getFuelStack().getItem() && this.getFuelStack().getItemDamage() == fuel.getFuelStack().getItemDamage();
		}

		return false;
	}

}