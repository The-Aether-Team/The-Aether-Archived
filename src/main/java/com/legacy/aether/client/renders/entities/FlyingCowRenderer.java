package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.FlyingCowModel;
import com.legacy.aether.client.renders.entities.layer.FlyingCowWingLayer;
import com.legacy.aether.common.entities.passive.mountable.EntityFlyingCow;

public class FlyingCowRenderer extends RenderLiving<EntityFlyingCow>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/flying_cow/flying_cow.png");

	private static final ResourceLocation TEXTURE_SADDLED = new ResourceLocation("aether_legacy", "textures/entities/flying_cow/saddle.png");

	public FlyingCowRenderer(RenderManager renderManager)
	{
		super(renderManager, new FlyingCowModel(), 0.7F);
		this.addLayer(new FlyingCowWingLayer(renderManager));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingCow entity) 
	{
		return entity.getSaddled() ? TEXTURE_SADDLED : TEXTURE;
	}

}