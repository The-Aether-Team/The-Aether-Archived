package com.gildedgames.the_aether.client.models.entities;

import com.gildedgames.the_aether.entities.hostile.EntityMimic;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MimicModel extends ModelBase {

	ModelRenderer box, boxLid;

	ModelRenderer leftLeg, rightLeg;

	public MimicModel() {
		this.box = new ModelRenderer(this, 0, 0);
		this.box.addBox(-8F, 0F, -8F, 16, 10, 16);
		this.box.setRotationPoint(0F, -24F, 0F);

		this.boxLid = new ModelRenderer(this, 16, 10);
		this.boxLid.addBox(0F, 0F, 0F, 16, 6, 16);
		this.boxLid.setRotationPoint(-8F, -24F, 8F);

		this.leftLeg = new ModelRenderer(this, 0, 0);
		this.leftLeg.addBox(-3F, 0F, -3F, 6, 15, 6);
		this.leftLeg.setRotationPoint(-4F, -15F, 0F);

		this.rightLeg = new ModelRenderer(this, 0, 0);
		this.rightLeg.addBox(-3F, 0F, -3F, 6, 15, 6);
		this.rightLeg.setRotationPoint(4F, -15F, 0F);

	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		EntityMimic mimic = (EntityMimic) entityIn;
		this.boxLid.rotateAngleX = 3.14159265F - mimic.mouth;
		this.rightLeg.rotateAngleX = mimic.legs;
		this.leftLeg.rotateAngleX = -mimic.legs;
	}

	public void renderHead(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityMimic mimic) {
		this.box.render(scale);
	}

	public void renderLegs(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityMimic mimic) {
		boxLid.render(scale);
		leftLeg.render(scale);
		rightLeg.render(scale);
	}

}