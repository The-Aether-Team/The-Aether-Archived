package com.legacy.aether.universal;

import net.minecraftforge.fml.common.Loader;

public class AetherCompatibility 
{

	public static boolean visualModsLoaded()
	{
		return Loader.isModLoaded("inventorytweaks") || Loader.isModLoaded("quark") || Loader.isModLoaded("Quark");
	}

}