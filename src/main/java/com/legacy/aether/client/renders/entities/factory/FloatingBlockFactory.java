package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.FloatingBlockRenderer;
import com.legacy.aether.common.entities.block.EntityFloatingBlock;

public class FloatingBlockFactory implements IRenderFactory<EntityFloatingBlock>
{

	@Override
	public Render<EntityFloatingBlock> createRenderFor(RenderManager manager)
	{
		return new FloatingBlockRenderer(manager);
	}

}