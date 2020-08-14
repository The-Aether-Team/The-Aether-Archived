package com.gildedgames.the_aether.api.freezables;

import com.gildedgames.the_aether.api.RegistryEntry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherFreezable extends RegistryEntry {

	public int timeRequired;

	public ItemStack input, output;

	public AetherFreezable(ItemStack input, Block output, int timeRequired) {
		this(input, new ItemStack(output), timeRequired);
	}

	public AetherFreezable(Block input, ItemStack output, int timeRequired) {
		this(new ItemStack(input), output, timeRequired);
	}

	public AetherFreezable(Block input, Block output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AetherFreezable(ItemStack input, Item output, int timeRequired) {
		this(input, new ItemStack(output), timeRequired);
	}

	public AetherFreezable(Item input, ItemStack output, int timeRequired) {
		this(new ItemStack(input), output, timeRequired);
	}

	public AetherFreezable(Item input, Item output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AetherFreezable(Block input, Item output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AetherFreezable(Item input, Block output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AetherFreezable(ItemStack input, ItemStack output, int timeRequired) {
		this.input = input;
		this.output = output;
		this.timeRequired = timeRequired;

		this.setRegistryName(input.getItem().getUnlocalizedName().toString() + "_meta_" + (input.isItemStackDamageable() ? 0 : input.getItemDamage()));
	}

	public int getTimeRequired() {
		return this.timeRequired;
	}

	public ItemStack getInput() {
		return this.input;
	}

	public ItemStack getOutput() {
		return this.output;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AetherFreezable) {
			AetherFreezable freezable = (AetherFreezable) obj;

			boolean inputCheck = this.getInput().getItem() == freezable.getInput().getItem() && this.getInput().getItemDamage() == freezable.getInput().getItemDamage();
			boolean outputCheck = this.getOutput().getItem() == freezable.getOutput().getItem() && this.getOutput().getItemDamage() == freezable.getOutput().getItemDamage();

			return inputCheck && outputCheck;
		}

		return false;
	}

}