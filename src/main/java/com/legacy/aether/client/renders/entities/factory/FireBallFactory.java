package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.projectile.FireBallRenderer;
import com.legacy.aether.common.entities.projectile.crystals.EntityFireBall;

public class FireBallFactory implements IRenderFactory<EntityFireBall>
{

	@Override
	public Render<EntityFireBall> createRenderFor(RenderManager manager)
	{
		return new FireBallRenderer(manager);
	}

}