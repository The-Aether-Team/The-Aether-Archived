package com.gildedgames.the_aether.client.renders.entities.layer;

import com.gildedgames.the_aether.entities.bosses.slider.EntitySlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.the_aether.client.models.entities.SliderModel;

public class SliderLayer implements LayerRenderer<EntitySlider>
{

	private static final ResourceLocation TEXTURE_GLOW = new ResourceLocation("aether_legacy", "textures/bosses/slider/slider_awake_glow.png");

	private static final ResourceLocation TEXTURE_GLOW_RED = new ResourceLocation("aether_legacy", "textures/bosses/slider/slider_awake_critical_glow.png");

	private final SliderModel model;

	public SliderLayer(SliderModel model)
	{
		super();

		this.model = model;
	}

	@Override
	public void doRenderLayer(EntitySlider slider, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
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

	        GlStateManager.enableBlend();
	        GlStateManager.disableAlpha();
	        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

	        if (slider.isInvisible())
	        {
	            GlStateManager.depthMask(false);
	        }
	        else
	        {
	            GlStateManager.depthMask(true);
	        }

	        int i = 61680;
	        int j = i % 65536;
	        int k = i / 65536;
	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        this.model.render(slider, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	        i = slider.getBrightnessForRender();
	        j = i % 65536;
	        k = i / 65536;
	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
	        GlStateManager.disableBlend();
	        GlStateManager.enableAlpha();
		}
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		return false;
	}

}