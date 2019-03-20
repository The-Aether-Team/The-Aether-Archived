package com.legacy.aether.client.renders.entities;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.client.models.entities.MoaModel;
import com.legacy.aether.client.renders.entities.layer.LayerMoaSaddle;
import com.legacy.aether.client.renders.entities.layer.MoaDonatorLayer;
import com.legacy.aether.entities.passive.mountable.EntityMoa;
import com.legacy.aether.entities.util.AetherMoaTypes;
import com.legacy.aether.player.PlayerAether;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class MoaRenderer extends RenderLiving<EntityMoa>
{

    private static final ResourceLocation MOS = new ResourceLocation("aether_legacy", "textures/entities/moa/mos.png");

	public MoaRenderer(RenderManager renderManager)
	{
		super(renderManager, new MoaModel(0.0F), 0.7F);

		this.addLayer(new MoaDonatorLayer(renderManager, (MoaModel) this.getMainModel()));
		this.addLayer(new LayerMoaSaddle(this));
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
		
		if (moa.isBeingRidden() && moa.getPassengers().get(0) instanceof EntityPlayer)
		{
			IPlayerAether player = AetherAPI.getInstance().get((EntityPlayer) moa.getPassengers().get(0));

			if (player != null && !((PlayerAether)player).donatorMoaSkin.shouldUseDefualt())
			{
				return null;
			}
		}

		 if (entity.hasCustomName() && "Mos".equals(entity.getCustomNameTag()) && (entity.getMoaType() == AetherMoaTypes.orange))
         {
			 return MOS;
         }
		 else
		 {
			return moa.getMoaType().getTexture(moa.isBeingRidden());
		 }
	}

}