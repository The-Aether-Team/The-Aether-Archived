package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.projectile.ZephyrSnowballRenderer;
import com.legacy.aether.entities.projectile.EntityZephyrSnowball;

public class ZephyrSnowballFactory implements IRenderFactory<EntityZephyrSnowball>
{

	@Override
	public Render<EntityZephyrSnowball> createRenderFor(RenderManager manager) 
	{
		return new ZephyrSnowballRenderer(manager);
	}

}