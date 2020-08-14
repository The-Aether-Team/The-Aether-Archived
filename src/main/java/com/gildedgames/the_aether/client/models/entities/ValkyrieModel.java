package com.gildedgames.the_aether.client.models.entities;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class ValkyrieModel extends ModelBiped {

	public ModelRenderer bipedBody2;
	public ModelRenderer bipedRightArm2;
	public ModelRenderer bipedLeftArm2;
	public ModelRenderer wingLeft;
	public ModelRenderer wingRight;
	public ModelRenderer skirt[];
	public ModelRenderer sword[];
	public ModelRenderer strand[];
	public ModelRenderer halo[];

	public static final int swordParts = 5;
	public static final int skirtParts = 6;
	public static final int strandParts = 22;
	public static final int haloParts = 4;

	public float sinage;
	public boolean gonRound, halow;

	public ValkyrieModel() {
		this(0.0F);
	}

	public ValkyrieModel(float f) {
		this(f, 0.0F);
	}

	public ValkyrieModel(float f, float f1) {
		this.isSneak = false;

		this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4F, -8F, -4F, 8, 8, 8, f);
		this.bipedHead.setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.bipedBody = new ModelRenderer(this, 12, 16);
		this.bipedBody.addBox(-3F, 0.0F, -1.5F, 6, 12, 3, f);
		this.bipedBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.bipedBody2 = new ModelRenderer(this, 12, 16);
		this.bipedBody2.addBox(-3F, 0.5F, -1.25F, 6, 5, 3, f + 0.75F);
		this.bipedBody2.setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.bipedRightArm = new ModelRenderer(this, 30, 16);
		this.bipedRightArm.addBox(-3F, -1.5F, -1.5F, 3, 12, 3, f);
		this.bipedRightArm.setRotationPoint(-4F, 1.5F + f1, 0.0F);

		this.bipedLeftArm = new ModelRenderer(this, 30, 16);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1F, -1.5F, -1.5F, 3, 12, 3, f);
		this.bipedLeftArm.setRotationPoint(5F, 1.5F + f1, 0.0F);

		this.bipedRightArm2 = new ModelRenderer(this, 30, 16);
		this.bipedRightArm2.addBox(-3F, -1.5F, -1.5F, 3, 3, 3, f + 0.75F);
		this.bipedRightArm2.setRotationPoint(-4F, 1.5F + f1, 0.0F);

		this.bipedLeftArm2 = new ModelRenderer(this, 30, 16);
		this.bipedLeftArm2.mirror = true;
		this.bipedLeftArm2.addBox(-1F, -1.5F, -1.5F, 3, 3, 3, f + 0.75F);
		this.bipedLeftArm2.setRotationPoint(5F, 1.5F + f1, 0.0F);

		this.bipedRightLeg = new ModelRenderer(this, 0, 16);
		this.bipedRightLeg.addBox(-2F, 0.0F, -1.5F, 3, 12, 3, f);
		this.bipedRightLeg.setRotationPoint(-1F, 12F + f1, 0.0F);

		this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-2F, 0.0F, -1.5F, 3, 12, 3, f);
		this.bipedLeftLeg.setRotationPoint(2.0F, 12F + f1, 0.0F);

		this.sword = new ModelRenderer[swordParts];
		this.sword[0] = new ModelRenderer(this, 9, 16);
		this.sword[0].addBox(-2.5F, 8F, 1.5F, 2, 2, 1, f);
		this.sword[0].setRotationPoint(-4F, 1.5F + f1, 0.0F);

		this.sword[1] = new ModelRenderer(this, 32, 10);
		this.sword[1].addBox(-3F, 6.5F, -2.75F, 3, 5, 1, f + 0.5F);
		this.sword[1].setRotationPoint(-4F, 1.5F + f1, 0.0F);

		this.sword[2] = new ModelRenderer(this, 42, 18);
		this.sword[2].addBox(-2F, 7.5F, -12.5F, 1, 3, 10, f);
		this.sword[2].setRotationPoint(-4F, 1.5F + f1, 0.0F);

		this.sword[3] = new ModelRenderer(this, 42, 18);
		this.sword[3].addBox(-2F, 7.5F, -22.5F, 1, 3, 10, f);
		this.sword[3].setRotationPoint(-4F, 1.5F + f1, 0.0F);

		this.sword[4] = new ModelRenderer(this, 28, 17);
		this.sword[4].addBox(-2F, 8.5F, -23.5F, 1, 1, 1, f);
		this.sword[4].setRotationPoint(-4F, 1.5F + f1, 0.0F);

		this.wingLeft = new ModelRenderer(this, 24, 31);
		this.wingLeft.addBox(0F, -4.5F, 0F, 19, 8, 1, f);
		this.wingLeft.setRotationPoint(0.5F, 4.5F + f1, 2.625F);

		this.wingRight = new ModelRenderer(this, 24, 31);
		this.wingRight.mirror = true;
		this.wingRight.addBox(-19F, -4.5F, 0F, 19, 8, 1, f);
		this.wingRight.setRotationPoint(-0.5F, 4.5F + f1, 2.625F);

		this.skirt = new ModelRenderer[skirtParts];
		this.skirt[0] = new ModelRenderer(this, 0, 0);
		this.skirt[0].addBox(0F, 0F, -1F, 3, 6, 1, f);
		this.skirt[0].setRotationPoint(-3F, 9F + f1, -1.5F);

		this.skirt[1] = new ModelRenderer(this, 0, 0);
		this.skirt[1].addBox(0F, 0F, -1F, 3, 6, 1, f);
		this.skirt[1].setRotationPoint(0F, 9F + f1, -1.5F);

		this.skirt[2] = new ModelRenderer(this, 0, 0);
		this.skirt[2].addBox(0F, 0F, 0F, 3, 6, 1, f);
		this.skirt[2].setRotationPoint(-3F, 9F + f1, 1.5F);

		this.skirt[3] = new ModelRenderer(this, 0, 0);
		this.skirt[3].addBox(0F, 0F, 0F, 3, 6, 1, f);
		this.skirt[3].setRotationPoint(0F, 9F + f1, 1.5F);

		this.skirt[4] = new ModelRenderer(this, 55, 19);
		this.skirt[4].addBox(-1F, 0F, 0F, 1, 6, 3, f);
		this.skirt[4].setRotationPoint(-3F, 9F + f1, -1.5F);

		this.skirt[5] = new ModelRenderer(this, 55, 19);
		this.skirt[5].addBox(0F, 0F, 0F, 1, 6, 3, f);
		this.skirt[5].setRotationPoint(3F, 9F + f1, -1.5F);

		this.strand = new ModelRenderer[strandParts];

		for (int i = 0; i < strandParts; i++) {
			this.strand[i] = new ModelRenderer(this, 42 + (i % 7), 17);
		}

		this.strand[0].addBox(-5F, -7F, -4F, 1, 3, 1, f);
		this.strand[0].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[1].addBox(4F, -7F, -4F, 1, 3, 1, f);
		this.strand[1].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[2].addBox(-5F, -7F, -3F, 1, 4, 1, f);
		this.strand[2].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[3].addBox(4F, -7F, -3F, 1, 4, 1, f);
		this.strand[3].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[4].addBox(-5F, -7F, -2F, 1, 4, 1, f);
		this.strand[4].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[5].addBox(4F, -7F, -2F, 1, 4, 1, f);
		this.strand[5].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[6].addBox(-5F, -7F, -1F, 1, 5, 1, f);
		this.strand[6].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[7].addBox(4F, -7F, -1F, 1, 5, 1, f);
		this.strand[7].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[8].addBox(-5F, -7F, 0.0F, 1, 5, 1, f);
		this.strand[8].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[9].addBox(4F, -7F, 0.0F, 1, 5, 1, f);
		this.strand[9].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[10].addBox(-5F, -7F, 1.0F, 1, 6, 1, f);
		this.strand[10].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[11].addBox(4F, -7F, 1.0F, 1, 6, 1, f);
		this.strand[11].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[12].addBox(-5F, -7F, 2.0F, 1, 7, 1, f);
		this.strand[12].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[13].addBox(4F, -7F, 2.0F, 1, 7, 1, f);
		this.strand[13].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[14].addBox(-5F, -7F, 3F, 1, 8, 1, f);
		this.strand[14].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[15].addBox(4F, -7F, 3F, 1, 8, 1, f);
		this.strand[15].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[16].addBox(-4F, -7F, 4F, 1, 9, 1, f);
		this.strand[16].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[17].addBox(3F, -7F, 4F, 1, 9, 1, f);
		this.strand[17].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[18] = new ModelRenderer(this, 42, 17);
		this.strand[18].addBox(-3F, -7F, 4F, 3, 10, 1, f);
		this.strand[18].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[19] = new ModelRenderer(this, 43, 17);
		this.strand[19].addBox(0.0F, -7F, 4F, 3, 10, 1, f);
		this.strand[19].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[20].addBox(-1F, -7F, -5F, 1, 2, 1, f);
		this.strand[20].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.strand[21].addBox(0.0F, -7F, -5F, 1, 3, 1, f);
		this.strand[21].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.halo = new ModelRenderer[haloParts];
		this.halo[0] = new ModelRenderer(this, 43, 9);
		this.halo[0].addBox(-2.5F, -11F, -3.5F, 5, 1, 1, f);
		this.halo[0].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.halo[1] = new ModelRenderer(this, 43, 9);
		this.halo[1].addBox(-2.5F, -11F, 2.5F, 5, 1, 1, f);
		this.halo[1].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.halo[2] = new ModelRenderer(this, 42, 11);
		this.halo[2].addBox(-3.5F, -11F, -2.5F, 1, 1, 5, f);
		this.halo[2].setRotationPoint(0.0F, 0.0F + f1, 0.0F);

		this.halo[3] = new ModelRenderer(this, 42, 11);
		this.halo[3].addBox(2.5F, -11F, -2.5F, 1, 1, 5, f);
		this.halo[3].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

		this.bipedHead.render(scale);
		this.bipedBody.render(scale);
		this.bipedRightArm.render(scale);
		this.bipedLeftArm.render(scale);
		this.bipedRightLeg.render(scale);
		this.bipedLeftLeg.render(scale);

		this.bipedBody2.render(scale);
		this.bipedRightArm2.render(scale);
		this.bipedLeftArm2.render(scale);
		this.wingLeft.render(scale);
		this.wingRight.render(scale);

		for (int i = 0; i < swordParts; i++) {
			this.sword[i].render(scale);
		}
		for (int i = 0; i < skirtParts; i++) {
			this.skirt[i].render(scale);
		}
		for (int i = 0; i < strandParts; i++) {
			this.strand[i].render(scale);
		}

		if (halow) {
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(770, 771);

			for (int i = 0; i < haloParts; i++) {
				this.halo[i].render(scale);
			}

			GL11.glEnable(GL11.GL_ALPHA_TEST);
		}
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		this.bipedHead.rotateAngleY = netHeadYaw / 57.29578F;
		this.bipedHead.rotateAngleX = headPitch / 57.29578F;

		this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedRightArm.rotateAngleZ = 0.05F;
		this.bipedLeftArm.rotateAngleZ = -0.05F;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;

		for (int i = 0; i < strandParts; i++) {
			this.strand[i].rotateAngleY = this.bipedHead.rotateAngleY;
			this.strand[i].rotateAngleX = this.bipedHead.rotateAngleX;
		}

		for (int i = 0; i < haloParts; i++) {
			this.halo[i].rotateAngleY = this.bipedHead.rotateAngleY;
			this.halo[i].rotateAngleX = this.bipedHead.rotateAngleX;
		}

		if (this.isRiding) {
			this.bipedRightArm.rotateAngleX += -0.6283185F;
			this.bipedLeftArm.rotateAngleX += -0.6283185F;
			this.bipedRightLeg.rotateAngleX = -1.256637F;
			this.bipedLeftLeg.rotateAngleX = -1.256637F;
			this.bipedRightLeg.rotateAngleY = 0.3141593F;
			this.bipedLeftLeg.rotateAngleY = -0.3141593F;
		}

		this.bipedRightArm.rotateAngleY = 0.0F;
		this.bipedLeftArm.rotateAngleY = 0.0F;

		if (this.onGround > -9990F) {
			float f6 = onGround;
			this.bipedBody2.rotateAngleY = this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * 3.141593F * 2.0F) * 0.2F;
			this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
			f6 = 1.0F - this.onGround;
			f6 *= f6;
			f6 *= f6;
			f6 = 1.0F - f6;
			float f7 = MathHelper.sin(f6 * 3.141593F);
			float f8 = MathHelper.sin(this.onGround * 3.141593F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
			this.bipedRightArm.rotateAngleX -= (double) f7 * 1.2D + (double) f8;
			this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
			this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * 3.141593F) * -0.4F;
		}

		this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

		for (int i = 0; i < swordParts; i++) {
			this.sword[i].rotateAngleZ = this.bipedRightArm.rotateAngleZ;
			this.sword[i].rotateAngleY = this.bipedRightArm.rotateAngleY;
			this.sword[i].rotateAngleX = this.bipedRightArm.rotateAngleX;
		}

		this.bipedRightArm2.rotateAngleZ = this.bipedRightArm.rotateAngleZ;
		this.bipedRightArm2.rotateAngleY = this.bipedRightArm.rotateAngleY;
		this.bipedRightArm2.rotateAngleX = this.bipedRightArm.rotateAngleX;
		this.bipedLeftArm2.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
		this.bipedLeftArm2.rotateAngleX = this.bipedLeftArm.rotateAngleX;

		this.wingLeft.rotateAngleY = -0.2F;
		this.wingRight.rotateAngleY = 0.2F;
		this.wingLeft.rotateAngleZ = -0.125F;
		this.wingRight.rotateAngleZ = 0.125F;

		this.wingLeft.rotateAngleY += Math.sin(this.sinage) / 6F;
		this.wingRight.rotateAngleY -= Math.sin(this.sinage) / 6F;
		this.wingLeft.rotateAngleZ += Math.cos(this.sinage) / (this.gonRound ? 8F : 3F);
		this.wingRight.rotateAngleZ -= Math.cos(this.sinage) / (this.gonRound ? 8F : 3F);

		this.skirt[0].rotateAngleX = -0.2F;
		this.skirt[1].rotateAngleX = -0.2F;
		this.skirt[2].rotateAngleX = 0.2F;
		this.skirt[3].rotateAngleX = 0.2F;
		this.skirt[4].rotateAngleZ = 0.2F;
		this.skirt[5].rotateAngleZ = -0.2F;

		if (this.bipedLeftLeg.rotateAngleX < -0.3F) {
			this.skirt[1].rotateAngleX += (this.bipedLeftLeg.rotateAngleX + 0.3F);
			this.skirt[2].rotateAngleX -= (this.bipedLeftLeg.rotateAngleX + 0.3F);
		}

		if (this.bipedLeftLeg.rotateAngleX > 0.3F) {
			this.skirt[3].rotateAngleX += (this.bipedLeftLeg.rotateAngleX - 0.3F);
			this.skirt[0].rotateAngleX -= (this.bipedLeftLeg.rotateAngleX - 0.3F);
		}

	}

}