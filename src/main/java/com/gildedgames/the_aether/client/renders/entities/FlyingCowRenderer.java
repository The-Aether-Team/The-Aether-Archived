package com.gildedgames.the_aether.client.renders.entities;

import com.gildedgames.the_aether.client.renders.entities.layer.FlyingCowWingLayer;
import com.gildedgames.the_aether.client.renders.entities.layer.LayerFlyingCowSaddle;
import com.gildedgames.the_aether.entities.passive.mountable.EntityFlyingCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.the_aether.client.models.entities.FlyingCowModel;

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