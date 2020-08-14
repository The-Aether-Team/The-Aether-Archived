package com.gildedgames.the_aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.entities.passive.mountable.EntityFlyingCow;

public class FlyingCowWingModel extends ModelBase {

	private ModelRenderer leftWingInner = new ModelRenderer(this, 0, 0);
	private ModelRenderer leftWingOuter = new ModelRenderer(this, 20, 0);
	private ModelRenderer rightWingInner = new ModelRenderer(this, 0, 0);
	private ModelRenderer rightWingOuter = new ModelRenderer(this, 40, 0);

	public FlyingCowWingModel() {
		this.leftWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
		this.leftWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
		this.rightWingInner.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);
		this.rightWingOuter.addBox(-1.0F, -8.0F, -4.0F, 2, 16, 8, 0.0F);

		this.rightWingOuter.rotateAngleY = (float) Math.PI;
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		GL11.glPushMatrix();

		EntityFlyingCow flyingcow = ((EntityFlyingCow) entityIn);
		GL11.glTranslatef(0.0F, -10.0F * scale, 0.0F);

		float wingBend = -((float) Math.acos((double) flyingcow.wingFold));

		float x = 32.0F * flyingcow.wingFold / 4.0F;
		float y = -32.0F * (float) Math.sqrt((double) (1.0F - flyingcow.wingFold * flyingcow.wingFold)) / 4.0F;

		float x2 = x * (float) Math.cos((double) flyingcow.wingAngle) - y * (float) Math.sin((double) flyingcow.wingAngle);
		float y2 = x * (float) Math.sin((double) flyingcow.wingAngle) + y * (float) Math.cos((double) flyingcow.wingAngle);

		this.leftWingInner.setRotationPoint(4.0F + x2, y2 + 12.0F, 0.0F);
		this.rightWingInner.setRotationPoint(-4.0F - x2, y2 + 12.0F, 0.0F);

		x *= 3.0F;
		x2 = x * (float) Math.cos((double) flyingcow.wingAngle) - y * (float) Math.sin((double) flyingcow.wingAngle);
		y2 = x * (float) Math.sin((double) flyingcow.wingAngle) + y * (float) Math.cos((double) flyingcow.wingAngle);

		this.leftWingOuter.setRotationPoint(4.0F + x2, y2 + 12.0F, 0.0F);
		this.rightWingOuter.setRotationPoint(-4.0F - x2, y2 + 12.0F, 0.0F);

		this.leftWingInner.rotateAngleZ = flyingcow.wingAngle + wingBend + ((float) Math.PI / 2F);
		this.leftWingOuter.rotateAngleZ = flyingcow.wingAngle - wingBend + ((float) Math.PI / 2F);
		this.rightWingInner.rotateAngleZ = -(flyingcow.wingAngle + wingBend - ((float) Math.PI / 2F));
		this.rightWingOuter.rotateAngleZ = -(flyingcow.wingAngle - wingBend + ((float) Math.PI / 2F));

		this.leftWingOuter.render(scale);
		this.leftWingInner.render(scale);
		this.rightWingOuter.render(scale);
		this.rightWingInner.render(scale);

		GL11.glPopMatrix();
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
	}

}