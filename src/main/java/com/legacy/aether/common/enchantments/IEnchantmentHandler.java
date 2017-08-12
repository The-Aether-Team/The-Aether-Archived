package com.legacy.aether.common.enchantments;

import net.minecraft.item.ItemStack;

public interface IEnchantmentHandler 
{

	/*
	 * Use this method to add your custom aether enchants. Please
	 * keep the default return value null to ensure no issues arise.
	 * @author - Kino
	 */
	public AetherEnchantment getEnchantment(ItemStack stack);

	/*
	 * Used to determine if an item can be used as enchantment fuel.
	 * Keep value at <= 0 if you don't have any fuel items to add
	 * @author - Kino
	 */
	public int getEnchantmentFuelTime(ItemStack stack);

}