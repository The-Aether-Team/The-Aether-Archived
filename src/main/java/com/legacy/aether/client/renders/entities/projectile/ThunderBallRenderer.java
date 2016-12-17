package com.legacy.aether.client.renders.entities.projectile;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.CrystalModel;
import com.legacy.aether.server.entities.projectile.crystals.EntityThunderBall;

public class ThunderBallRenderer extends RenderLiving<EntityThunderBall> 
{

	private CrystalModel model;

	public ThunderBallRenderer(RenderManager rendermanagerIn)
	{
		super(rendermanagerIn, new CrystalModel(), 0.25F);
		this.model = (CrystalModel)this.mainModel;
	}

    public void preRenderCallback(EntityThunderBall hs, float f)
    {
		for(int i = 0; i < 3; i ++) 
		{
			model.sinage[i] = hs.sinage[i];
		}
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityThunderBall entity) 
	{
    	return new ResourceLocation("aether_legacy", "textures/entities/crystals/electroball.png");
	}

}
