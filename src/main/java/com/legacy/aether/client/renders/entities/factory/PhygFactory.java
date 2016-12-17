package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.PhygRenderer;
import com.legacy.aether.server.entities.passive.mountable.EntityPhyg;

public class PhygFactory implements IRenderFactory<EntityPhyg>
{

	@Override
	public Render<EntityPhyg> createRenderFor(RenderManager manager) 
	{
		return new PhygRenderer(manager);
	}

}