package com.gildedgames.the_aether.client.renders.entities;

import com.gildedgames.the_aether.client.renders.entities.layer.SwetLayer;
import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class SwetRenderer extends RenderLiving<EntitySwet>
{
    private static final ResourceLocation TEXTURE_BLUE = new ResourceLocation("aether_legacy", "textures/entities/swet/swet_blue.png");

    private static final ResourceLocation TEXTURE_GOLDEN = new ResourceLocation("aether_legacy", "textures/entities/swet/swet_golden.png");

	public SwetRenderer(RenderManager renderManager)
	{
		super(renderManager, new ModelSlime(16), 0.3F);
		this.addLayer(new SwetLayer(renderManager, this));
	}

    @Override
    protected void preRenderCallback(EntitySwet swet, float partialTicks)
    {
        float f1 = swet.swetHeight; //height
        float f2 = swet.swetWidth; //width
        float f3 = 1.5F; //scale

        if(!swet.getPassengers().isEmpty())
        {
            f3 = 1.5F + (swet.getPassengers().get(0).width + swet.getPassengers().get(0).height) * 0.75F;
        }

        GlStateManager.scale(f2 * f3, f1 * f3, f2 * f3);
    }

	@Override
	public ResourceLocation getEntityTexture(EntitySwet swet)
	{
        return swet.getType() == 1 ? TEXTURE_BLUE : TEXTURE_GOLDEN;
	}
}