package com.gildedgames.the_aether.client.models.attachments;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelHalo extends ModelBiped {

	public ModelRenderer halo1, halo2, halo3, halo4;

	public ModelHalo() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		this.halo1 = new ModelRenderer(this, 0, 0);
		this.halo1.addBox(-3F, 0F, -2F, 1, 1, 4);
		this.halo1.setTextureSize(64, 32);
		this.halo1.setRotationPoint(0.0F, 0F, 0F);
		this.halo2 = new ModelRenderer(this, 0, 0);
		this.halo2.addBox(2F, 0F, -2F, 1, 1, 4);
		this.halo2.setTextureSize(64, 32);
		this.halo3 = new ModelRenderer(this, 0, 0);
		this.halo3.addBox(-2F, 0F, 2F, 4, 1, 1);
		this.halo3.setTextureSize(64, 32);
		this.halo4 = new ModelRenderer(this, 0, 0);
		this.halo4.addBox(-2F, 0F, -3F, 4, 1, 1);
		this.halo4.setTextureSize(64, 32);
		this.halo4.setRotationPoint(0.0F, 0F, 0.0F);
	}

	public void renderHalo(float scale) {
		this.halo1.rotateAngleY = this.halo2.rotateAngleY = this.halo3.rotateAngleY = this.halo4.rotateAngleY = this.bipedHead.rotateAngleY;
		this.halo1.rotateAngleX = this.halo2.rotateAngleX = this.halo3.rotateAngleX = this.halo4.rotateAngleX = this.bipedHead.rotateAngleX;
		this.halo1.rotationPointX = this.halo2.rotationPointX = this.halo3.rotationPointX = this.halo4.rotationPointX = 0.0F;
		this.halo1.rotationPointY = this.halo2.rotationPointY = this.halo3.rotationPointY = this.halo4.rotationPointY = 0.0F;

		this.halo1.render(scale);
		this.halo2.render(scale);
		this.halo3.render(scale);
		this.halo4.render(scale);
	}

}