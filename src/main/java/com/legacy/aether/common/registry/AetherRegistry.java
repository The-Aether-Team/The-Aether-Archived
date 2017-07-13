package com.legacy.aether.common.registry;

import java.util.ArrayList;
import java.util.List;

import com.legacy.aether.common.registry.objects.AetherEnchantment;
import com.legacy.aether.common.registry.objects.AetherFreezable;

import net.minecraft.item.ItemStack;

public class AetherRegistry
{

	private static List<AetherEnchantment> enchantables = new ArrayList<AetherEnchantment>();

	private static List<AetherFreezable> freezables = new ArrayList<AetherFreezable>();

	public static void registerEnchantment(ItemStack from, ItemStack to, int enchantmentTime)
	{
		enchantables.add(new AetherEnchantment(from, to, enchantmentTime));
	}

	public static void registerFreezable(ItemStack from, ItemStack to, int freezerTime)
	{
		freezables.add(new AetherFreezable(from, to, freezerTime));
	}

	public static AetherEnchantment getEnchantment(int enchantmentID)
	{
		return enchantables.get(enchantmentID);
	}

	public static AetherFreezable getFreezable(int freezableID)
	{
		return freezables.get(freezableID);
	}

	public static List<AetherEnchantment> getEnchantables()
	{
		return enchantables;
	}

	public static List<AetherFreezable> getFreezables()
	{
		return freezables;
	}

	public static int getEnchantmentSize()
	{
		return enchantables.size();
	}

	public static int getFreezableSize()
	{
		return freezables.size();
	}

}