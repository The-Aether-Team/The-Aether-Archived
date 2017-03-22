package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.PhygWingModel;
import com.legacy.aether.common.entities.passive.mountable.EntityPhyg;

public class PhygWingLayer implements LayerRenderer<EntityPhyg> 
{

	private static final ResourceLocation TEXTURE_WINGS = new ResourceLocation("aether_legacy", "textures/entities/phyg/wings.png");

	private RenderManager renderManager;

	private PhygWingModel model;

	public PhygWingLayer(RenderManager manager)
	{
		this.renderManager = manager;
		this.model = new PhygWingModel();
	}

	@Override
	public void doRenderLayer(EntityPhyg phyg, float limbSwing, float prevLimbSwing, float partialTicks, float rotation, float interpolateRotation, float prevRotationPitch, float scale) 
	{
		this.renderManager.renderEngine.bindTexture(TEXTURE_WINGS);

		this.model.render(phyg, limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale);
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}

}