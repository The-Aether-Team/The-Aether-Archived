package com.legacy.aether.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.legacy.aether.client.gui.AetherLoadingScreen;

public class ClientTickHandler 
{

	public static Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) throws Exception
	{
		TickEvent.Phase phase = event.phase;
		TickEvent.Type type = event.type;

		if (phase == TickEvent.Phase.END)
		{
			if (type.equals(TickEvent.Type.CLIENT))
			{
				if (!(mc.loadingScreen instanceof AetherLoadingScreen))
				{
					mc.loadingScreen = new AetherLoadingScreen(mc);
				}
			}
		}
	}

}