package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.projectile.DartBaseRenderer;
import com.legacy.aether.common.entities.projectile.darts.EntityDartBase;

public class DartFactory<ENTITY extends EntityDartBase> implements IRenderFactory<ENTITY>
{

	@Override
	public Render<ENTITY> createRenderFor(RenderManager manager)
	{
		return new DartBaseRenderer<ENTITY>(manager);
	}

}