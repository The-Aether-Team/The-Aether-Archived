package com.gildedgames.the_aether.client.renders.entities;

import com.gildedgames.the_aether.client.renders.entities.layer.SheepuffCoatLayer;
import com.gildedgames.the_aether.entities.passive.EntitySheepuff;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.the_aether.client.models.entities.SheepuffWoolModel;

public class SheepuffRenderer extends RenderLiving<EntitySheepuff>
{

	public SheepuffRenderer(RenderManager renderManager) 
	{
		super(renderManager, new SheepuffWoolModel(), 0.7F);
		this.addLayer(new SheepuffCoatLayer(renderManager, this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySheepuff entity) 
	{
		return new ResourceLocation("aether_legacy", "textures/entities/sheepuff/sheepuff.png");
	}

}