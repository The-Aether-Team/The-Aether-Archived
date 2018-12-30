package com.legacy.aether.compatibility.client;

import com.legacy.aether.CommonProxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AetherClientCompatibility
{

	public static void initialization()
	{
		if (Loader.isModLoaded("battlegear2"))
		{
			CommonProxy.registerEvent(new BattlegearClientEventHandler());
		}
	}

}