package com.gildedgames.the_aether.api.enchantments;

import com.gildedgames.the_aether.api.RegistryEntry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherEnchantment extends RegistryEntry {

	public int timeRequired;

	public ItemStack input, output;

	public AetherEnchantment(ItemStack input, Block output, int timeRequired) {
		this(input, new ItemStack(output), timeRequired);
	}

	public AetherEnchantment(Block input, ItemStack output, int timeRequired) {
		this(new ItemStack(input), output, timeRequired);
	}

	public AetherEnchantment(Block input, Block output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AetherEnchantment(ItemStack input, Item output, int timeRequired) {
		this(input, new ItemStack(output), timeRequired);
	}

	public AetherEnchantment(Item input, ItemStack output, int timeRequired) {
		this(new ItemStack(input), output, timeRequired);
	}

	public AetherEnchantment(Item input, Item output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AetherEnchantment(Block input, Item output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AetherEnchantment(Item input, Block output, int timeRequired) {
		this(new ItemStack(input), new ItemStack(output), timeRequired);
	}

	public AetherEnchantment(Item result, int timeRequired) {
		this(new ItemStack(result), new ItemStack(result), timeRequired);
	}

	public AetherEnchantment(ItemStack input, ItemStack output, int timeRequired) {
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
		if (obj instanceof AetherEnchantment) {
			AetherEnchantment freezable = (AetherEnchantment) obj;

			boolean inputCheck = this.getInput().getItem() == freezable.getInput().getItem() && this.getInput().getItemDamage() == freezable.getInput().getItemDamage();
			boolean outputCheck = this.getOutput().getItem() == freezable.getOutput().getItem() && this.getOutput().getItemDamage() == freezable.getOutput().getItemDamage();

			return inputCheck && outputCheck;
		}

		return false;
	}

}