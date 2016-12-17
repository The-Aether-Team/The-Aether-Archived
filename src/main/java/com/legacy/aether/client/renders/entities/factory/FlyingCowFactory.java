package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.FlyingCowRenderer;
import com.legacy.aether.server.entities.passive.mountable.EntityFlyingCow;

public class FlyingCowFactory implements IRenderFactory<EntityFlyingCow>
{

	@Override
	public Render<EntityFlyingCow> createRenderFor(RenderManager manager)
	{
		return new FlyingCowRenderer(manager);
	}

}