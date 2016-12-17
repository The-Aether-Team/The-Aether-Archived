package com.legacy.aether.client.renders.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.AerwhaleModel;
import com.legacy.aether.server.entities.passive.EntityAerwhale;

public class AerwhaleRenderer extends Render<EntityAerwhale>
{

	private static final ResourceLocation AERWHALE_TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/aerwhale/aerwhale.png");

    private ModelBase model = new AerwhaleModel();

	public AerwhaleRenderer(RenderManager renderManager) 
	{
		super(renderManager);
	}

	@Override
    public void doRender(EntityAerwhale aerwhale, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        this.renderManager.renderEngine.bindTexture(AERWHALE_TEXTURE);
        GlStateManager.translate(x, y + 2.0D, z);
        GlStateManager.rotate(90.0F - aerwhale.rotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F - aerwhale.rotationPitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        this.model.render(aerwhale, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityAerwhale aerwhale) 
	{
        return AERWHALE_TEXTURE;
	}

}