package com.gildedgames.the_aether.client.renders.entities;

import com.gildedgames.the_aether.entities.passive.EntityAerwhale;
import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.client.models.entities.AerwhaleModel;
import com.gildedgames.the_aether.client.models.entities.OldAerwhaleModel;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AerwhaleRenderer extends RenderLiving<EntityAerwhale>
{
    private static final ResourceLocation AERWHALE_TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/aerwhale/aerwhale.png");
    private static final ResourceLocation OLD_AERWHALE_TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/aerwhale/old_aerwhale.png");

    public AerwhaleRenderer(RenderManager rendermanagerIn)
    {
        super(rendermanagerIn, new AerwhaleModel(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityAerwhale aerwhale, float partialTickTime)
    {
        this.mainModel = AetherConfig.visual_options.legacy_models ? new OldAerwhaleModel() : new AerwhaleModel();

        GlStateManager.translate(0, 1.2D, 0);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAerwhale aerwhale)
    {
        return AetherConfig.visual_options.legacy_models ? OLD_AERWHALE_TEXTURE : AERWHALE_TEXTURE;
    }
}