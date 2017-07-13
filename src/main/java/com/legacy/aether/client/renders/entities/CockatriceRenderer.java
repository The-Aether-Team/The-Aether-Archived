package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.client.models.entities.CockatriceModel;
import com.legacy.aether.common.entities.hostile.EntityCockatrice;

public class CockatriceRenderer extends RenderLiving<EntityCockatrice>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/cockatrice/cockatrice.png");

	public CockatriceRenderer(RenderManager rendermanagerIn) 
	{
		super(rendermanagerIn, new CockatriceModel(), 1.0F);
	}

    protected float getWingRotation(EntityCockatrice cockatrice, float f)
    {
        float f1 = cockatrice.prevWingRotation + (cockatrice.wingRotation - cockatrice.prevWingRotation) * f;
        float f2 = cockatrice.prevDestPos + (cockatrice.destPos - cockatrice.prevDestPos) * f;

        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    protected float handleRotationFloat(EntityCockatrice cockatrice, float f)
    {
        return this.getWingRotation(cockatrice, f);
    }

    protected void preRenderCallback(EntityCockatrice cockatrice, float f)
    {
        GL11.glScalef(1.8F, 1.8F, 1.8F);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityCockatrice cockatrice)
	{
		return TEXTURE;
	}

}