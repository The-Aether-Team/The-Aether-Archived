package com.gildedgames.the_aether.client.models.entities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import com.gildedgames.the_aether.entities.bosses.sun_spirit.EntitySunSpirit;

public class SunSpiritModel extends ModelBiped {

	public ModelRenderer bipedBody2;
	public ModelRenderer bipedBody3;
	public ModelRenderer bipedBody4;
	public ModelRenderer bipedRightArm2;
	public ModelRenderer bipedLeftArm2;
	public ModelRenderer bipedRightArm3;
	public ModelRenderer bipedLeftArm3;

	public SunSpiritModel() {
		this(0.0F);
	}

	public SunSpiritModel(float modelSize) {
		this(modelSize, 0.0F);
	}

	public SunSpiritModel(float modelSize, float rotationPointY) {
		this.isSneak = false;

		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4F, -8F, -3F, 8, 5, 7, modelSize);
		this.bipedHead.setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4F, -3F, -4F, 8, 3, 8, modelSize);
		this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.bipedBody = new ModelRenderer(this, 0, 12);
		this.bipedBody.addBox(-5F, 0.0F, -2.5F, 10, 6, 5, modelSize);
		this.bipedBody.setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.bipedBody2 = new ModelRenderer(this, 0, 23);
		this.bipedBody2.addBox(-4.5F, 6.0F, -2F, 9, 5, 4, modelSize);
		this.bipedBody2.setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.bipedBody3 = new ModelRenderer(this, 30, 27);
		this.bipedBody3.addBox(-4.5F, 11.0F, -2F, 5, 1, 4, modelSize + 0.5F);
		this.bipedBody3.setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.bipedBody4 = new ModelRenderer(this, 30, 27);
		this.bipedBody4.addBox(-0.5F, 11.0F, -2F, 5, 1, 4, modelSize + 0.5F);
		this.bipedBody4.setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.bipedRightArm = new ModelRenderer(this, 30, 11);
		this.bipedRightArm.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, modelSize + 0.5F);
		this.bipedRightArm.setRotationPoint(-8F, 2.0F + rotationPointY, 0.0F);

		this.bipedRightArm2 = new ModelRenderer(this, 30, 11);
		this.bipedRightArm2.addBox(-2.5F, 2.5F, -2.5F, 5, 10, 5, modelSize);
		this.bipedRightArm2.setRotationPoint(-8F, 2.0F + rotationPointY, 0.0F);

		this.bipedRightArm3 = new ModelRenderer(this, 30, 26);
		this.bipedRightArm3.addBox(-2.5F, 7.5F, -2.5F, 5, 1, 5, modelSize + 0.25F);
		this.bipedRightArm3.setRotationPoint(-8F, 2.0F + rotationPointY, 0.0F);

		this.bipedLeftArm = new ModelRenderer(this, 30, 11);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, modelSize + 0.5F);
		this.bipedLeftArm.setRotationPoint(8F, 2.0F + rotationPointY, 0.0F);

		this.bipedLeftArm2 = new ModelRenderer(this, 30, 11);
		this.bipedLeftArm2.mirror = true;
		this.bipedLeftArm2.addBox(-2.5F, 2.5F, -2.5F, 5, 10, 5, modelSize);
		this.bipedLeftArm2.setRotationPoint(8F, 2.0F + rotationPointY, 0.0F);

		this.bipedLeftArm3 = new ModelRenderer(this, 30, 26);
		this.bipedLeftArm3.mirror = true;
		this.bipedLeftArm3.addBox(-2.5F, 7.5F, -2.5F, 5, 1, 5, modelSize + 0.25F);
		this.bipedLeftArm3.setRotationPoint(8F, 2.0F + rotationPointY, 0.0F);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		if (entityIn instanceof EntitySunSpirit) {
			GL11.glScalef(2.25F, 2.25F, 2.25F);
		} else {
			GL11.glScalef(1.0F, 1.0F, 1.0F);
		}

		GL11.glTranslatef(0F, -0.25F, 0F);

		this.bipedHead.render(scale);
		this.bipedHeadwear.render(scale);
		this.bipedBody.render(scale);
		this.bipedBody2.render(scale);
		this.bipedBody3.render(scale);
		this.bipedBody4.render(scale);
		this.bipedRightArm.offsetX = 0.985F;
		this.bipedRightArm.render(scale);
		this.bipedRightArm2.render(scale);
		this.bipedRightArm3.render(scale);
		this.bipedLeftArm.offsetX = -0.985F;
		this.bipedLeftArm.render(scale);
		this.bipedLeftArm2.render(scale);
		this.bipedLeftArm3.render(scale);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
		this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;

		this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleY = this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleZ = this.bipedLeftArm.rotateAngleY = 0.0F;

		this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F - 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F - 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

		this.bipedBody4.rotateAngleX = this.bipedBody3.rotateAngleX = this.bipedBody2.rotateAngleX = this.bipedBody.rotateAngleX;
		this.bipedBody4.rotateAngleY = this.bipedBody3.rotateAngleY = this.bipedBody2.rotateAngleY = this.bipedBody.rotateAngleY;

		this.bipedLeftArm3.rotateAngleX = this.bipedLeftArm2.rotateAngleX = -this.bipedLeftArm.rotateAngleX;
		this.bipedLeftArm3.rotateAngleY = this.bipedLeftArm2.rotateAngleY = -this.bipedLeftArm.rotateAngleY;
		this.bipedLeftArm3.rotateAngleZ = this.bipedLeftArm2.rotateAngleZ = -this.bipedLeftArm.rotateAngleZ;

		this.bipedRightArm3.rotateAngleX = this.bipedRightArm2.rotateAngleX = -this.bipedRightArm.rotateAngleX;
		this.bipedRightArm3.rotateAngleY = this.bipedRightArm2.rotateAngleY = -this.bipedRightArm.rotateAngleY;
		this.bipedRightArm3.rotateAngleZ = this.bipedRightArm2.rotateAngleZ = -this.bipedRightArm.rotateAngleZ;
	}

}