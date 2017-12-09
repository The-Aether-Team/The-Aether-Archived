package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.AerbunnyRenderer;
import com.legacy.aether.entities.passive.mountable.EntityAerbunny;

public class AerbunnyFactory implements IRenderFactory<EntityAerbunny>
{

	@Override
	public Render<EntityAerbunny> createRenderFor(RenderManager manager) 
	{
		return new AerbunnyRenderer(manager);
	}

}