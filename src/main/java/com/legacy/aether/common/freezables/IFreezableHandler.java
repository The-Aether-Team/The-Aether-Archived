package com.legacy.aether.common.freezables;

import net.minecraft.item.ItemStack;

public interface IFreezableHandler
{

	/*
	 * Use this method to add your custom aether freezables. Please
	 * keep the default return value null to ensure no issues arise.
	 * @author - Kino
	 */
	public AetherFreezable getFreezable(ItemStack stack);

	/*
	 * Used to determine if an item can be used as freezer fuel.
	 * Keep value at <= 0 if you don't have any fuel items to add
	 * @author - Kino
	 */
	public int getFreezerFuelTime(ItemStack stack);

}