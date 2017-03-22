package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.MiniCloudModel;
import com.legacy.aether.common.entities.passive.EntityMiniCloud;

public class MiniCloudRenderer extends RenderLiving<EntityMiniCloud>
{

	public MiniCloudRenderer(RenderManager rendermanagerIn) 
	{
		super(rendermanagerIn, new MiniCloudModel(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMiniCloud entity) 
	{
		return new ResourceLocation("aether_legacy", "textures/entities/mini_cloud/mini_cloud.png");
	}

}