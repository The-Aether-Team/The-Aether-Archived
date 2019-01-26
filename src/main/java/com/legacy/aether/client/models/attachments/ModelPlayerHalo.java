package com.legacy.aether.client.models.attachments;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPlayerHalo extends ModelBase//ModelBiped
{
    public ModelRenderer halo1;
    public ModelRenderer halo2;
    public ModelRenderer halo3;
    public ModelRenderer halo4;

    public ModelPlayerHalo()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.halo3 = new ModelRenderer(this, 0, 4);
        this.halo3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.halo3.addBox(2.0F, -10.0F, -2.0F, 1, 1, 4, 0.0F);
        this.halo1 = new ModelRenderer(this, 0, 1);
        this.halo1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.halo1.addBox(-2.0F, -10.0F, -3.0F, 4, 1, 1, 0.0F);
        this.halo4 = new ModelRenderer(this, 0, 1);
        this.halo4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.halo4.addBox(-2.0F, -10.0F, 2.0F, 4, 1, 1, 0.0F);
        this.halo2 = new ModelRenderer(this, 0, 4);
        this.halo2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.halo2.addBox(-3.0F, -10.0F, -2.0F, 1, 1, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
        /*this.halo3.render(f5);
        this.halo1.render(f5);
        this.halo4.render(f5);
        this.halo2.render(f5);*/
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	
    	/*this.halo1.rotateAngleX = this.bipedHead.rotateAngleX;
  	  	this.halo1.rotateAngleY = this.bipedHead.rotateAngleY;
  	  	this.halo2.rotateAngleX = this.bipedHead.rotateAngleX;
	  	this.halo2.rotateAngleY = this.bipedHead.rotateAngleY;
	  	this.halo3.rotateAngleX = this.bipedHead.rotateAngleX;
  	  	this.halo3.rotateAngleY = this.bipedHead.rotateAngleY;
  	  	this.halo4.rotateAngleX = this.bipedHead.rotateAngleX;
	  	this.halo4.rotateAngleY = this.bipedHead.rotateAngleY;*/
    }
}
