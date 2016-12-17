package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.MimicRenderer;
import com.legacy.aether.server.entities.hostile.EntityMimic;

public class MimicFactory implements IRenderFactory<EntityMimic>
{

	@Override
	public Render<EntityMimic> createRenderFor(RenderManager manager) 
	{
		return new MimicRenderer(manager);
	}

}