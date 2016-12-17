package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.ZephyrModel;
import com.legacy.aether.server.entities.hostile.EntityZephyr;

public class ZephyrRenderer extends RenderLiving<EntityZephyr> 
{

    private static final ResourceLocation ZEPHYR_TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/zephyr/zephyr.png");

	public ZephyrRenderer(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ZephyrModel(), 0.5F);
	}

    protected void renderZephyrMovement(EntityZephyr zephyr, float partialTickTime)
    {
        float f1 = ((float)zephyr.shootingAI.prevAttackCounter + (float)(zephyr.shootingAI.attackCounter - zephyr.shootingAI.prevAttackCounter) * partialTickTime) / 20.0F;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        f1 = 1.0F / (f1 * f1 * f1 * f1 * f1 * 2.0F + 1.0F);

        float f2 = (8.0F + f1) / 2.0F;
        float f3 = (8.0F + 1.0F / f1) / 2.0F;

        GlStateManager.scale(f3, f2, f3);
        GlStateManager.translate(0, 0.5D, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void preRenderCallback(EntityZephyr zephyr, float partialTickTime)
    {
        this.renderZephyrMovement(zephyr, partialTickTime);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityZephyr zephyr) 
	{
		return ZEPHYR_TEXTURE;
	}

}