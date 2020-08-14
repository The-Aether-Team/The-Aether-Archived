package com.gildedgames.the_aether.client.renders.entities.projectile;

import com.gildedgames.the_aether.entities.projectile.crystals.EntityThunderBall;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.the_aether.client.models.entities.CrystalModel;

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
		
		GlStateManager.translate(0, 0.3D, 0);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityThunderBall entity) 
	{
    	return new ResourceLocation("aether_legacy", "textures/entities/crystals/electroball.png");
	}

}
