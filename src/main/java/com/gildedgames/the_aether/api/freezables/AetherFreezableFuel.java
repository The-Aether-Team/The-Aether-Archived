package com.gildedgames.the_aether.api.freezables;

import com.gildedgames.the_aether.api.RegistryEntry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherFreezableFuel extends RegistryEntry {

	public int timeGiven;

	public ItemStack fuelStack;

	public AetherFreezableFuel(Block fuelBlock, int timeGiven) {
		this(new ItemStack(fuelBlock), timeGiven);
	}

	public AetherFreezableFuel(Item fuelItem, int timeGiven) {
		this(new ItemStack(fuelItem), timeGiven);
	}

	public AetherFreezableFuel(ItemStack fuelStack, int timeGiven) {
		this.timeGiven = timeGiven;
		this.fuelStack = fuelStack;

		this.setRegistryName(fuelStack.getItem().getUnlocalizedName().toString() + "_meta_" + (fuelStack.isItemStackDamageable() ? 0 : fuelStack.getItemDamage()));
	}

	public int getTimeGiven() {
		return this.timeGiven;
	}

	public ItemStack getFuelStack() {
		return this.fuelStack;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AetherFreezableFuel) {
			AetherFreezableFuel fuel = (AetherFreezableFuel) obj;

			return this.getFuelStack().getItem() == fuel.getFuelStack().getItem() && this.getFuelStack().getItemDamage() == fuel.getFuelStack().getItemDamage();
		}

		return false;
	}

}