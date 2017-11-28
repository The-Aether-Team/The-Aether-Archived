package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.SunSpiritModel;
import com.legacy.aether.entities.bosses.EntityFireMinion;

public class FireMinionRenderer extends RenderBiped<EntityFireMinion>
{

    private static final ResourceLocation SPIRIT = new ResourceLocation("aether_legacy", "textures/bosses/sun_spirit/sun_spirit.png");

    public FireMinionRenderer(RenderManager renderManager)
    {
        super(renderManager, new SunSpiritModel(0.0F, 0.0F), 0.4F);
        this.shadowSize = 0.8F;
        
    }

	@Override
    protected ResourceLocation getEntityTexture(EntityFireMinion entity)
    {
		return SPIRIT;
    }

}