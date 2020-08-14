package com.gildedgames.the_aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class SliderModel extends ModelBase {

	public ModelRenderer head;

	public float hurtAngle, hurtAngleX, hurtAngleZ;

	public SliderModel() {
		this(0.0F);
	}

	public SliderModel(float modelSize) {
		this(modelSize, 0.0F);
	}

	public SliderModel(float modelSize, float rotationPointY) {
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-8F, -16F, -8F, 16, 16, 16, modelSize);
		this.head.setRotationPoint(0.0F, rotationPointY, 0.0F);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.head.rotateAngleY = this.head.rotateAngleY = 0.0F;

		GL11.glPushMatrix();

		GL11.glScalef(2.0F, 2.0F, 2.0F);

		if (this.hurtAngle > 0.01F) {
			GL11.glRotatef(this.hurtAngle * -30F, this.hurtAngleX, 0F, this.hurtAngleZ);
		}

		this.head.render(scale);

		GL11.glPopMatrix();
	}

}