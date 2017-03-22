package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.SunSpiritModel;
import com.legacy.aether.common.entities.bosses.sun_spirit.EntitySunSpirit;

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
    protected ResourceLocation getEntityTexture(EntitySunSpirit entity)
    {
		return entity.isFreezing() ? SPIRIT_FROZE : SPIRIT;
    }

}