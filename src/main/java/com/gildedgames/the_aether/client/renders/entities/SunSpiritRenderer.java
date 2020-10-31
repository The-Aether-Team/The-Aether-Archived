package com.gildedgames.the_aether.client.renders.entities;

import com.gildedgames.the_aether.entities.bosses.sun_spirit.EntitySunSpirit;
import com.gildedgames.the_aether.client.models.entities.SunSpiritModel;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class SunSpiritRenderer extends RenderBiped<EntitySunSpirit>
{

    private static final ResourceLocation SPIRIT = new ResourceLocation("aether_legacy", "textures/bosses/sun_spirit/sun_spirit.png");
    private static final ResourceLocation SPIRIT_FROZE = new ResourceLocation("aether_legacy", "textures/bosses/sun_spirit/frozen_sun_spirit.png");

    public SunSpiritRenderer(RenderManager renderManager)
    {
        super(renderManager, new SunSpiritModel(0.0F, 0.0F), 1.0F);
        this.shadowSize = 0.8F;
        
    }

    @Override
    protected void preRenderCallback(EntitySunSpirit spirit, float partialTickTime)
    {
    	GlStateManager.translate(0, 0.5D, 0);
        //this.renderZephyrMovement(zephyr, partialTickTime);
    }

	@Override
    protected ResourceLocation getEntityTexture(EntitySunSpirit entity)
    {
		return entity.isFreezing() ? SPIRIT_FROZE : SPIRIT;
    }

}