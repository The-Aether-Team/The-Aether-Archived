package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

import com.legacy.aether.client.renders.entities.SwetRenderer;
import com.legacy.aether.entities.passive.mountable.EntitySwet;

public class SwetLayer implements LayerRenderer<EntitySwet> 
{

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
        this.slime.setModelAttributes(this.render.getMainModel());
        this.slime.setLivingAnimations(swet, limbSwing, limbSwingAmount, partialTicks);

		GlStateManager.pushMatrix();
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

		this.manager.renderEngine.bindTexture(this.render.getEntityTexture(swet));
        this.slime.render(swet, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        GlStateManager.popMatrix();
	}

	@Override
	public boolean shouldCombineTextures() 
	{
		return false;
	}

}