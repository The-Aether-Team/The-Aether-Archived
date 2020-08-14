package com.gildedgames.the_aether.client.models.attachments;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelHalo extends ModelBase
{

    public ModelRenderer halo1, halo2, halo3, halo4;
 
    public ModelHalo()
  	{
    	textureWidth = 64;
    	textureHeight = 32;
    	halo1 = new ModelRenderer(this, 0, 0);
    	halo1.addBox(-3F, 0F, -2F, 1, 1, 4);
    	halo1.setTextureSize(64, 32);
    	halo1.setRotationPoint(0.0F, 0F, 0F);
    	halo2 = new ModelRenderer(this, 0, 0);
    	halo2.addBox(2F, 0F, -2F, 1, 1, 4);
    	halo2.setTextureSize(64, 32);
    	halo3 = new ModelRenderer(this, 0, 0);
      	halo3.addBox(-2F, 0F, 2F, 4, 1, 1);
      	halo3.setTextureSize(64, 32);
      	halo4 = new ModelRenderer(this, 0, 0);
      	halo4.addBox(-2F, 0F, -3F, 4, 1, 1);
      	halo4.setTextureSize(64, 32);
      	halo4.setRotationPoint(0.0F, 0F, 0.0F);
  	}

    public void renderHalo(ModelRenderer renderer, float scale)
    {
    	copyModelAngles(this.halo1, renderer);
    	copyModelAngles(this.halo2, renderer);
    	copyModelAngles(this.halo3, renderer);
    	copyModelAngles(this.halo4, renderer);

    	this.halo1.render(scale);
    	this.halo2.render(scale);
    	this.halo3.render(scale);
    	this.halo4.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;

        if (flag)
        {
            this.halo1.rotateAngleX = -((float)Math.PI / 4F);
            this.halo2.rotateAngleX = -((float)Math.PI / 4F);
            this.halo3.rotateAngleX = -((float)Math.PI / 4F);
            this.halo4.rotateAngleX = -((float)Math.PI / 4F);
        }
        else
        {
            this.halo1.rotateAngleX = headPitch * 0.017453292F;
            this.halo2.rotateAngleX = headPitch * 0.017453292F;
            this.halo3.rotateAngleX = headPitch * 0.017453292F;
            this.halo4.rotateAngleX = headPitch * 0.017453292F;
        }

        if (entityIn.isSneaking())
        {
            this.halo1.rotationPointY = 1.0F;
            this.halo2.rotationPointY = 1.0F;
            this.halo3.rotationPointY = 1.0F;
            this.halo4.rotationPointY = 1.0F;
        }
        else
        {
            this.halo1.rotationPointY = 0.0F;
            this.halo2.rotationPointY = 0.0F;
            this.halo3.rotationPointY = 0.0F;
            this.halo4.rotationPointY = 0.0F;
        }

    }

}