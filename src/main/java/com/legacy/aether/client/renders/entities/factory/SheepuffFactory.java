package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.SheepuffRenderer;
import com.legacy.aether.entities.passive.EntitySheepuff;

public class SheepuffFactory implements IRenderFactory<EntitySheepuff>
{

	@Override
	public Render<EntitySheepuff> createRenderFor(RenderManager manager)
	{
		return new SheepuffRenderer(manager);
	}

}