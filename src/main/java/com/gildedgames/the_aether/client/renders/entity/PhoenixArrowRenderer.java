package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.projectile.EntityPhoenixArrow;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class PhoenixArrowRenderer extends Render {

    public PhoenixArrowRenderer() {
        super();

    }

    public void renderArrow(EntityPhoenixArrow var1, double var2, double var4, double var6, float var8, float var9) {
        if (var1.prevRotationYaw != 0.0F || var1.prevRotationPitch != 0.0F) {
            this.bindTexture(Aether.locate("textures/entities/projectile/flaming_arrow.png"));
            GL11.glPushMatrix();
            GL11.glTranslatef((float) var2, (float) var4, (float) var6);
            GL11.glRotatef(var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9 - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9, 0.0F, 0.0F, 1.0F);
            Tessellator tessellator = Tessellator.instance;
            byte b0 = 0;
            float f2 = 0.0F;
            float f3 = 0.5F;
            float f4 = (float) (0 + b0 * 10) / 32.0F;
            float f5 = (float) (5 + b0 * 10) / 32.0F;
            float f6 = 0.0F;
            float f7 = 0.15625F;
            float f8 = (float) (5 + b0 * 10) / 32.0F;
            float f9 = (float) (10 + b0 * 10) / 32.0F;
            float f10 = 0.05625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            float f11 = (float) var1.arrowShake - var9;

            if (f11 > 0.0F) {
                float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
                GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
            }

            GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(f10, f10, f10);
            GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
            GL11.glNormal3f(f10, 0.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double) f6, (double) f8);
            tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double) f7, (double) f8);
            tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double) f7, (double) f9);
            tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double) f6, (double) f9);
            tessellator.draw();
            GL11.glNormal3f(-f10, 0.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double) f6, (double) f8);
            tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double) f7, (double) f8);
            tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double) f7, (double) f9);
            tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double) f6, (double) f9);
            tessellator.draw();

            for (int i = 0; i < 4; ++i) {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glNormal3f(0.0F, 0.0F, f10);
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double) f2, (double) f4);
                tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, (double) f3, (double) f4);
                tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, (double) f3, (double) f5);
                tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double) f2, (double) f5);
                tessellator.draw();
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.renderArrow((EntityPhoenixArrow) entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return Aether.locate("textures/entities/projectile/flaming_arrow.png");
    }

}