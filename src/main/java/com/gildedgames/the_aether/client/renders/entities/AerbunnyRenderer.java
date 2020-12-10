package com.gildedgames.the_aether.client.renders.entities;

import com.gildedgames.the_aether.entities.passive.mountable.EntityAerbunny;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.client.models.entities.AerbunnyModel;

public class AerbunnyRenderer extends RenderLiving<EntityAerbunny>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/aerbunny/aerbunny.png");

    public AerbunnyModel model;

	public AerbunnyRenderer(RenderManager renderManager)
	{
		super(renderManager, new AerbunnyModel(), 0.3F);
		this.model = (AerbunnyModel) this.getMainModel();
	}

    protected void rotateAerbunny(EntityAerbunny entitybunny)
    {
    	if (!entitybunny.isRiding())
    	{
    		GlStateManager.translate(0, 0.2D, 0);
    	}
    	else
        {
            if (entitybunny.getRidingEntity() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entitybunny.getRidingEntity();

                if (player.isElytraFlying())
                {
                    GlStateManager.translate(0.0D, 0.7D, -1.4D);
                }
            }
        }
    	
        if (!entitybunny.onGround)
        {
            if (entitybunny.motionY > 0.5D)
            {
                GL11.glRotatef(15.0F, -1.0F, 0.0F, 0.0F);
            }
            else if (entitybunny.motionY < -0.5D)
            {
                GL11.glRotatef(-15.0F, -1.0F, 0.0F, 0.0F);
            }
            else
            {
                GL11.glRotatef((float)(entitybunny.motionY * 30.0D), -1.0F, 0.0F, 0.0F);
            }
        }

        this.model.puffiness = (float)(entitybunny.getRidingEntity() != null ? entitybunny.getPuffinessClient() : entitybunny.getPuffiness()) / 10.0F;
    }

    protected void preRenderCallback(EntityAerbunny entitybunny, float f)
    {
        this.rotateAerbunny(entitybunny);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityAerbunny entity)
	{
		return TEXTURE;
	}

}
