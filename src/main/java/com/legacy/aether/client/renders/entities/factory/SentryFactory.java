package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.SentryRenderer;
import com.legacy.aether.server.entities.hostile.EntitySentry;

public class SentryFactory implements IRenderFactory<EntitySentry>
{

	@Override
	public Render<EntitySentry> createRenderFor(RenderManager manager) 
	{
		return new SentryRenderer(manager);
	}

}