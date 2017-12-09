package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.projectile.IceyBallRenderer;
import com.legacy.aether.entities.projectile.crystals.EntityIceyBall;

public class IceyBallFactory implements IRenderFactory<EntityIceyBall>
{

	@Override
	public Render<EntityIceyBall> createRenderFor(RenderManager manager) 
	{
		return new IceyBallRenderer(manager);
	}

}