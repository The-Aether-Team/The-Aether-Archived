package com.legacy.aether.client.renders.entities;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.renders.entities.layer.LayerPhygSaddle;
import com.legacy.aether.client.renders.entities.layer.PhygWingLayer;
import com.legacy.aether.entities.passive.mountable.EntityPhyg;

public class PhygRenderer extends RenderLiving<EntityPhyg>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/phyg/phyg.png");

	public PhygRenderer(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ModelPig(), 0.7F);
		this.addLayer(new PhygWingLayer(rendermanagerIn));
		this.addLayer(new LayerPhygSaddle(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPhyg entity)
	{
		return TEXTURE;
	}

}