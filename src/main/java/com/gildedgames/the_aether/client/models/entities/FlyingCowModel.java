package com.gildedgames.the_aether.client.models.entities;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class FlyingCowModel extends ModelQuadruped {

	ModelRenderer udders;
	ModelRenderer horn1;
	ModelRenderer horn2;

	public FlyingCowModel(float scale) {
		super(12, scale);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
		this.head.setRotationPoint(0.0F, 4.0F, -8.0F);
		this.horn1 = new ModelRenderer(this, 22, 0);
		this.horn1.addBox(-4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
		this.horn1.setRotationPoint(0.0F, 3.0F, -7.0F);
		this.horn2 = new ModelRenderer(this, 22, 0);
		this.horn2.addBox(3.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
		this.horn2.setRotationPoint(0.0F, 3.0F, -7.0F);
		this.udders = new ModelRenderer(this, 52, 0);
		this.udders.addBox(-2.0F, -3.0F, 0.0F, 4, 6, 2, 0.0F);
		this.udders.setRotationPoint(0.0F, 14.0F, 6.0F);
		this.udders.rotateAngleX = ((float) Math.PI / 2F);
		this.body = new ModelRenderer(this, 18, 4);
		this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, scale);
		this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
		--this.leg1.rotationPointX;
		++this.leg2.rotationPointX;
		this.leg1.rotationPointZ += 0.0F;
		this.leg2.rotationPointZ += 0.0F;
		--this.leg3.rotationPointX;
		++this.leg4.rotationPointX;
		--this.leg3.rotationPointZ;
		--this.leg4.rotationPointZ;
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		if (this.isChild) {
			float f6 = 2.0F;
			GL11.glPushMatrix();
			GL11.glPopMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * scale, 0.0F);
			this.horn1.render(scale);
			this.horn2.render(scale);
			this.udders.render(scale);
		} else {
			this.horn1.render(scale);
			this.horn2.render(scale);
			this.udders.render(scale);
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

		this.horn1.rotateAngleY = this.head.rotateAngleY;
		this.horn1.rotateAngleX = this.head.rotateAngleX;
		this.horn2.rotateAngleY = this.head.rotateAngleY;
		this.horn2.rotateAngleX = this.head.rotateAngleX;
	}

}