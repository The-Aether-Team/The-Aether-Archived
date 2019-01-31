package com.legacy.aether.universal.fastcrafting;

import net.minecraftforge.fml.common.Loader;

public class FastCraftingUtil
{

	public static boolean isFastCraftingLoaded()
	{
		return Loader.isModLoaded("fastcrafting");
	}

	public static boolean isOverridenGUI(Class<?> clazz)
	{
		return isFastCraftingLoaded() && clazz.getName().equals("com.dazo66.fastcrafting.gui.GuiInventoryOverride");
	}

}