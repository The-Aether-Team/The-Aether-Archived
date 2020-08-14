package com.gildedgames.the_aether.compatibility.client;

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
			mods.battlegear2.api.core.BattlegearUtils.RENDER_BUS.register(new BattlegearClientEventHandler());
		}
	}

}