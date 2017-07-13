package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.MoaModel;
import com.legacy.aether.common.entities.passive.mountable.EntityMoa;

public class MoaLayer implements LayerRenderer<EntityMoa>
{

	private static final ResourceLocation TEXTURE_BRACE = new ResourceLocation("aether_legacy", "textures/entities/moa/moa_brace.png");

	private RenderManager renderManager;

	private MoaModel moaModel;

	public MoaLayer(RenderManager manager, MoaModel moaModel)
	{
		this.renderManager = manager;
		this.moaModel = moaModel;
	}

	@Override
	public void doRenderLayer(EntityMoa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		if (moa.isSitting())
		{
			this.renderManager.renderEngine.bindTexture(TEXTURE_BRACE);
			this.moaModel.render(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}

}