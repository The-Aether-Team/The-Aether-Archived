package com.legacy.aether.client.renders.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.Aether;
import com.legacy.aether.client.models.entities.ValkyrieModel;
import com.legacy.aether.entities.bosses.EntityValkyrie;

public class ValkyrieRenderer extends RenderLiving
{

	private static final ResourceLocation TEXTURE = Aether.locate("textures/entities/valkyrie/valkyrie.png");

	public ValkyrieRenderer()
	{
		super(new ValkyrieModel(), 0.3F);
	}

	@Override
    protected void preRenderCallback(EntityLivingBase valkyrie, float partialTickTime)
    {
        ((ValkyrieModel)this.mainModel).sinage = ((EntityValkyrie)valkyrie).sinage;
        ((ValkyrieModel)this.mainModel).gonRound = valkyrie.onGround;
        ((ValkyrieModel)this.mainModel).halow = true;
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) 
	{
		return TEXTURE;
	}

}
