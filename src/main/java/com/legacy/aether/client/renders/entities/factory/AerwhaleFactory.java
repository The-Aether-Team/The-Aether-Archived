package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.AerwhaleRenderer;
import com.legacy.aether.common.entities.passive.EntityAerwhale;

public class AerwhaleFactory implements IRenderFactory<EntityAerwhale> 
{

	@Override
	public Render<EntityAerwhale> createRenderFor(RenderManager manager)
	{
		return new AerwhaleRenderer(manager);
	}

}