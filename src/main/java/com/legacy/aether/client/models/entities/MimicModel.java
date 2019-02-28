package com.legacy.aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class MimicModel extends ModelBase
{
    public ModelRenderer upper_body;
    public ModelRenderer lower_body;
    public ModelRenderer left_leg;
    public ModelRenderer right_leg;
    public ModelRenderer nob;
    
    public MimicModel()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.left_leg = new ModelRenderer(this, 64, 0);
        this.left_leg.setRotationPoint(1.5F, 9.0F, 0.0F);
        this.left_leg.addBox(0.0F, 0.0F, -3.0F, 6, 15, 6, 0.0F);
        this.upper_body = new ModelRenderer(this, 0, 10);
        this.upper_body.setRotationPoint(-8.0F, 0.0F, 8.0F);
        this.upper_body.addBox(0.0F, 0.0F, 0.0F, 16, 6, 16, 0.0F);
        this.setRotateAngle(upper_body, 3.141592653589793F, 0.0F, 0.0F);
        this.right_leg = new ModelRenderer(this, 64, 0);
        this.right_leg.mirror = true;
        this.right_leg.setRotationPoint(-2.5F, 9.0F, 0.0F);
        this.right_leg.addBox(-5.1F, 0.0F, -3.0F, 6, 15, 6, 0.0F);
        this.lower_body = new ModelRenderer(this, 0, 38);
        this.lower_body.setRotationPoint(-8.0F, 0.0F, -8.0F);
        this.lower_body.addBox(0.0F, 0.0F, 0.0F, 16, 10, 16, 0.0F);
        this.nob = new ModelRenderer(this, 0, 0);
        this.nob.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.nob.addBox(-1.0F, -2.0F, 16.0F, 2, 4, 1, 0.0F);
        this.upper_body.addChild(this.nob);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
        this.left_leg.render(f5);
        this.upper_body.render(f5);
        this.right_leg.render(f5);
        this.lower_body.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.upper_body.rotateAngleX = 3.14159265F - (float) ((Math.cos((float) ageInTicks / 10F * 3.14159265F)) + 1F) * 0.6F;
		this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.7F;
		this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.7F;
	}
}
