package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.CockatriceRenderer;
import com.legacy.aether.server.entities.hostile.EntityCockatrice;

public class CockatriceFactory implements IRenderFactory<EntityCockatrice>
{

	@Override
	public Render<? super EntityCockatrice> createRenderFor(RenderManager manager) 
	{
		return new CockatriceRenderer(manager);
	}

}