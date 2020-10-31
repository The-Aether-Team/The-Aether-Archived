package com.gildedgames.the_aether.client.renders.entities;

import com.gildedgames.the_aether.entities.hostile.EntityCockatrice;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.client.models.entities.CockatriceModel;

public class CockatriceRenderer extends RenderLiving<EntityCockatrice>
{

    private static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/cockatrice/cockatrice.png");

	public CockatriceRenderer(RenderManager rendermanagerIn) 
	{
		super(rendermanagerIn, new CockatriceModel(), 0.5F);
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