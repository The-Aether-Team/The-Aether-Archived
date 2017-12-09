package com.legacy.aether.client.renders.entities.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import com.legacy.aether.client.renders.entities.SliderRenderer;
import com.legacy.aether.entities.bosses.slider.EntitySlider;

public class SliderFactory implements IRenderFactory<EntitySlider>
{

	@Override
	public Render<EntitySlider> createRenderFor(RenderManager manager)
	{
		return new SliderRenderer(manager);
	}

}