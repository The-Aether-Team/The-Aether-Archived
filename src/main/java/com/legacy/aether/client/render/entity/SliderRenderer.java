package com.legacy.aether.client.render.entity;

import com.legacy.aether.Aether;
import com.legacy.aether.client.model.SliderModel;
import com.legacy.aether.client.render.entity.layer.SliderLayer;
import com.legacy.aether.entity.boss.EntitySlider;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class SliderRenderer extends RenderLiving<EntitySlider>
{

	private static final ResourceLocation TEXTURE_AWAKE = Aether.locate("textures/bosses/slider/slider_awake.png");

	private static final ResourceLocation TEXTURE_AWAKE_RED = Aether.locate("textures/bosses/slider/slider_awake_critical.png");

	private static final ResourceLocation TEXTURE_SLEEP = Aether.locate("textures/bosses/slider/slider_asleep.png");

	private static final ResourceLocation TEXTURE_SLEEP_RED = Aether.locate("textures/bosses/slider/slider_asleep_critical.png");

	public SliderRenderer(RenderManager renderManager)
	{
		super(renderManager, new SliderModel(0.0F, 12.0F), 1.5F);

		this.addLayer(new SliderLayer(this));
	}

	@Override
	protected void preRenderCallback(EntitySlider slider, float f)
	{

	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySlider slider)
	{
		if (slider.isAwake())
		{
			return slider.getMovementAI().hasCriticalHealth() ? TEXTURE_AWAKE_RED : TEXTURE_AWAKE;
		}

		return slider.getMovementAI().hasCriticalHealth() ? TEXTURE_SLEEP_RED : TEXTURE_SLEEP;
	}

}