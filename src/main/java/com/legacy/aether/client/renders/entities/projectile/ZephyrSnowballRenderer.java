package com.legacy.aether.client.renders.entities.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.legacy.aether.common.entities.projectile.EntityZephyrSnowball;

public class ZephyrSnowballRenderer extends Render<EntityZephyrSnowball>
{

	public ZephyrSnowballRenderer(RenderManager renderManager) 
	{
		super(renderManager);
	}

	public void doRenderFireball(EntityZephyrSnowball entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		Minecraft.getMinecraft().getItemRenderer().renderItem(Minecraft.getMinecraft().thePlayer, new ItemStack(Items.SNOWBALL), TransformType.NONE);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

	@Override
    public void doRender(EntityZephyrSnowball entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
		this.doRenderFireball(entity, x, y, z, entityYaw, partialTicks);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityZephyrSnowball entity) 
	{
		return null;
	}

}