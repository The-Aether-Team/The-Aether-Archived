package com.gildedgames.the_aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class CrystalModel extends ModelBase {

	public ModelRenderer head[];

	public float sinage[];

	private static final float sponge = (180F / 3.141593F);

	public CrystalModel() {
		this(0.0F);
	}

	public CrystalModel(float f) {
		this(f, 0.0F);
	}

	public CrystalModel(float f, float f1) {
		this.sinage = new float[3];
		this.head = new ModelRenderer[3];
		this.head[0] = new ModelRenderer(this, 0, 0);
		this.head[1] = new ModelRenderer(this, 32, 0);
		this.head[2] = new ModelRenderer(this, 0, 16);

		for (int i = 0; i < 3; i++) {
			this.head[i].addBox(-4F, -4F, -4F, 8, 8, 8, f);
			this.head[i].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		}
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		GL11.glTranslatef(0F, 0.75F, 0F);

		GL11.glEnable(2977 /*GL_NORMALIZE*/);
		GL11.glEnable(3042 /*GL_BLEND*/);
		GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
		GL11.glBlendFunc(770, 771);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glPushMatrix();
		GL11.glRotatef(this.sinage[0] * sponge, 1F, 0F, 0F);
		this.head[0].render(scale);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotatef(this.sinage[1] * sponge, 0F, 1F, 0F);
		this.head[1].render(scale);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotatef(this.sinage[2] * sponge, 0F, 0F, 1F);
		this.head[2].render(scale);
		GL11.glPopMatrix();

		GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		for (int i = 0; i < 3; i++) {
			this.head[i].rotateAngleY = netHeadYaw / 57.29578F;
			this.head[i].rotateAngleX = headPitch / 57.29578F;
		}
	}

}