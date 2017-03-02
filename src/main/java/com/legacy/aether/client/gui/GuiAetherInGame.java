package com.legacy.aether.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.legacy.aether.client.overlay.AetherOverlay;
import com.legacy.aether.server.player.PlayerAether;

public class GuiAetherInGame extends Gui
{

	private Minecraft mc;

	public GuiAetherInGame(Minecraft mc)
	{
		super();
		this.mc = mc;
	}

	@SubscribeEvent
	public void onRenderBlockOverlay(RenderBlockOverlayEvent event)
	{
		if (this.mc.thePlayer != null)
		{
			PlayerAether player = PlayerAether.get(this.mc.thePlayer);

			if (player.isWearingPhoenixSet() && event.getOverlayType() == OverlayType.FIRE)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent event)
	{
		if (event.isCancelable() || event.getType() != ElementType.TEXT)
		{
			return;
		}

		PlayerAether player = PlayerAether.get(this.mc.thePlayer);

		if (player.poisonInstance() != null)
		{
			AetherOverlay.renderCure(this.mc);
			AetherOverlay.renderPoison(this.mc);
		}

		if (player.thePlayer != null)
		{
			AetherOverlay.renderIronBubble(this.mc);
			AetherOverlay.renderCooldown(this.mc);
			AetherOverlay.renderJumps(this.mc);
			AetherOverlay.renderBossHP(this.mc);
		}

        float portalTime =  player.portalAnimTime * 1.2F + (player.prevPortalAnimTime -  player.portalAnimTime);

        if(portalTime > 0.0F)
        {
        	GlStateManager.enableBlend();

            ScaledResolution scaledresolution = new ScaledResolution(this.mc);

            AetherOverlay.renderPortal(portalTime, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());

            GlStateManager.disableBlend();
        }
        
	}

}