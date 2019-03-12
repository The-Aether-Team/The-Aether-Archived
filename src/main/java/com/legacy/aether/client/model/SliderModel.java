package com.legacy.aether.client.model;

import com.legacy.aether.entity.boss.EntitySlider;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class SliderModel extends ModelBase
{

	public ModelRenderer head;

	public SliderModel()
	{
		this(0.0F);
	}

	public SliderModel(float modelSize)
	{
		this(modelSize, 0.0F);
	}

	public SliderModel(float modelSize, float rotationPointY)
	{
		this.head = new ModelRenderer(this, 0, 0);

		this.head.addBox(-8.0F, -16.0F, -8.0F, 16, 16, 16, modelSize);
		this.head.setRotationPoint(0.0F, rotationPointY, 0.0F);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

		this.head.rotateAngleY = this.head.rotateAngleY = 0.0F;

		GlStateManager.pushMatrix();
		GlStateManager.scalef(2.0F, 2.0F, 2.0F);

		if (entityIn instanceof EntitySlider && ((EntitySlider) entityIn).hurtAngle > 0.01F)
		{
			GlStateManager.rotatef(((EntitySlider) entityIn).hurtAngle * -30.0F, ((EntitySlider) entityIn).hurtAngleX, 0.0F, ((EntitySlider) entityIn).hurtAngleZ);
		}

		this.head.render(scale);

		GlStateManager.popMatrix();
	}

}