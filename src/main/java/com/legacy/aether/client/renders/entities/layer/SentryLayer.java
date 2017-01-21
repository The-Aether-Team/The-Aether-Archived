package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.server.entities.hostile.EntitySentry;

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
	        this.model.render(sentry, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	        i = sentry.getBrightnessForRender(partialTicks);
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