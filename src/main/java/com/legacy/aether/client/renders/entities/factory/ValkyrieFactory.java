package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.ValkyrieRenderer;
import com.legacy.aether.entities.bosses.EntityValkyrie;

public class ValkyrieFactory implements IRenderFactory<EntityValkyrie>
{

	@Override
	public Render<EntityValkyrie> createRenderFor(RenderManager manager) 
	{
		return new ValkyrieRenderer(manager);
	}

}