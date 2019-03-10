package com.legacy.aether.client;

import com.legacy.aether.client.render.AetherEntityRenders;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientAether
{

	public static void initialization(FMLClientSetupEvent event)
	{
		AetherEntityRenders.initialization();
	}

}