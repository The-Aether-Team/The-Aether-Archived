package com.legacy.aether.client.renders.entities;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.renders.entities.layer.SwetLayer;
import com.legacy.aether.common.entities.passive.mountable.EntitySwet;

public class SwetRenderer extends RenderLiving<EntitySwet> 
{

    private static final ResourceLocation TEXTURE_BLUE = new ResourceLocation("aether_legacy", "textures/entities/swet/swet_blue.png");

    private static final ResourceLocation TEXTURE_GOLDEN = new ResourceLocation("aether_legacy", "textures/entities/swet/swet_golden.png");

	public SwetRenderer(RenderManager renderManager)
	{
		super(renderManager, new ModelSlime(16), 0.3F);
		this.addLayer(new SwetLayer(renderManager, this));
	}

    protected void setupAnimation(EntitySwet swet, float f)
    {
        float f2 = 1.0F;
        float f1 = 1.0F;
        float f3 = 1.5F;

        if(!swet.onGround)
        {
            if(swet.motionY > 0.85D)
            {
                f1 = 1.425F;
                f2 = 0.575F;
            }
            else if(swet.motionY < -0.85D)
            {
                f1 = 0.575F;
                f2 = 1.425F;
            } 
            else
            {
                float f4 = (float)swet.motionY * 0.5F;
                f1 += f4;
                f2 -= f4;
            }
        }

        if(!swet.getPassengers().isEmpty())
        {
            f3 = 1.5F + (swet.getPassengers().get(0).width + swet.getPassengers().get(0).height) * 0.75F;
        }

        GlStateManager.scale(f2 * f3, f1 * f3, f2 * f3);
    }

    @Override
    protected void preRenderCallback(EntitySwet swet, float f)
    {
        this.setupAnimation(swet, f);
    }

	@Override
	public ResourceLocation getEntityTexture(EntitySwet swet)
	{
        return swet.getType() == 1 ? TEXTURE_BLUE : TEXTURE_GOLDEN;
	}
}