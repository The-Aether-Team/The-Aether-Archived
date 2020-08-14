package com.gildedgames.the_aether.client.models.entities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MiniCloudModel extends ModelBase {

	public ModelRenderer head[];

	public MiniCloudModel() {
		this(0.0F);
	}

	public MiniCloudModel(float modelSize) {
		this(modelSize, 0.0F);
	}

	public MiniCloudModel(float modelSize, float rotationPointY) {
		this.head = new ModelRenderer[5];

		this.head[0] = new ModelRenderer(this, 0, 0);
		this.head[1] = new ModelRenderer(this, 36, 0);
		this.head[2] = new ModelRenderer(this, 36, 0);
		this.head[3] = new ModelRenderer(this, 36, 8);
		this.head[4] = new ModelRenderer(this, 36, 8);

		this.head[0].addBox(-4.5F, -4.5F, -4.5F, 9, 9, 9, modelSize);
		this.head[0].setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.head[1].addBox(-3.5F, -3.5F, -5.5F, 7, 7, 1, modelSize);
		this.head[1].setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.head[2].addBox(-3.5F, -3.5F, 4.5F, 7, 7, 1, modelSize);
		this.head[2].setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.head[3].addBox(-5.5F, -3.5F, -3.5F, 1, 7, 7, modelSize);
		this.head[3].setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);

		this.head[4].addBox(4.5F, -3.5F, -3.5F, 1, 7, 7, modelSize);
		this.head[4].setRotationPoint(0.0F, 0.0F + rotationPointY, 0.0F);
	}

	@Override
	public void render(Entity e, float f, float f1, float f2, float f3, float f4, float f5) {
		GL11.glTranslatef(0.0F, 1.3F, 0.0F);

		this.setRotationAngles(f, f1, f2, f3, f4, f5, e);

		for (int i = 0; i < 5; i++) {
			this.head[i].render(f5);
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		for (int i = 0; i < 5; i++) {
			this.head[i].rotateAngleY = 0F;
			this.head[i].rotateAngleX = 0F;
		}
	}

}