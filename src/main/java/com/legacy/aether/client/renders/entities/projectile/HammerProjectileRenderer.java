package com.legacy.aether.client.renders.entities.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.legacy.aether.common.entities.projectile.EntityHammerProjectile;
import com.legacy.aether.common.items.ItemsAether;

public class HammerProjectileRenderer extends Render<EntityHammerProjectile>
{

	public HammerProjectileRenderer(RenderManager renderManager) 
	{
		super(renderManager);
		this.shadowSize = 0.0F;
	}

	public void doRenderNotchWave(EntityHammerProjectile notchwave, double par2, double par4, double par6, float par8, float par9)
	{
		float f4 = 0.6F, f5 = 0.4F, f6 = 1.5F;

		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glRotatef(180F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-f4, -f5, 0.0F);
		GL11.glScalef(f6, f6, f6);

		Minecraft.getMinecraft().getItemRenderer().renderItem(Minecraft.getMinecraft().player, new ItemStack(ItemsAether.notch_hammer, 1, 1565), TransformType.NONE);
		GL11.glPopMatrix();
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