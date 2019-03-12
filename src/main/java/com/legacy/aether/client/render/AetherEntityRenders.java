package com.legacy.aether.client.render;

import com.legacy.aether.client.render.entity.SliderRenderer;
import com.legacy.aether.entity.boss.EntitySlider;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class AetherEntityRenders
{

	public static void initialization()
	{
		register(EntitySlider.class, SliderRenderer::new);
	}

	private static <T extends Entity> void register(Class<T> entityClass, IRenderFactory<? super T> renderFactory)
	{
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}

}