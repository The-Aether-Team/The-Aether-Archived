package com.legacy.aether.client.render.entity.layer;

import com.legacy.aether.Aether;
import com.legacy.aether.client.render.entity.SliderRenderer;
import com.legacy.aether.entity.boss.EntitySlider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class SliderLayer implements LayerRenderer<EntitySlider>
{

	private static final ResourceLocation TEXTURE_GLOW = Aether.locate("textures/entity/slider/slider_awake_glow.png");

	private static final ResourceLocation TEXTURE_GLOW_RED = Aether.locate("textures/entity/slider/slider_awake_critical_glow.png");

	private final SliderRenderer renderer;

	public SliderLayer(SliderRenderer renderer)
	{
		super();

		this.renderer = renderer;
	}

	@Override
	public void render(EntitySlider slider, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (slider.isAwake())
		{
			this.renderer.bindTexture(slider.getMovementAI().hasCriticalHealth() ? TEXTURE_GLOW_RED : TEXTURE_GLOW);

			GlStateManager.enableBlend();
			GlStateManager.disableAlphaTest();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
			GlStateManager.depthMask(!slider.isInvisible());

			int i = 61680;
			int j = 150;
			int k = 150;
			OpenGlHelper.glMultiTexCoord2f(OpenGlHelper.GL_TEXTURE1, (float) j, (float) k);
			this.renderer.getMainModel().render(slider, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			i = slider.getBrightnessForRender();
			j = 150;
			k = 150;
			OpenGlHelper.glMultiTexCoord2f(OpenGlHelper.GL_TEXTURE1, (float) j, (float) k);
			this.renderer.setLightmap(slider);

			GlStateManager.depthMask(true);
			GlStateManager.disableBlend();
			GlStateManager.enableAlphaTest();
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}

}