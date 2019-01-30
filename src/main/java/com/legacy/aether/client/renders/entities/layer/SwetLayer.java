package com.legacy.aether.client.renders.entities.layer;

import com.legacy.aether.client.renders.entities.SwetRenderer;
import com.legacy.aether.entities.passive.mountable.EntitySwet;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class SwetLayer implements LayerRenderer<EntitySwet> 
{

	@SuppressWarnings("unused")
	private RenderManager manager;

	private SwetRenderer render;

	private ModelSlime slime;

	public SwetLayer(RenderManager manager, SwetRenderer render)
	{
		this.manager = manager;
		this.render = render;
		this.slime = new ModelSlime(0);
	}

	@Override
	public void doRenderLayer(EntitySwet swet, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (!swet.isInvisible())
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slime.setModelAttributes(this.render.getMainModel());
            this.slime.render(swet, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		return false;
	}

}