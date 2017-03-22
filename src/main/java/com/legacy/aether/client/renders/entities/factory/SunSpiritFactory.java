package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.SunSpiritRenderer;
import com.legacy.aether.common.entities.bosses.sun_spirit.EntitySunSpirit;

public class SunSpiritFactory implements IRenderFactory<EntitySunSpirit>
{

	@Override
	public Render<EntitySunSpirit> createRenderFor(RenderManager manager)
	{
		return new SunSpiritRenderer(manager);
	}

}