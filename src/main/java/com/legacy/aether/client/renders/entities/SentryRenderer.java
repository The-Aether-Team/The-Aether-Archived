package com.legacy.aether.client.renders.entities;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.client.renders.entities.layer.SentryLayer;
import com.legacy.aether.server.entities.hostile.EntitySentry;

public class SentryRenderer extends RenderLiving<EntitySentry>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/sentry/sentry.png");

    private static final ResourceLocation TEXTURE_LIT = new ResourceLocation("aether_legacy", "textures/entities/sentry/sentry_lit.png");

	public SentryRenderer(RenderManager renderManager)
    {
        super(renderManager, new ModelSlime(0), 0.3F);
        this.addLayer(new SentryLayer((ModelSlime) this.getMainModel()));
    }

    protected void preRenderCallback(EntitySentry entityliving, float f)
    {
        float f1 = 1.75F;
        GL11.glScalef(f1, f1, f1);
    }

    protected ResourceLocation getEntityTexture(EntitySentry entity)
    {
        return !entity.isAwake() ? TEXTURE : TEXTURE_LIT;
    }

}