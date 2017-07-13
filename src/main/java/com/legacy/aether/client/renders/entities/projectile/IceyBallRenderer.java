package com.legacy.aether.client.renders.entities.projectile;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.models.entities.CrystalModel;
import com.legacy.aether.common.entities.projectile.crystals.EntityIceyBall;

public class IceyBallRenderer extends RenderLiving<EntityIceyBall>
{

	private CrystalModel model;

    public IceyBallRenderer(RenderManager renderManager)
    {
		super(renderManager, new CrystalModel(), 0.25F);
		this.model = (CrystalModel)this.mainModel;
    }

    public void preRenderCallback(EntityIceyBall hs, float f)
    {
		for(int i = 0; i < 3; i ++) 
		{
			model.sinage[i] = hs.sinage[i];
		}
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityIceyBall entity)
	{
    	return new ResourceLocation("aether_legacy", "textures/entities/crystals/iceyball.png");
	}

}