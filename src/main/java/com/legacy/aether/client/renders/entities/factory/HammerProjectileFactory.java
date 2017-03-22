package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.projectile.HammerProjectileRenderer;
import com.legacy.aether.common.entities.projectile.EntityHammerProjectile;

public class HammerProjectileFactory implements IRenderFactory<EntityHammerProjectile>
{

	@Override
	public Render<EntityHammerProjectile> createRenderFor(RenderManager manager)
	{
		return new HammerProjectileRenderer(manager);
	}

}