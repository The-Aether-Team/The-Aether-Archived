package com.legacy.aether.client.renders.entities.projectile;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.entities.projectile.EntityHammerProjectile;

public class HammerProjectileRenderer extends Render<EntityHammerProjectile>
{

	public HammerProjectileRenderer(RenderManager renderManager) 
	{
		super(renderManager);

		this.shadowSize = 0.0F;
	}

	public void doRenderNotchWave(EntityHammerProjectile notchwave, double par2, double par4, double par6, float par8, float par9)
	{
		GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.translate(par2, par4, par6);

        this.bindTexture(this.getEntityTexture(notchwave));

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertex = tessellator.getBuffer();

        GlStateManager.rotate(180F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

        vertex.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        vertex.pos(-0.5F, -0.25F, 0.0F).tex(0.0F, 0.0F).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertex.pos(0.5F, -0.25F, 0.0F).tex(0.0F, 1.0F).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertex.pos(0.5F, 0.75F, 0.0F).tex(1.0F, 1.0F).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertex.pos(-0.5F, 0.75F, 0.0F).tex(1.0F, 0.0F).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
	}

	@Override
	public void doRender(EntityHammerProjectile par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRenderNotchWave(par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHammerProjectile entity)
	{
		return new ResourceLocation("aether_legacy", "textures/entities/projectile/notch_wave.png");
	}

}