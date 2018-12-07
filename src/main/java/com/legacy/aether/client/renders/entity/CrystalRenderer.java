package com.legacy.aether.client.renders.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.Aether;
import com.legacy.aether.client.models.entities.CrystalModel;
import com.legacy.aether.entities.projectile.crystals.EntityCrystal;

public class CrystalRenderer extends RenderLiving
{

	public CrystalRenderer()
	{
		super(new CrystalModel(), 0.25F);
	}

	@Override
    public void preRenderCallback(EntityLivingBase entity, float f)
    {
		for(int i = 0; i < 3; i ++) 
		{
			((CrystalModel)this.mainModel).sinage[i] = ((EntityCrystal)entity).sinage[i];
		}

		GL11.glTranslatef(0.0F, 0.3F, 0.0F);
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) 
	{
		return Aether.locate("textures/entities/crystals/" + ((EntityCrystal)entity).getCrystalType().name().toLowerCase() + ".png");
	}

}
