package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.ParachuteRenderer;
import com.legacy.aether.common.entities.passive.mountable.EntityParachute;

public class ParachuteFactory implements IRenderFactory<EntityParachute>
{

	@Override
	public Render<EntityParachute> createRenderFor(RenderManager manager) 
	{
		return new ParachuteRenderer(manager);
	}

}