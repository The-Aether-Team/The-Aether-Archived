package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.FlyingCowModel;
import com.legacy.aether.client.renders.entities.layer.FlyingCowWingLayer;
import com.legacy.aether.client.renders.entities.layer.LayerFlyingCowSaddle;
import com.legacy.aether.entities.passive.mountable.EntityFlyingCow;

public class FlyingCowRenderer extends RenderLiving<EntityFlyingCow>
{

	private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/flying_cow/flying_cow.png");

	public FlyingCowRenderer(RenderManager renderManager)
	{
		super(renderManager, new FlyingCowModel(0.0F), 0.7F);
		this.addLayer(new FlyingCowWingLayer(renderManager));
		this.addLayer(new LayerFlyingCowSaddle(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingCow entity) 
	{
		return TEXTURE;
	}

}