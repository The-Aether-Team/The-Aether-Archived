package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.AechorPlantRenderer;
import com.legacy.aether.server.entities.hostile.EntityAechorPlant;

public class AechorPlantFactory implements IRenderFactory<EntityAechorPlant>
{

	@Override
	public Render<EntityAechorPlant> createRenderFor(RenderManager manager)
	{
		return new AechorPlantRenderer(manager);
	}

}