package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.MoaRenderer;
import com.legacy.aether.server.entities.passive.mountable.EntityMoa;

public class MoaFactory implements IRenderFactory<EntityMoa>
{

	@Override
	public Render<EntityMoa> createRenderFor(RenderManager manager)
	{
		return new MoaRenderer(manager);
	}

}