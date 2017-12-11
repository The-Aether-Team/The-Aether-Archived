package com.legacy.aether.api.accessories;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AetherAccessory extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<AetherAccessory>
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