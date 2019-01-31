package com.legacy.aether.universal.pixelmon;

import net.minecraftforge.fml.common.Loader;

public class PixelmonUtil
{

	/*
	 * This modid works for both Pixelmon Generations and Reforged
	 */
	public static boolean isPixelmonLoaded()
	{
		return Loader.isModLoaded("pixelmon");
	}

	public static boolean isOverridenInventoryGUI(Class<?> clazz)
	{
		return isPixelmonLoaded() && clazz.getName().equals("com.pixelmonmod.pixelmon.client.gui.inventory.GuiInventoryPixelmonExtended");
	}

	public static boolean isOverridenCreativeGUI(Class<?> clazz)
	{
		return isPixelmonLoaded() && clazz.getName().equals("com.pixelmonmod.pixelmon.client.gui.inventory.GuiCreativeInventoryExtended");
	}

}