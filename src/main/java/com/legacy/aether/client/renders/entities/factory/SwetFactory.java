package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.SwetRenderer;
import com.legacy.aether.server.entities.passive.mountable.EntitySwet;

public class SwetFactory implements IRenderFactory<EntitySwet>
{

	@Override
	public Render<EntitySwet> createRenderFor(RenderManager manager) 
	{
		return new SwetRenderer(manager);
	}

}