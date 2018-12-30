package com.legacy.aether.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;

import com.legacy.aether.CommonProxy;
import com.legacy.aether.client.audio.AetherMusicHandler;
import com.legacy.aether.client.gui.AetherLoadingScreen;
import com.legacy.aether.client.gui.GuiAetherInGame;
import com.legacy.aether.client.gui.GuiSunAltar;
import com.legacy.aether.client.renders.AetherEntityRenderer;
import com.legacy.aether.client.renders.RendersAether;
import com.legacy.aether.compatibility.client.AetherClientCompatibility;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	public static final IIcon[] ACCESSORY_ICONS = new IIcon[8];

	@Override
	public void init() {
		berryBushRenderID = RenderingRegistry.getNextAvailableRenderId();
		treasureChestRenderID = RenderingRegistry.getNextAvailableRenderId();
		aetherFlowerRenderID = RenderingRegistry.getNextAvailableRenderId();

		Minecraft.getMinecraft().loadingScreen = new AetherLoadingScreen(Minecraft.getMinecraft());

		EntityRenderer previousRenderer = Minecraft.getMinecraft().entityRenderer;

		Minecraft.getMinecraft().entityRenderer = new AetherEntityRenderer(Minecraft.getMinecraft(), previousRenderer, Minecraft.getMinecraft().getResourceManager());

		RendersAether.initialization();

		registerEvent(new AetherMusicHandler());
		registerEvent(new AetherClientEvents());
		registerEvent(new GuiAetherInGame(Minecraft.getMinecraft()));

		AetherClientCompatibility.initialization();
	}

	@Override
	public void sendMessage(EntityPlayer player, String text) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(text));
	}

	@Override
	public void openSunAltar() {
		FMLClientHandler.instance().getClient().displayGuiScreen(new GuiSunAltar());
	}

	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

}