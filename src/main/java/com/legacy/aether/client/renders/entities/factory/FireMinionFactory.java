package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.FireMinionRenderer;
import com.legacy.aether.entities.bosses.EntityFireMinion;

public class FireMinionFactory implements IRenderFactory<EntityFireMinion>
{

	@Override
	public Render<EntityFireMinion> createRenderFor(RenderManager manager) 
	{
		return new FireMinionRenderer(manager);
	}

}