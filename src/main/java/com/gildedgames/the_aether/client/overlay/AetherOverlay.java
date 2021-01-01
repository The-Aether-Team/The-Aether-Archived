package com.gildedgames.the_aether.client.overlay;

import com.gildedgames.the_aether.Aether;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.api.player.util.IAetherBoss;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AetherOverlay {

	private static final ResourceLocation TEXTURE_JUMPS = Aether.locate("textures/gui/jumps.png");

	private static final ResourceLocation TEXTURE_COOLDOWN_BAR = Aether.locate("textures/gui/cooldown_bar.png");

	private static final ResourceLocation TEXTURE_POISON_VIGNETTE = Aether.locate("textures/blur/poison_vignette.png");

	private static final ResourceLocation TEXTURE_CURE_VIGNETTE = Aether.locate("textures/blur/cure_vignette.png");

	public static void renderPoison(Minecraft mc) {
		PlayerAether playerAether = PlayerAether.get(mc.thePlayer);

		if (playerAether.poisonTime > 0) {
			ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
			Tessellator tessellator = Tessellator.instance;

			float alpha = getPoisonAlpha((float) (playerAether.poisonTime % 50) / 50);
			int width = scaledresolution.getScaledWidth();
			int height = scaledresolution.getScaledHeight();

			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glColor4f(0.5F, 0.5F, 0.5F, alpha);


			mc.renderEngine.bindTexture(TEXTURE_POISON_VIGNETTE);

			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0.0D, (double) height, -90.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((double) width, (double) height, -90.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((double) width, 0.0D, -90.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
			tessellator.draw();

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}

	public static void renderCure(Minecraft mc) {
		PlayerAether playerAether = PlayerAether.get(mc.thePlayer);

		if (playerAether.isCured()) {
			ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
			Tessellator tessellator = Tessellator.instance;

			float alpha = 0.5F;
			int width = scaledresolution.getScaledWidth();
			int height = scaledresolution.getScaledHeight();

			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glColor4f(0.5F, 0.5F, 0.5F, alpha);

			mc.renderEngine.bindTexture(TEXTURE_CURE_VIGNETTE);

			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0.0D, (double) height, -90.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((double) width, (double) height, -90.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((double) width, 0.0D, -90.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
			tessellator.draw();

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}

	public static void renderIronBubble(Minecraft mc) {
		ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		int width = scaledresolution.getScaledWidth();
		int height = scaledresolution.getScaledHeight();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

		mc.renderEngine.bindTexture(Gui.icons);

		int bubbleAmount = PlayerAether.get(mc.thePlayer).getAccessoryInventory().getAccessoryCount(new ItemStack(ItemsAether.iron_bubble));

		if (mc.playerController.shouldDrawHUD() && mc.thePlayer.isInWater() && mc.thePlayer.isInsideOfMaterial(Material.water)) {
			for (int i = 0; i < bubbleAmount; ++i) {
				drawTexturedModalRect((width / 2 - 8 * i) + 81, height - 49, 16, 18, 9, 9);
			}
		}

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	public static void renderCooldown(Minecraft mc) {
		PlayerAether playerInfo = PlayerAether.get(mc.thePlayer);

		if (playerInfo.getHammerCooldown() != 0) {
			ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

			int cooldownRemaining = (int) ((float) (playerInfo.getHammerCooldown()) / (float) (playerInfo.getHammerMaxCooldown()) * 128F);
			int width = scaledresolution.getScaledWidth();

			mc.fontRenderer.drawStringWithShadow(playerInfo.getHammerName() + " " + I18n.format("item.notch_hammer.cooldown"), (width / 2) - (mc.fontRenderer.getStringWidth(playerInfo.getHammerName() + " Cooldown") / 2), 32, 0xffffffff);

			GL11.glPushMatrix();

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_ALPHA_TEST);

			mc.renderEngine.bindTexture(TEXTURE_COOLDOWN_BAR);

			drawTexturedModalRect(width / 2 - 64, 42, 0, 8, 128, 8);

			drawTexturedModalRect(width / 2 - 64, 42, 0, 0, cooldownRemaining, 8);

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			GL11.glPopMatrix();
		}
	}

	public static void renderJumps(Minecraft mc) {
		EntityPlayer player = mc.thePlayer;

		if (player == null || player.ridingEntity == null || !(player.ridingEntity instanceof EntityMoa)) {
			return;
		}

		ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		EntityMoa moa = (EntityMoa) (player.ridingEntity);

		int width = scaledresolution.getScaledWidth();

		GL11.glPushMatrix();

		mc.renderEngine.bindTexture(TEXTURE_JUMPS);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		for (int jump = 0; jump < moa.getMaxJumps(); jump++) {
			int yPos = 18;
			int xPos = ((width / 2) + (jump * 8)) - (moa.getMaxJumps() * 8) / 2;

			if (jump < moa.getRemainingJumps()) {
				drawTexturedModalRect(xPos, yPos, 0, 0, 9, 11);
			} else {
				drawTexturedModalRect(xPos, yPos, 10, 0, 9, 11);
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glPopMatrix();
	}

	public static void renderAetherPortal(float timeInPortal, ScaledResolution scaledRes) {
		if (timeInPortal < 1.0F) {
			timeInPortal = timeInPortal * timeInPortal;
			timeInPortal = timeInPortal * timeInPortal;
			timeInPortal = timeInPortal * 0.8F + 0.2F;
		}

		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, timeInPortal);
		IIcon iicon = BlocksAether.aether_portal.getBlockTextureFromSide(1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		float f = iicon.getMinU();
		float f1 = iicon.getMinV();
		float f2 = iicon.getMaxU();
		float f3 = iicon.getMaxV();
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, (double) scaledRes.getScaledHeight(), -90.0D, (double) f, (double) f3);
		tessellator.addVertexWithUV((double) scaledRes.getScaledWidth(), (double) scaledRes.getScaledHeight(), -90.0D, (double) f2, (double) f3);
		tessellator.addVertexWithUV((double) scaledRes.getScaledWidth(), 0.0D, -90.0D, (double) f2, (double) f1);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, (double) f, (double) f1);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void renderBossHP(Minecraft mc) {
		PlayerAether player = PlayerAether.get(mc.thePlayer);

		IAetherBoss boss = (IAetherBoss) player.getFocusedBoss();

		if (player.getFocusedBoss() != null) {
			if (player.getFocusedBoss().getBossHealth() <= 0.0F) {
				player.setFocusedBoss(null);
				return;
			}
			//System.out.println(player.getFocusedBoss().getBossHealth());
			String bossTitle = boss.getBossName();
			ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

			int healthRemaining = (int) (boss.getBossHealth() / boss.getMaxBossHealth() * 256F);
			int width = scaledresolution.getScaledWidth();

			GL11.glPushMatrix();

			mc.fontRenderer.drawStringWithShadow(bossTitle, width / 2 - (mc.fontRenderer.getStringWidth(bossTitle) / 2), 2, 0xffffffff);

			mc.renderEngine.bindTexture(Aether.locate("textures/gui/boss_bar.png"));

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			drawTexturedModalRect(width / 2 - 128, 12, 0, 16, 256, 32);

			drawTexturedModalRect(width / 2 - 128, 12, 0, 0, healthRemaining, 16);

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			GL11.glPopMatrix();
		}
	}

	public static void drawTexturedModalRect(float x, float y, float u, float v, float width, float height) {
		float zLevel = -90.0F;

		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), (double) zLevel, (double) ((float) (u + 0) * var7), (double) ((float) (v + height) * var8));
		tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) zLevel, (double) ((float) (u + width) * var7), (double) ((float) (v + height) * var8));
		tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) zLevel, (double) ((float) (u + width) * var7), (double) ((float) (v + 0) * var8));
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) zLevel, (double) ((float) (u + 0) * var7), (double) ((float) (v + 0) * var8));
		tessellator.draw();
	}

	public static float getPoisonAlpha(float f) {
		return (f * f) / 5.0F + 0.4F;
	}

	public static float getCureAlpha(float f) {
		return (f * f) / 10.0F + 0.4F;
	}

}