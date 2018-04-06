package com.legacy.aether.api.accessories;

import com.legacy.aether.api.AetherRegistryEntry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherAccessory extends AetherRegistryEntry<AetherAccessory>
{

	private ItemStack accessoryStack;

	private AccessoryType accessoryType;

	public AetherAccessory(Block item, AccessoryType type)
	{
		this(new ItemStack(item), type);
	}

	public AetherAccessory(Item item, AccessoryType type)
	{
		this(new ItemStack(item), type);
	}

	public AetherAccessory(ItemStack stack, AccessoryType type)
	{
		this.accessoryType = type;
		this.accessoryStack = stack;

		this.setRegistryName(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata()));
	}

	public AccessoryType getAccessoryType()
	{
		return this.accessoryType;
	}

	public ItemStack getAccessoryStack()
	{
		return this.accessoryStack;
	}
}