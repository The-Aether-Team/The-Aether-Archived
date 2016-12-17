package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.projectile.ThunderBallRenderer;
import com.legacy.aether.server.entities.projectile.crystals.EntityThunderBall;

public class ThunderBallFactory implements IRenderFactory<EntityThunderBall>
{

	@Override
	public Render<EntityThunderBall> createRenderFor(RenderManager manager)
	{
		return new ThunderBallRenderer(manager);
	}

}
