package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.client.models.entities.AerwhaleModel;
import com.gildedgames.the_aether.client.models.entities.OldAerwhaleModel;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class AerwhaleRenderer extends RenderLiving
{
    private static final ResourceLocation AERWHALE_TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/aerwhale/aerwhale.png");
    private static final ResourceLocation OLD_AERWHALE_TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/aerwhale/old_aerwhale.png");

    public AerwhaleRenderer()
    {
        super(AetherConfig.oldMobsEnabled() ? new OldAerwhaleModel() : new AerwhaleModel(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase aerwhale, float partialTickTime)
    {
        GL11.glTranslated(0, 1.2D, 0);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity aerwhale)
    {
        return AetherConfig.oldMobsEnabled() ? OLD_AERWHALE_TEXTURE : AERWHALE_TEXTURE;
    }
}