package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.projectile.LightningKnifeRenderer;
import com.legacy.aether.server.entities.projectile.EntityLightningKnife;

public class LightningKnifeFactory implements IRenderFactory<EntityLightningKnife> 
{

	@Override
	public Render<EntityLightningKnife> createRenderFor(RenderManager manager) 
	{
		return new LightningKnifeRenderer(manager);
	}

}