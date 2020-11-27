package com.gildedgames.the_aether.client.gui;

import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.shader.Framebuffer;

import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.client.gui.trivia.AetherTrivia;

import cpw.mods.fml.client.FMLClientHandler;

public class AetherLoadingScreen extends LoadingScreenRenderer {

	private String message = "";

	private Minecraft mc;

	private String currentDisplayedTrivia = "";

	private long systemTime = Minecraft.getSystemTime();

	private Framebuffer framebuffer;

	public AetherLoadingScreen(Minecraft mcIn) {
		super(mcIn);

		this.mc = mcIn;

		this.framebuffer = new Framebuffer(mcIn.displayWidth, mcIn.displayHeight, false);
		this.framebuffer.setFramebufferFilter(9728);
	}

	@Override
	public void resetProgressAndMessage(String message) {
		super.resetProgressAndMessage(message);

		this.currentDisplayedTrivia = AetherTrivia.getNewTrivia();
	}

	@Override
	public void displayProgressMessage(String message) {
		this.systemTime = 0L;
		this.message = message;
		this.setLoadingProgress(-1);
		this.systemTime = 0L;
	}

	@Override
	public void setLoadingProgress(int progress) {
		long j = Minecraft.getSystemTime();

		if (j - this.systemTime >= 100L) {
			this.systemTime = j;
			ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
			int k = scaledresolution.getScaleFactor();
			int l = scaledresolution.getScaledWidth();
			int i1 = scaledresolution.getScaledHeight();

			if (OpenGlHelper.isFramebufferEnabled()) {
				this.framebuffer.framebufferClear();
			} else {
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			}

			this.framebuffer.bindFramebuffer(false);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0F, 0.0F, -200.0F);

			if (!OpenGlHelper.isFramebufferEnabled()) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			}

			if (!FMLClientHandler.instance().handleLoadingScreen(scaledresolution)) {
				Tessellator tessellator = Tessellator.instance;
				this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
				float f = 32.0F;
				tessellator.startDrawingQuads();
				tessellator.setColorOpaque_I(4210752);
				tessellator.addVertexWithUV(0.0D, (double) i1, 0.0D, 0.0D, (double) ((float) i1 / f));
				tessellator.addVertexWithUV((double) l, (double) i1, 0.0D, (double) ((float) l / f), (double) ((float) i1 / f));
				tessellator.addVertexWithUV((double) l, 0.0D, 0.0D, (double) ((float) l / f), 0.0D);
				tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
				tessellator.draw();

				if (progress >= 0) {
					byte b0 = 100;
					byte b1 = 2;
					int j1 = l / 2 - b0 / 2;
					int k1 = i1 / 2 + 16;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tessellator.startDrawingQuads();
					tessellator.setColorOpaque_I(8421504);
					tessellator.addVertex((double) j1, (double) k1, 0.0D);
					tessellator.addVertex((double) j1, (double) (k1 + b1), 0.0D);
					tessellator.addVertex((double) (j1 + b0), (double) (k1 + b1), 0.0D);
					tessellator.addVertex((double) (j1 + b0), (double) k1, 0.0D);
					tessellator.setColorOpaque_I(8454016);
					tessellator.addVertex((double) j1, (double) k1, 0.0D);
					tessellator.addVertex((double) j1, (double) (k1 + b1), 0.0D);
					tessellator.addVertex((double) (j1 + progress), (double) (k1 + b1), 0.0D);
					tessellator.addVertex((double) (j1 + progress), (double) k1, 0.0D);
					tessellator.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}

				GL11.glEnable(GL11.GL_BLEND);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				this.mc.fontRenderer.drawStringWithShadow(this.message, (l - this.mc.fontRenderer.getStringWidth(this.message)) / 2, i1 / 2 - 4 + 8, 16777215);
				this.mc.fontRenderer.drawStringWithShadow(this.currentDisplayedTrivia, (l - this.mc.fontRenderer.getStringWidth(this.currentDisplayedTrivia)) / 2, i1 - 16, 0xffff99);
			}

			this.framebuffer.unbindFramebuffer();

			if (OpenGlHelper.isFramebufferEnabled()) {
				this.framebuffer.framebufferRender(l * k, i1 * k);
			}

			this.mc.func_147120_f();

			try {
				Thread.yield();
			} catch (Exception exception) {
				;
			}
		}
	}

}