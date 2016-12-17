package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.ZephyrRenderer;
import com.legacy.aether.server.entities.hostile.EntityZephyr;

public class ZephyrFactory implements IRenderFactory<EntityZephyr>
{

	@Override
	public Render<EntityZephyr> createRenderFor(RenderManager manager) 
	{
		return new ZephyrRenderer(manager);
	}

}