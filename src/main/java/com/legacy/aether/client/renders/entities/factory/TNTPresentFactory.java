package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.TNTPresentRenderer;
import com.legacy.aether.server.entities.block.EntityTNTPresent;

public class TNTPresentFactory  implements IRenderFactory<EntityTNTPresent>
{

	@Override
	public Render<EntityTNTPresent> createRenderFor(RenderManager manager)
	{
		return new TNTPresentRenderer(manager);
	}

}