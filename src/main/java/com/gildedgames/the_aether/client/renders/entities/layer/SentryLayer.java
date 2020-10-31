package com.gildedgames.the_aether.client.renders.entities.layer;

import com.gildedgames.the_aether.entities.hostile.EntitySentry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class SentryLayer implements LayerRenderer<EntitySentry>
{

    private static final ResourceLocation TEXTURE_EYE = new ResourceLocation("aether_legacy", "textures/entities/sentry/eye.png");

	private final ModelSlime model;

	public SentryLayer(ModelSlime model)
	{
		super();

		this.model = model;
	}

	@Override
	public void doRenderLayer(EntitySentry sentry, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();

		if (sentry.isAwake())
		{
			renderManager.renderEngine.bindTexture(TEXTURE_EYE);

	        GlStateManager.enableBlend();
	        GlStateManager.disableAlpha();
	        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

	        if (sentry.isInvisible())
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
	        GlStateManager.translate(0.0D, 0.0D, -0.001D);
	        this.model.render(sentry, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	        i = sentry.getBrightnessForRender();
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
		return true;
	}

}