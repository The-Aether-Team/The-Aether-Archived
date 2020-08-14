package com.gildedgames.the_aether.client.models.attachments;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAetherWings extends ModelBiped {

	public ModelRenderer wingLeft;

	public ModelRenderer wingRight;

	public float sinage;

	public boolean gonRound;

	public ModelAetherWings() {
		this(0.0F);
	}

	public ModelAetherWings(float f) {
		this(f, 0.0F);
	}

	public ModelAetherWings(float f, float f1) {
		this.isSneak = false;

		this.wingLeft = new ModelRenderer(this, 24, 31);
		this.wingLeft.addBox(0F, -7F, 1F, 20, 8, 0, f);
		this.wingLeft.setRotationPoint(0.5F, 5F + f1, 2.625F);

		this.wingRight = new ModelRenderer(this, 24, 31);
		this.wingRight.mirror = true;
		this.wingRight.addBox(-19F, -7F, 1F, 20, 8, 0, f);
		this.wingRight.setRotationPoint(-0.5F, 5F + f1, 2.625F);
	}

	@Override
	public void render(Entity e, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, e);

		this.wingLeft.renderWithRotation(f5);
		this.wingRight.renderWithRotation(f5);
	}

	public void setWingSinage(float sinage) {
		this.sinage = sinage;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		this.gonRound = entity.onGround;

		this.wingLeft.rotateAngleY = -0.4F;
		this.wingRight.rotateAngleY = 0.4F;
		this.wingLeft.rotateAngleZ = -0.125F;
		this.wingRight.rotateAngleZ = 0.125F;

		if (entity.isSneaking()) {
			this.wingLeft.rotateAngleX = 0.45F;
			this.wingRight.rotateAngleX = 0.45F;
			this.wingLeft.offsetY = -0.17F;
			this.wingRight.offsetY = -0.17F;
			this.wingLeft.offsetZ = 0.112F;
			this.wingRight.offsetZ = 0.112F;
		} else {
			this.wingLeft.rotateAngleX = this.wingLeft.offsetZ = this.wingLeft.offsetY =
					this.wingRight.rotateAngleX = this.wingRight.offsetZ = this.wingRight.offsetY = 0.0F;
		}

		this.wingLeft.rotateAngleY += Math.sin(this.sinage) / 6F;
		this.wingRight.rotateAngleY -= Math.sin(this.sinage) / 6F;
		this.wingLeft.rotateAngleZ += Math.cos(this.sinage) / (this.gonRound ? 8F : 3F);
		this.wingRight.rotateAngleZ -= Math.cos(this.sinage) / (this.gonRound ? 8F : 3F);
	}

}