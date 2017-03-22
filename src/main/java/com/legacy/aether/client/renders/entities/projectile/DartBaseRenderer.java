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

import com.legacy.aether.common.entities.projectile.darts.EntityDartBase;
import com.legacy.aether.common.entities.projectile.darts.EntityDartEnchanted;
import com.legacy.aether.common.entities.projectile.darts.EntityDartGolden;

public class DartBaseRenderer<DART extends EntityDartBase> extends Render<DART>
{

    public DartBaseRenderer(RenderManager renderManager) 
    {
		super(renderManager);
		this.shadowSize = 0.0F;
	}

	public void renderDart(DART dart, double d, double d1, double d2, float f, float f1)
    {
        this.bindEntityTexture(dart);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(dart.prevRotationYaw + (dart.rotationYaw - dart.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(dart.prevRotationPitch + (dart.rotationPitch - dart.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer renderWorld = tessellator.getBuffer();
        byte i = 1;
        float f2 = 0.0F;
        float f3 = 0.5F;
        float f4 = (float)(0 + i * 10) / 32.0F;
        float f5 = (float)(5 + i * 10) / 32.0F;
        float f6 = 0.0F;
        float f7 = 0.15625F;
        float f8 = (float)(5 + i * 10) / 32.0F;
        float f9 = (float)(10 + i * 10) / 32.0F;
        float f10 = 0.05625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f11 = (float)dart.arrowShake - f1;

        if (f11 > 0.0F)
        {
            float j = -MathHelper.sin(f11 * 3.0F) * f11;
            GL11.glRotatef(j, 0.0F, 0.0F, 1.0F);
        }

        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(f10, f10, f10);
        GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
        GL11.glNormal3f(f10, 0.0F, 0.0F);
        renderWorld.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderWorld.pos(-7.0D, -2.0D, -2.0D).tex((double)f6, (double)f8).endVertex();
        renderWorld.pos(-7.0D, -2.0D, 2.0D).tex((double)f7, (double)f8).endVertex();
        renderWorld.pos(-7.0D, 2.0D, 2.0D).tex((double)f7, (double)f9).endVertex();
        renderWorld.pos(-7.0D, 2.0D, -2.0D).tex((double)f6, (double)f9).endVertex();
        tessellator.draw();
        GL11.glNormal3f(-f10, 0.0F, 0.0F);
        renderWorld.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderWorld.pos(-7.0D, 2.0D, -2.0D).tex((double)f6, (double)f8).endVertex();
        renderWorld.pos(-7.0D, 2.0D, 2.0D).tex((double)f7, (double)f8).endVertex();
        renderWorld.pos(-7.0D, -2.0D, 2.0D).tex((double)f7, (double)f9).endVertex();
        renderWorld.pos(-7.0D, -2.0D, -2.0D).tex((double)f6, (double)f9).endVertex();
        tessellator.draw();

        for (int var23 = 0; var23 < 5; ++var23)
        {
            GL11.glRotatef(72.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            renderWorld.begin(7, DefaultVertexFormats.POSITION_TEX);
            renderWorld.pos(-8.0D, -2.0D, 0.0D).tex((double)f2, (double)f4).endVertex();
            renderWorld.pos(8.0D, -2.0D, 0.0D).tex((double)f3, (double)f4).endVertex();
            renderWorld.pos(8.0D, 2.0D, 0.0D).tex((double)f3, (double)f5).endVertex();
            renderWorld.pos(-8.0D, 2.0D, 0.0D).tex((double)f2, (double)f5).endVertex();
            tessellator.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    public void doRender(DART entity, double d, double d1, double d2, float f, float f1)
    {
        this.renderDart(entity, d, d1, d2, f, f1);
        super.doRender(entity, d, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(DART entity)
    {
    	String base = entity instanceof EntityDartGolden ? "golden" : entity instanceof EntityDartEnchanted ? "enchanted" : "poison";
        return new ResourceLocation("aether_legacy", "textures/entities/projectile/dart/" + base + "_dart.png");
    }

}