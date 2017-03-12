package com.legacy.aether.universal;

import net.minecraftforge.fml.common.Loader;

public class AetherCompatibility
{

	public static void initialization()
	{
		if (isLoaded("jei"))
		{
			
		}
	}

	public static boolean isLoaded(String modId)
	{
		return Loader.isModLoaded(modId);
	}

}