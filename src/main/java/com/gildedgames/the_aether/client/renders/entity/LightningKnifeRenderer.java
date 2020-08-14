package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.projectile.EntityLightningKnife;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class LightningKnifeRenderer extends Render {

    public LightningKnifeRenderer() {
        super();
    }

    public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
        this.doRenderKnife((EntityLightningKnife) var1, var2, var4, var6, var8, var9);
    }

    public void doRenderKnife(EntityLightningKnife arr, double d, double d1, double d2, float yaw, float time) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(yaw, 0F, 1.0F, 0F);
        GL11.glRotatef(-(arr.prevRotationPitch + (arr.rotationPitch - arr.prevRotationPitch) * time), 1F, 0F, 0F);
        GL11.glRotatef(45f, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-0.5F, 0.0F, -0.5F);

        this.bindTexture(Aether.locate("textures/items/weapons/lightning_knife.png"));

        float texMinX = 0F;
        float texMaxX = 1.0F;
        float texMinY = 0F;
        float texMaxY = 1.0F;
        float f4 = 1.0F;
        float f8 = 0.0625F;

        Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, texMaxX, texMaxY);
        tessellator.addVertexWithUV(f4, 0.0D, 0.0D, texMinX, texMaxY);
        tessellator.addVertexWithUV(f4, 0.0D, 1.0D, texMinX, texMinY);
        tessellator.addVertexWithUV(0.0D, 0.0D, 1.0D, texMaxX, texMinY);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        tessellator.addVertexWithUV(0.0D, 0.0F - f8, 1.0D, texMaxX, texMinY);
        tessellator.addVertexWithUV(f4, 0.0F - f8, 1.0D, texMinX, texMinY);
        tessellator.addVertexWithUV(f4, 0.0F - f8, 0.0D, texMinX, texMaxY);
        tessellator.addVertexWithUV(0.0D, 0.0F - f8, 0.0D, texMaxX, texMaxY);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);

        for (int i = 0; i < 16; i++) {
            float f9 = (float) i / 16F;
            float f13 = (texMaxX + (texMinX - texMaxX) * f9) - 0.001953125F;
            float f17 = f4 * f9;
            tessellator.addVertexWithUV(f17, 0.0F - f8, 0.0D, f13, texMaxY);
            tessellator.addVertexWithUV(f17, 0.0D, 0.0D, f13, texMaxY);
            tessellator.addVertexWithUV(f17, 0.0D, 1.0D, f13, texMinY);
            tessellator.addVertexWithUV(f17, 0.0F - f8, 1.0D, f13, texMinY);
        }

        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);

        for (int j = 0; j < 16; j++) {
            float f10 = (float) j / 16F;
            float f14 = (texMaxX + (texMinX - texMaxX) * f10) - 0.001953125F;
            float f18 = f4 * f10 + 0.0625F;
            tessellator.addVertexWithUV(f18, 0.0F - f8, 1.0D, f14, texMinY);
            tessellator.addVertexWithUV(f18, 0.0D, 1.0D, f14, texMinY);
            tessellator.addVertexWithUV(f18, 0.0D, 0.0D, f14, texMaxY);
            tessellator.addVertexWithUV(f18, 0.0F - f8, 0.0D, f14, texMaxY);
        }

        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for (int k = 0; k < 16; k++) {
            float f11 = (float) k / 16F;
            float f15 = (texMaxY + (texMinY - texMaxY) * f11) - 0.001953125F;
            float f19 = f4 * f11 + 0.0625F;
            tessellator.addVertexWithUV(0.0D, 0.0D, f19, texMaxX, f15);
            tessellator.addVertexWithUV(f4, 0.0D, f19, texMinX, f15);
            tessellator.addVertexWithUV(f4, 0.0F - f8, f19, texMinX, f15);
            tessellator.addVertexWithUV(0.0D, 0.0F - f8, f19, texMaxX, f15);
        }

        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 1.0F);

        for (int l = 0; l < 16; l++) {
            float f12 = (float) l / 16F;
            float f16 = (texMaxY + (texMinY - texMaxY) * f12) - 0.001953125F;
            float f20 = f4 * f12;
            tessellator.addVertexWithUV(f4, 0.0D, f20, texMinX, f16);
            tessellator.addVertexWithUV(0.0D, 0.0D, f20, texMaxX, f16);
            tessellator.addVertexWithUV(0.0D, 0.0F - f8, f20, texMaxX, f16);
            tessellator.addVertexWithUV(f4, 0.0F - f8, f20, texMinX, f16);
        }

        tessellator.draw();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }

}