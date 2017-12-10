package com.legacy.aether.client.renders.entities.projectile;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.legacy.aether.Aether;
import com.legacy.aether.entities.projectile.EntityLightningKnife;

public class LightningKnifeRenderer extends Render<EntityLightningKnife> 
{

	public LightningKnifeRenderer(RenderManager renderManager)
	{
		super(renderManager);
	}

    public void doRender(EntityLightningKnife var1, double var2, double var4, double var6, float var8, float var9)
    {
        this.doRenderKnife(var1, var2, var4, var6, var8, var9);
    }

	public void doRenderKnife(EntityLightningKnife arr, double d, double d1, double d2, float yaw, float time)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
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

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertex = tessellator.getBuffer();

        vertex.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        vertex.pos(0.0D, 0.0D, 0.0D).tex(texMaxX, texMaxY).normal(0.0F, 0.0F, 1.0F).endVertex();
        vertex.pos(f4, 0.0D, 0.0D).tex(texMinX, texMaxY).normal(0.0F, 0.0F, 1.0F).endVertex();
        vertex.pos(f4, 0.0D, 1.0D).tex(texMinX, texMinY).normal(0.0F, 0.0F, 1.0F).endVertex();
        vertex.pos(0.0D, 0.0D, 1.0D).tex(texMaxX, texMinY).normal(0.0F, 0.0F, 1.0F).endVertex();
        tessellator.draw();

        vertex.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        vertex.pos(0.0D, 0.0F - f8, 1.0D).tex(texMaxX, texMinY).normal(0.0F, 0.0F, -1F).endVertex();
        vertex.pos(f4, 0.0F - f8, 1.0D).tex(texMinX, texMinY).normal(0.0F, 0.0F, -1F).endVertex();
        vertex.pos(f4, 0.0F - f8, 0.0D).tex(texMinX, texMaxY).normal(0.0F, 0.0F, -1F).endVertex();
        vertex.pos(0.0D, 0.0F - f8, 0.0D).tex(texMaxX, texMaxY).normal(0.0F, 0.0F, -1F).endVertex();
        tessellator.draw();

        vertex.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

        for(int i = 0; i < 16; i++)
        {
            float f9 = (float)i / 16F;
            float f13 = (texMaxX + (texMinX - texMaxX) * f9) - 0.001953125F;
            float f17 = f4 * f9;
            vertex.pos(f17, 0.0F - f8, 0.0D).tex(f13, texMaxY).normal(-1F, 0.0F, 0.0F).endVertex();
            vertex.pos(f17, 0.0D, 0.0D).tex(f13, texMaxY).normal(-1F, 0.0F, 0.0F).endVertex();
            vertex.pos(f17, 0.0D, 1.0D).tex(f13, texMinY).normal(-1F, 0.0F, 0.0F).endVertex();
            vertex.pos(f17, 0.0F - f8, 1.0D).tex(f13, texMinY).normal(-1F, 0.0F, 0.0F).endVertex();
        }

        tessellator.draw();

        vertex.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

        for(int j = 0; j < 16; j++)
        {
            float f10 = (float)j / 16F;
            float f14 = (texMaxX + (texMinX - texMaxX) * f10) - 0.001953125F;
            float f18 = f4 * f10 + 0.0625F;
            vertex.pos(f18, 0.0F - f8, 1.0D).tex(f14, texMinY).normal(1.0F, 0.0F, 0.0F).endVertex();
            vertex.pos(f18, 0.0D, 1.0D).tex(f14, texMinY).normal(1.0F, 0.0F, 0.0F).endVertex();
            vertex.pos(f18, 0.0D, 0.0D).tex(f14, texMaxY).normal(1.0F, 0.0F, 0.0F).endVertex();
            vertex.pos(f18, 0.0F - f8, 0.0D).tex(f14, texMaxY).normal(1.0F, 0.0F, 0.0F).endVertex();
        }

        tessellator.draw();

        vertex.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

        for(int k = 0; k < 16; k++)
        {
            float f11 = (float)k / 16F;
            float f15 = (texMaxY + (texMinY - texMaxY) * f11) - 0.001953125F;
            float f19 = f4 * f11 + 0.0625F;
            vertex.pos(0.0D, 0.0D, f19).tex(texMaxX, f15).normal(0.0F, 1.0F, 0.0F).endVertex();
            vertex.pos(f4, 0.0D, f19).tex(texMinX, f15).normal(0.0F, 1.0F, 0.0F).endVertex();
            vertex.pos(f4, 0.0F - f8, f19).tex(texMinX, f15).normal(0.0F, 1.0F, 0.0F).endVertex();
            vertex.pos(0.0D, 0.0F - f8, f19).tex(texMaxX, f15).normal(0.0F, 1.0F, 0.0F).endVertex();
        }

        tessellator.draw();

        vertex.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);

        for(int l = 0; l < 16; l++)
        {
            float f12 = (float)l / 16F;
            float f16 = (texMaxY + (texMinY - texMaxY) * f12) - 0.001953125F;
            float f20 = f4 * f12;
            vertex.pos(f4, 0.0D, f20).tex(texMinX, f16).normal(0.0F, -1F, 0.0F).endVertex();
            vertex.pos(0.0D, 0.0D, f20).tex(texMaxX, f16).normal(0.0F, -1F, 0.0F).endVertex();
            vertex.pos(0.0D, 0.0F - f8, f20).tex(texMaxX, f16).normal(0.0F, -1F, 0.0F).endVertex();
            vertex.pos(f4, 0.0F - f8, f20).tex(texMinX, f16).normal(0.0F, -1F, 0.0F).endVertex();
        }

        tessellator.draw();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityLightningKnife entity) 
	{
		return null;
	}

}