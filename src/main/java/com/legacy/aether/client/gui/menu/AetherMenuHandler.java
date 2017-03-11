package com.legacy.aether.client.gui.menu;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.GuiAccessDenied;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AetherMenuHandler 
{

	private Minecraft mc = Minecraft.getMinecraft();

	private static boolean world_loaded, menu_loaded;

	@SubscribeEvent
	public void onPlayerLoggingOut(ActionPerformedEvent.Pre event)
	{
		GuiScreen gui = event.getGui();
		int id = event.getButton().id;

		if (gui instanceof GuiDisconnected || gui instanceof GuiAccessDenied)
		{
			if (id == 0 || id == 1)
			{
				this.unloadWorld();
				this.loadWorld();
			}
		}
	}

	@SubscribeEvent
	public void onOpenGui(GuiOpenEvent event)
	{
		if (event.getGui() == null && !world_loaded)
		{
			world_loaded = true;
		}
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) throws Exception
	{
		TickEvent.Phase phase = event.phase;
		TickEvent.Type type = event.type;

		if (phase == TickEvent.Phase.END)
		{
			if (type.equals(TickEvent.Type.CLIENT))
			{
				this.updateMenu();
			}
		}
	}

	private void updateMenu() throws Exception
	{
		GuiScreen screen = this.mc.currentScreen;

		if (screen != null && screen.getClass() == GuiMainMenu.class)
		{
			this.unloadWorld();

			if (this.mc.getSaveLoader().getSaveList().size() <= 0)
			{
				this.mc.displayGuiScreen(new GuiAetherMainMenuFallback());
			}
			else
			{
				this.loadWorld();
			}
		}

		if (world_loaded)
		{
			if (!(screen instanceof GuiAetherMainMenu) && !menu_loaded)
			{
				this.mc.displayGuiScreen(new GuiAetherMainMenu(false));
				menu_loaded = true;
			}
		}
	}

	public void loadWorld()
	{
		if (this.mc.theWorld != null)
		{
			this.mc.theWorld.sendQuittingDisconnectingPacket();
		}

		FMLClientHandler.instance().tryLoadExistingWorld(null, this.getWorldSummary(0));
	}

	public void unloadWorld()
	{
		world_loaded = false;
		menu_loaded = false;
	}

	protected WorldSummary getWorldSummary(int fileNumber)
	{
		WorldSummary s = null;
		List<WorldSummary> list = null;

		try
		{
			list = this.mc.getSaveLoader().getSaveList();
			Collections.sort(list);
			s = list.get(fileNumber);
		}
		catch (AnvilConverterException e) { e.printStackTrace(); }

		return s;
	}

}