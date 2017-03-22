package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.MiniCloudRenderer;
import com.legacy.aether.common.entities.passive.EntityMiniCloud;

public class MiniCloudFactory implements IRenderFactory<EntityMiniCloud>
{

	@Override
	public Render<EntityMiniCloud> createRenderFor(RenderManager manager)
	{
		return new MiniCloudRenderer(manager);
	}

}