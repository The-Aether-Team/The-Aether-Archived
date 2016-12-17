package com.legacy.aether.client.renders.entities.projectile;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.legacy.aether.server.entities.projectile.EntityPhoenixArrow;

public class PhoenixArrowRenderer extends Render<EntityPhoenixArrow>
{

	public PhoenixArrowRenderer(RenderManager renderManager) 
	{
		super(renderManager);

	}

    public void renderArrow(EntityPhoenixArrow var1, double var2, double var4, double var6, float var8, float var9)
    {
        if (var1.prevRotationYaw != 0.0F || var1.prevRotationPitch != 0.0F)
        {
            GL11.glPushMatrix();
            this.renderManager.renderEngine.bindTexture(this.getEntityTexture(var1));
            GL11.glTranslatef((float)var2, (float)var4, (float)var6);
            GL11.glRotatef(var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9 - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9, 0.0F, 0.0F, 1.0F);
            Tessellator var10 = Tessellator.getInstance();
            VertexBuffer renderer = var10.getBuffer();
            byte var11 = 0;
            float var12 = 0.0F;
            float var13 = 0.5F;
            float var14 = (float)(0 + var11 * 10) / 32.0F;
            float var15 = (float)(5 + var11 * 10) / 32.0F;
            float var16 = 0.0F;
            float var17 = 0.15625F;
            float var18 = (float)(5 + var11 * 10) / 32.0F;
            float var19 = (float)(10 + var11 * 10) / 32.0F;
            float var20 = 0.05625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            float var21 = (float)var1.arrowShake - var9;

            if (var21 > 0.0F)
            {
                float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
                GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
            }

            GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(var20, var20, var20);
            GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
            GL11.glNormal3f(var20, 0.0F, 0.0F);
            renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            renderer.pos(-7.0D, -2.0D, -2.0D).tex((double)var16, (double)var18).endVertex();
            renderer.pos(-7.0D, -2.0D, 2.0D).tex((double)var17, (double)var18).endVertex();
            renderer.pos(-7.0D, 2.0D, 2.0D).tex((double)var17, (double)var19).endVertex();
            renderer.pos(-7.0D, 2.0D, -2.0D).tex((double)var16, (double)var19).endVertex();
            var10.draw();
            GL11.glNormal3f(-var20, 0.0F, 0.0F);
            renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            renderer.pos(-7.0D, 2.0D, -2.0D).tex((double)var16, (double)var18).endVertex();
            renderer.pos(-7.0D, 2.0D, 2.0D).tex((double)var17, (double)var18).endVertex();
            renderer.pos(-7.0D, -2.0D, 2.0D).tex((double)var17, (double)var19).endVertex();
            renderer.pos(-7.0D, -2.0D, -2.0D).tex((double)var16, (double)var19).endVertex();
            var10.draw();

            for (int var23 = 0; var23 < 4; ++var23)
            {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glNormal3f(0.0F, 0.0F, var20);
                renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                renderer.pos(-8.0D, -2.0D, 0.0D).tex((double)var12, (double)var14).endVertex();
                renderer.pos(8.0D, -2.0D, 0.0D).tex((double)var13, (double)var14).endVertex();
                renderer.pos(8.0D, 2.0D, 0.0D).tex((double)var13, (double)var15).endVertex();
                renderer.pos(-8.0D, 2.0D, 0.0D).tex((double)var12, (double)var15).endVertex();
                var10.draw();
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }

    public void doRender(EntityPhoenixArrow entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	this.renderArrow(entity, x, y, z, entityYaw, partialTicks);
        this.renderName(entity, x, y, z);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityPhoenixArrow entity) 
	{
		return new ResourceLocation("aether_legacy", "textures/entities/projectile/flaming_arrow.png");
	}

}