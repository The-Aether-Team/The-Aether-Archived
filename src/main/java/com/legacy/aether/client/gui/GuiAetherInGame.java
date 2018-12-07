package com.legacy.aether.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import com.legacy.aether.client.overlay.AetherOverlay;
import com.legacy.aether.player.PlayerAether;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiAetherInGame extends Gui {

	private Minecraft mc;

	public GuiAetherInGame(Minecraft mc) {
		super();
		this.mc = mc;
	}

	@SubscribeEvent
	public void onRenderBlockOverlay(RenderBlockOverlayEvent event) {
		if (this.mc.thePlayer != null) {
			PlayerAether player = PlayerAether.get(this.mc.thePlayer);

			if (player.getAccessoryInventory().isWearingPhoenixSet() && event.overlayType == OverlayType.FIRE) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent event) {
		if (event.isCancelable() || event.type != ElementType.TEXT) {
			return;
		}

		PlayerAether player = PlayerAether.get(this.mc.thePlayer);

		if (player.getEntity() != null) {
			AetherOverlay.renderCure(this.mc);
			AetherOverlay.renderPoison(this.mc);
			AetherOverlay.renderIronBubble(this.mc);
			AetherOverlay.renderCooldown(this.mc);
			AetherOverlay.renderJumps(this.mc);
			AetherOverlay.renderBossHP(this.mc);
		}

		float portalTime = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * event.partialTicks;//player.portalAnimTime * 1.2F + (player.prevPortalAnimTime -  player.portalAnimTime);

		if (portalTime > 0.0F) {
			AetherOverlay.renderAetherPortal(portalTime, new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight));
		}
	}

}