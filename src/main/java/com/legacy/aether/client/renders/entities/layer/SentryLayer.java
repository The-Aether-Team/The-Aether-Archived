package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.server.entities.hostile.EntitySentry;

public class SentryLayer implements LayerRenderer<EntitySentry>
{

    private static final ResourceLocation TEXTURE_EYE = new ResourceLocation("aether_legacy", "textures/entities/sentry/eye.png");

	@Override
	public void doRenderLayer(EntitySentry sentry, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
	{
        if (sentry.getAttackTarget() != null)
        {
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(TEXTURE_EYE);
            float f1 = 1.0F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            char j = 61680;
            int k = j % 65536;
            int l = j / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k / 1.0F, (float)l / 1.0F);
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