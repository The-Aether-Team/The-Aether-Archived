package com.gildedgames.the_aether.client.renders.entities.layer;

import com.gildedgames.the_aether.entities.passive.mountable.EntityPhyg;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.the_aether.client.models.entities.PhygWingModel;

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
		return true;
	}

}