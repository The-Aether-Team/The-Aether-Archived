package com.legacy.aether.client.renders.entities;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.client.models.entities.AerwhaleModel;
import com.legacy.aether.client.models.entities.OldAerwhaleModel;
import com.legacy.aether.entities.passive.EntityAerwhale;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class AerwhaleRenderer extends Render<EntityAerwhale>
{

	private static final ResourceLocation AERWHALE_TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/aerwhale/aerwhale.png");

	private static final ResourceLocation OLD_AERWHALE_TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/aerwhale/old_aerwhale.png");

    private ModelBase model = new AerwhaleModel();

    private ModelBase old_model = new OldAerwhaleModel();
    
	public AerwhaleRenderer(RenderManager renderManager) 
	{
		super(renderManager);
	}

	@Override
    public void doRender(EntityAerwhale aerwhale, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        
        if (aerwhale.hurtResistantTime > 11)
		{
			GlStateManager.color(1.0F, 0.5F, 0.5F);
		}
		else
		{
			GlStateManager.color(1.0F, 1.0F, 1.0F);
		}
        
        
        if (AetherConfig.oldMobsEnabled())
        {
        	this.renderManager.renderEngine.bindTexture(OLD_AERWHALE_TEXTURE);
        	 //GlStateManager.translate(x - 0.2D, 0 , 0);
        }
        else
        {
        	this.renderManager.renderEngine.bindTexture(AERWHALE_TEXTURE);
        }
        
        GlStateManager.translate(x, y + 2.0D, z);
        GlStateManager.rotate(90.0F - (float) aerwhale.aerwhaleRotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F - (float) aerwhale.aerwhaleRotationPitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        if (AetherConfig.oldMobsEnabled())
        {
        	this.old_model.render(aerwhale, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            //this.renderManager.renderEngine.bindTexture(OLD_AERWHALE_TEXTURE);
        }
        else
        {
        	this.model.render(aerwhale, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
            //this.renderManager.renderEngine.bindTexture(AERWHALE_TEXTURE);
        }
        
        GlStateManager.popMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityAerwhale aerwhale) 
	{
        return AERWHALE_TEXTURE;
	}

}