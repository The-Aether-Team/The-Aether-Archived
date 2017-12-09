package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.projectile.PhoenixArrowRenderer;
import com.legacy.aether.entities.projectile.EntityPhoenixArrow;

public class PhoenixArrowFactory implements IRenderFactory<EntityPhoenixArrow> 
{

	@Override
	public Render<EntityPhoenixArrow> createRenderFor(RenderManager manager) 
	{
		return new PhoenixArrowRenderer(manager);
	}

}