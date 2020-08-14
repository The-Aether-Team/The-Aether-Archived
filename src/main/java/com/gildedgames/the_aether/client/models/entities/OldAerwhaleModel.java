package com.gildedgames.the_aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * AerwhaleModel - Either Mojang or a mod author
 * Created using Tabula 7.0.0
 */
public class OldAerwhaleModel extends ModelBase {
	public ModelRenderer Middlebody;
	public ModelRenderer LeftFin;
	public ModelRenderer Head;
	public ModelRenderer BackfinLeft;
	public ModelRenderer BackBody;
	public ModelRenderer BackfinRight;
	public ModelRenderer RightFin;

	public OldAerwhaleModel() {
		this.textureWidth = 192;
		this.textureHeight = 96;
		this.Middlebody = new ModelRenderer(this, 0, 0);
		this.Middlebody.setRotationPoint(0.0F, -1.0F, 14.0F);
		this.Middlebody.addBox(-9.0F, -6.0F, 1.0F, 15, 15, 15, 0.0F);
		this.Head = new ModelRenderer(this, 60, 0);
		this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Head.addBox(-12.0F, -9.0F, -14.0F, 21, 18, 30, 0.0F);
		this.BackBody = new ModelRenderer(this, 0, 30);
		this.BackBody.setRotationPoint(0.0F, 5.0F, 38.0F);
		this.BackBody.addBox(-6.0F, -9.0F, -8.5F, 9, 9, 12, 0.0F);
		this.BackfinRight = new ModelRenderer(this, 0, 51);
		this.BackfinRight.setRotationPoint(-5.0F, 2.2F, 38.0F);
		this.BackfinRight.addBox(-4.0F, 0.0F, -6.0F, 24, 3, 12, 0.0F);
		this.setRotateAngle(BackfinRight, 0.10471975511965977F, -2.5497515042385164F, 0.0F);
		this.BackfinLeft = new ModelRenderer(this, 0, 51);
		this.BackfinLeft.setRotationPoint(3.0F, 2.2F, 38.0F);
		this.BackfinLeft.addBox(-4.0F, 0.0F, -6.0F, 24, 3, 12, 0.0F);
		this.setRotateAngle(BackfinLeft, -0.10471975511965977F, -0.593411945678072F, 0.0F);
		this.RightFin = new ModelRenderer(this, 0, 66);
		this.RightFin.setRotationPoint(-10.0F, 4.0F, 10.0F);
		this.RightFin.addBox(-12.0F, 1.4F, -6.0F, 12, 3, 6, 0.0F);
		this.setRotateAngle(RightFin, 0.0F, 0.17453292519943295F, 0.0F);
		this.LeftFin = new ModelRenderer(this, 0, 66);
		this.LeftFin.setRotationPoint(7.0F, 4.0F, 10.0F);
		this.LeftFin.addBox(0.0F, 1.4F, -6.0F, 12, 3, 6, 0.0F);
		this.setRotateAngle(LeftFin, 0.0F, -0.17453292519943295F, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.Middlebody.render(f5);
		this.Head.render(f5);
		this.BackBody.render(f5);
		this.BackfinRight.render(f5);
		this.BackfinLeft.render(f5);
		this.RightFin.render(f5);
		this.LeftFin.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
