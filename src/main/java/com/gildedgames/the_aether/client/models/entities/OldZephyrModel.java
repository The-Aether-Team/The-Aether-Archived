package com.gildedgames.the_aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class OldZephyrModel extends ModelBase {
	public ModelRenderer zephyr;

	public OldZephyrModel() {
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.zephyr = new ModelRenderer(this, 0, 0);
		this.zephyr.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.zephyr.addBox(-10.0F, 0.0F, -10.0F, 20, 14, 24, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.zephyr.render(f5);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
