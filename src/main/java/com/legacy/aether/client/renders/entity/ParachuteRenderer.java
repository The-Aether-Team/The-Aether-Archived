package com.legacy.aether.client.renders.entity;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.passive.mountable.EntityParachute;

public class ParachuteRenderer extends Render {

    private final RenderBlocks renderBlocks = new RenderBlocks();

    public ParachuteRenderer() {
        super();
    }

    public void renderParachute(EntityParachute entityParachute, double d, double d1, double d2, float f, float f1) {
        this.bindTexture(TextureMap.locationBlocksTexture);

        int meta = entityParachute.isGoldenParachute ? 2 : 0;

        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1 + 0.5F, (float) d2);
        this.bindEntityTexture(entityParachute);
        this.renderBlocks.renderBlockAsItem(BlocksAether.aercloud, meta, entityParachute.getBrightness(f1));
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderParachute((EntityParachute) entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TextureMap.locationBlocksTexture;
    }

}