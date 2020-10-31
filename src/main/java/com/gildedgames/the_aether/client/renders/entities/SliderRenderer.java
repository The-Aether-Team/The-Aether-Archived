package com.gildedgames.the_aether.client.renders.entities;

import com.gildedgames.the_aether.client.renders.entities.layer.SliderLayer;
import com.gildedgames.the_aether.entities.bosses.slider.EntitySlider;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.the_aether.client.models.entities.SliderModel;

public class SliderRenderer extends RenderLiving<EntitySlider>
{

	private static final ResourceLocation TEXTURE_AWAKE_RED = new ResourceLocation("aether_legacy", "textures/bosses/slider/slider_awake_critical.png");

	private static final ResourceLocation TEXTURE_AWAKE = new ResourceLocation("aether_legacy", "textures/bosses/slider/slider_awake.png");

	private static final ResourceLocation TEXTURE_SLEEP = new ResourceLocation("aether_legacy", "textures/bosses/slider/slider_asleep.png");

	private static final ResourceLocation TEXTURE_SLEEP_RED = new ResourceLocation("aether_legacy", "textures/bosses/slider/slider_asleep_critical.png");

	public SliderRenderer(RenderManager renderManager)
    {
        super(renderManager, new SliderModel(0.0F, 12.0F), 1.5F);
        this.addLayer(new SliderLayer((SliderModel) this.mainModel));
    }

	@Override
	protected void preRenderCallback(EntitySlider slider, float f) 
	{
		((SliderModel)this.mainModel).hurtAngle = slider.hurtAngle;
		((SliderModel)this.mainModel).hurtAngleX = slider.hurtAngleX;
		((SliderModel)this.mainModel).hurtAngleZ = slider.hurtAngleZ;
	}

    protected void renderLivingAt(EntitySlider slider, double x, double y, double z)
    {
    	super.renderLivingAt(slider, x, y, z);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntitySlider slider)
	{
		if (slider.isAwake())
		{
			return slider.criticalCondition() ? TEXTURE_AWAKE_RED : TEXTURE_AWAKE;
		}

		return slider.criticalCondition() ? TEXTURE_SLEEP_RED : TEXTURE_SLEEP;
	}

}