package com.legacy.aether.client.renders.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.client.models.entities.MoaModel;
import com.legacy.aether.client.renders.entities.layer.MoaDonatorLayer;
import com.legacy.aether.client.renders.entities.layer.MoaLayer;
import com.legacy.aether.server.entities.passive.mountable.EntityMoa;
import com.legacy.aether.server.player.PlayerAether;

public class MoaRenderer extends RenderLiving<EntityMoa>
{

	public MoaRenderer(RenderManager renderManager)
	{
		super(renderManager, new MoaModel(), 1.0F);
		this.addLayer(new MoaLayer(renderManager, (MoaModel) this.getMainModel()));
		this.addLayer(new MoaDonatorLayer(renderManager, (MoaModel) this.getMainModel()));
	}

	protected float getWingRotation(EntityMoa moa, float f)
	{
		float f1 = moa.prevWingRotation + (moa.wingRotation - moa.prevWingRotation) * f;
		float f2 = moa.prevDestPos + (moa.destPos - moa.prevDestPos) * f;

		return (MathHelper.sin(f1) + 1.0F) * f2;
	}

	@Override
	protected float handleRotationFloat(EntityMoa entityliving, float f)
	{
		return this.getWingRotation(entityliving, f);
	}
    
	protected void scaleMoa(EntityMoa entityMoa)
	{
		float moaScale = entityMoa.isChild() ?  1.0f : 1.8f;

		GL11.glScalef(moaScale, moaScale, moaScale);
	}

	@Override
	protected void preRenderCallback(EntityMoa entityliving, float f)
	{
		this.scaleMoa(entityliving);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMoa entity)
	{
		EntityMoa moa = (EntityMoa)entity;
		
		if (!moa.getPassengers().isEmpty() && moa.getPassengers().get(0) instanceof EntityPlayer)
		{
			PlayerAether player = PlayerAether.get((EntityPlayer) moa.getPassengers().get(0));

			if (player != null && !player.donatorMoaSkin.shouldUseDefualt())
			{
				return null;
			}
		}

		return moa.getColor().getTexture(moa.getSaddled());
	}

}