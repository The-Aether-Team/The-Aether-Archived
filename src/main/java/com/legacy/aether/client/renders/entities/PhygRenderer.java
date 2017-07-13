package com.legacy.aether.client.renders.entities;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.renders.entities.layer.PhygWingLayer;
import com.legacy.aether.common.entities.passive.mountable.EntityPhyg;

public class PhygRenderer extends RenderLiving<EntityPhyg>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/phyg/phyg.png");

	private static final ResourceLocation TEXTURE_SADDLED = new ResourceLocation("aether_legacy", "textures/entities/phyg/saddle.png");

	public PhygRenderer(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new ModelPig(), 0.7F);
		this.addLayer(new PhygWingLayer(rendermanagerIn));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPhyg entity)
	{
		return ((EntityPhyg) entity).getSaddled() ? TEXTURE_SADDLED : TEXTURE;
	}

}