package com.legacy.aether.client.renders.entities.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.legacy.aether.common.entities.projectile.EntityLightningKnife;
import com.legacy.aether.common.items.ItemsAether;

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
        GL11.glDepthMask(true);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(-yaw, 0F, 0F, 0F);
        GL11.glRotatef(-(arr.prevRotationPitch + (arr.rotationPitch - arr.prevRotationPitch) * time), 0.25F, 0F, 0F);
        GL11.glRotatef(90.0F, 1F, 0F, 0.25F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		Minecraft.getMinecraft().getItemRenderer().renderItem(Minecraft.getMinecraft().thePlayer, new ItemStack(ItemsAether.lightning_knife), TransformType.NONE);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityLightningKnife entity) 
	{
		return null;
	}

}
