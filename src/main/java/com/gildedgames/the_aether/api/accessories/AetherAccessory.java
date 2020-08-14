package com.gildedgames.the_aether.api.accessories;

import com.gildedgames.the_aether.api.RegistryEntry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherAccessory extends RegistryEntry {

	private ItemStack accessoryStack;

	private AccessoryType accessoryType;

	private AccessoryType extraType;

	public AetherAccessory(Block item, AccessoryType type) {
		this(new ItemStack(item), type);
	}

	public AetherAccessory(Item item, AccessoryType type) {
		this(new ItemStack(item), type);
	}

	public AetherAccessory(ItemStack stack, AccessoryType type) {
		this.accessoryType = type;
		this.accessoryStack = stack;
		this.extraType = type == AccessoryType.RING ? AccessoryType.EXTRA_RING : type == AccessoryType.MISC ? AccessoryType.EXTRA_MISC : null;

		this.setRegistryName(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage()));
	}

	public AccessoryType getAccessoryType() {
		return this.accessoryType;
	}

	public AccessoryType getExtraType() {
		return this.extraType;
	}

	public ItemStack getAccessoryStack() {
		return this.accessoryStack;
	}
}