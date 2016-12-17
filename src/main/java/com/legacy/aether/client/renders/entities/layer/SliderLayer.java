package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.server.entities.bosses.slider.EntitySlider;

public class SliderLayer implements LayerRenderer<EntitySlider>
{

	private static final ResourceLocation TEXTURE_GLOW = new ResourceLocation("aether_legacy", "textures/bosses/slider/slider_awake_glow.png");

	private static final ResourceLocation TEXTURE_GLOW_RED = new ResourceLocation("aether_legacy", "textures/bosses/slider/slider_awake_critical_glow.png");

	@Override
	public void doRenderLayer(EntitySlider slider, float x, float y, float z, float p_177141_5_, float p_177141_6_, float p_177141_7_, float yaw)
	{
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

		if (slider.isAwake())
		{
			if (slider.criticalCondition())
			{
				renderManager.renderEngine.bindTexture(TEXTURE_GLOW_RED);
			}
			else
			{
				renderManager.renderEngine.bindTexture(TEXTURE_GLOW);
			}

			float f1 = 1.0F;

			GL11.glEnable(GL11.GL_BLEND);

			int j = 61680;
			int k = j % 0x10000;
			int l = j / 0x10000;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k / 1.0F, (float) l / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
		}
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		return false;
	}

}