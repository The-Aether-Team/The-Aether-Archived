package com.gildedgames.the_aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ZephyrModel extends ModelBase {

	ModelRenderer LeftFace;
	ModelRenderer BodyRightSide2;
	ModelRenderer Mouth;
	ModelRenderer CloudButt;
	ModelRenderer Tail3;
	ModelRenderer RightFace;
	ModelRenderer BodyLeftSide1;
	ModelRenderer BodyLeftSide2;
	ModelRenderer Body;
	ModelRenderer BodyRightSide1;
	ModelRenderer Tail1;
	ModelRenderer Tail2;

	public ZephyrModel() {
		this.textureWidth = 128;
		this.textureHeight = 32;

		this.setTextureOffset("Tail1.tail1", 96, 22);
		this.setTextureOffset("Tail2.tail2", 80, 24);
		this.setTextureOffset("Tail3.tail3", 84, 18);

		this.Tail1 = new ModelRenderer(this, "Tail1");
		this.Tail1.setRotationPoint(0F, 0F, 12.4F);
		this.Tail1.addBox("tail1", -2.5F, -2.5F, -2.5F, 5, 5, 5);

		this.Tail2 = new ModelRenderer(this, "Tail2");
		this.Tail2.setRotationPoint(0, 0, 6);
		this.Tail2.addBox("tail2", -2F, -2F, -1.966667F, 4, 4, 4);

		this.Tail3 = new ModelRenderer(this, "Tail3");
		this.Tail3.setRotationPoint(0, 0, 5);
		this.Tail3.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3);

		this.Tail1.addChild(Tail2);
		this.Tail2.addChild(Tail3);

		this.LeftFace = new ModelRenderer(this, 67, 11);
		this.LeftFace.addBox(3F, -1F, -9F, 4, 6, 2);
		this.LeftFace.setRotationPoint(0F, 8F, 0F);
		this.LeftFace.setTextureSize(128, 32);
		this.LeftFace.mirror = true;
		this.setRotation(LeftFace, 0F, 0F, 0F);

		this.BodyRightSide2 = new ModelRenderer(this, 25, 11);
		this.BodyRightSide2.addBox(-2F, -3.333333F, -2.5F, 2, 6, 6);
		this.BodyRightSide2.setRotationPoint(-5.5F, 9F, 2F);
		this.BodyRightSide2.setTextureSize(128, 32);
		this.BodyRightSide2.mirror = true;
		this.setRotation(BodyRightSide2, 0F, 0F, 0F);
		this.BodyRightSide2.mirror = false;

		this.Mouth = new ModelRenderer(this, 66, 19);
		this.Mouth.addBox(-3F, 1F, -8F, 6, 3, 1);
		this.Mouth.setRotationPoint(0F, 8F, 0F);
		this.Mouth.setTextureSize(128, 32);
		this.Mouth.mirror = true;
		this.setRotation(Mouth, 0F, 0F, 0F);

		this.CloudButt = new ModelRenderer(this, 0, 0);
		this.CloudButt.addBox(-6F, -3F, 0F, 8, 6, 2);
		this.CloudButt.setRotationPoint(2F, 8F, 7F);
		this.CloudButt.setTextureSize(128, 32);
		this.CloudButt.mirror = true;
		this.setRotation(CloudButt, 0F, 0F, 0F);

		this.RightFace = new ModelRenderer(this, 67, 11);
		this.RightFace.addBox(-7F, -1F, -9F, 4, 6, 2);
		this.RightFace.setRotationPoint(0F, 8F, 0F);
		this.RightFace.setTextureSize(128, 32);
		this.RightFace.mirror = true;
		this.setRotation(RightFace, 0F, 0F, 0F);
		this.RightFace.mirror = false;

		this.BodyLeftSide1 = new ModelRenderer(this, 0, 20);
		this.BodyLeftSide1.addBox(0F, -3F, -3F, 2, 6, 6);
		this.BodyLeftSide1.setRotationPoint(6F, 8F, -4F);
		this.BodyLeftSide1.setTextureSize(128, 32);
		this.BodyLeftSide1.mirror = true;
		this.setRotation(BodyLeftSide1, 0F, 0F, 0F);

		this.BodyLeftSide2 = new ModelRenderer(this, 25, 11);
		this.BodyLeftSide2.addBox(0F, -3.333333F, -2.5F, 2, 6, 6);
		this.BodyLeftSide2.setRotationPoint(5.5F, 9F, 2F);
		this.BodyLeftSide2.setTextureSize(128, 32);
		this.BodyLeftSide2.mirror = true;
		this.setRotation(BodyLeftSide2, 0F, 0F, 0F);

		this.Body = new ModelRenderer(this, 27, 9);
		this.Body.addBox(-6F, -4F, -7F, 12, 9, 14);
		this.Body.setRotationPoint(0F, 8F, 0F);
		this.Body.setTextureSize(128, 32);
		this.setRotation(Body, 0F, 0F, 0F);

		this.BodyRightSide1 = new ModelRenderer(this, 0, 20);
		this.BodyRightSide1.addBox(-2F, -3F, -3F, 2, 6, 6);
		this.BodyRightSide1.setRotationPoint(-6F, 8F, -4F);
		this.BodyRightSide1.setTextureSize(128, 32);
		this.BodyRightSide1.mirror = true;
		this.setRotation(BodyRightSide1, 0F, 0F, 0F);
		this.BodyRightSide1.mirror = false;
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

		this.LeftFace.render(scale);
		this.BodyRightSide2.render(scale);
		this.Mouth.render(scale);
		this.CloudButt.render(scale);
		this.RightFace.render(scale);
		this.BodyLeftSide1.render(scale);
		this.BodyLeftSide2.render(scale);
		this.Body.render(scale);
		this.BodyRightSide1.render(scale);
		this.Tail1.render(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		float motion = (float) (Math.sin(limbSwing * 20 / 57.2957795) * limbSwingAmount * .5F);

		this.LeftFace.rotationPointY = motion + 8;
		this.LeftFace.rotationPointX = motion * 0.5F;

		this.BodyLeftSide1.rotationPointY = 8 - motion * 0.5F;
		this.BodyLeftSide2.rotationPointY = 9 + motion * 0.5F;

		this.RightFace.rotationPointY = 8 - motion;
		this.RightFace.rotationPointX = -motion * 0.5F;

		this.BodyRightSide1.rotationPointY = 8 - motion * 0.5F;
		this.BodyRightSide2.rotationPointY = 9 + motion * 0.5F;

		this.Tail1.rotationPointX = (float) (Math.sin(limbSwing * 20 / 57.2957795) * limbSwingAmount * 0.75F);

		this.Tail1.rotateAngleY = (float) (Math.sin(limbSwing * 0.5F / 57.2957795) * limbSwingAmount * 0.75F);
		this.Tail1.rotationPointY = 8 - motion;

		this.Tail2.rotationPointX = (float) (Math.sin(limbSwing * 15 / 57.2957795) * limbSwingAmount * 0.85F);
		this.Tail2.rotationPointY = motion * 1.25F;
		this.Tail2.rotateAngleY = this.Tail1.rotateAngleY + 0.25F;

		this.Tail3.rotationPointX = (float) (Math.sin(limbSwing * 10 / 57.2957795) * limbSwingAmount * 0.95F);
		this.Tail3.rotationPointY = -motion;
		this.Tail3.rotateAngleY = this.Tail2.rotateAngleY + 0.35F;
	}
}