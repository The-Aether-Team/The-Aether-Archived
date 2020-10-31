package com.gildedgames.the_aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class AerwhaleModel extends ModelBase
{

    public ModelRenderer FrontBody;
    public ModelRenderer RightFin;
    public ModelRenderer BottomPartHead;
    public ModelRenderer LeftFin;
    public ModelRenderer BottomPartMiddlebody;
    public ModelRenderer Head;
    public ModelRenderer MiddleFin;
    public ModelRenderer BackfinRight;
    public ModelRenderer BackBody;
    public ModelRenderer BackfinLeft;
    public ModelRenderer Middlebody;

    public AerwhaleModel()
    {
        this.textureWidth = 512;
        this.textureHeight = 64;

        this.FrontBody = new ModelRenderer(this, 0, 0);
        this.FrontBody.addBox(-11.5F, -1.0F, -0.5F, 19, 5, 21);
        this.FrontBody.setRotationPoint(2.0F, 6.0F, 38.0F);
        this.FrontBody.setTextureSize(512, 64);
        this.FrontBody.mirror = true;
        this.setRotation(this.FrontBody, -0.1047198F, 0.0F, 0.0F);
  
        this.RightFin = new ModelRenderer(this, 446, 1);
        this.RightFin.addBox(-20.0F, -2.0F, -6.0F, 19, 3, 14);
        this.RightFin.setRotationPoint(-10.0F, 4.0F, 10.0F);
        this.RightFin.setTextureSize(512, 64);
        this.RightFin.mirror = true;
        this.setRotation(this.RightFin, -0.148353F, 0.2094395F, 0.0F);
        this.RightFin.mirror = false;

        this.BottomPartHead = new ModelRenderer(this, 116, 28);
        this.BottomPartHead.addBox(-13.0F, 4.0F, -15.0F, 26, 6, 30);
        this.BottomPartHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BottomPartHead.setTextureSize(512, 64);
        this.BottomPartHead.mirror = true;
        this.setRotation(this.BottomPartHead, 0.0F, 0.0F, 0.0F);

        this.LeftFin = new ModelRenderer(this, 446, 1);
        this.LeftFin.addBox(1.0F, -2.0F, -6.0F, 19, 3, 14);
        this.LeftFin.setRotationPoint(10.0F, 4.0F, 10.0F);
        this.LeftFin.setTextureSize(512, 64);
        this.LeftFin.mirror = true;
        this.setRotation(this.LeftFin, -0.148353F, -0.2094395F, 0.0F);

        this.BottomPartMiddlebody = new ModelRenderer(this, 16, 32);
        this.BottomPartMiddlebody.addBox(-12.0F, 5.0F, -1.0F, 24, 6, 26);
        this.BottomPartMiddlebody.setRotationPoint(0.0F, -1.0F, 14.0F);
        this.BottomPartMiddlebody.setTextureSize(512, 64);
        this.BottomPartMiddlebody.mirror = true;
        this.setRotation(this.BottomPartMiddlebody, 0.0F, 0.0F, 0.0F);

        this.Head = new ModelRenderer(this, 408, 18);
        this.Head.addBox(-12.0F, -9.0F, -14.0F, 24, 18, 28);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.setTextureSize(512, 64);
        this.Head.mirror = true;
        this.setRotation(this.Head, 0.0F, 0.0F, 0.0F);

        this.MiddleFin = new ModelRenderer(this, 318, 35);
        this.MiddleFin.addBox(-1.0F, -11.0F, 7.0F, 2, 7, 8);
        this.MiddleFin.setRotationPoint(0.0F, -1.0F, 14.0F);
        this.MiddleFin.setTextureSize(512, 64);
        this.MiddleFin.mirror = true;
        this.setRotation(this.MiddleFin, -0.1441704F, 0.0F, 0.0F);

        this.BackfinRight = new ModelRenderer(this, 261, 5);
        this.BackfinRight.addBox(-11.0F, 0.0F, -6.0F, 15, 3, 24);
        this.BackfinRight.setRotationPoint(-4.0F, 5.0F, 59.0F);
        this.BackfinRight.setTextureSize(512, 64);
        this.BackfinRight.mirror = true;
        this.setRotation(this.BackfinRight, -0.1047198F, -0.7330383F, 0.0F);
        this.BackfinRight.mirror = false;

        this.BackBody = new ModelRenderer(this, 228, 32);
        this.BackBody.addBox(-10.5F, -9.0F, -2.0F, 17, 10, 22);
        this.BackBody.setRotationPoint(2.0F, 5.0F, 38.0F);
        this.BackBody.setTextureSize(512, 64);
        this.BackBody.mirror = true;
        this.setRotation(this.BackBody, -0.1047198F, 0.0F, 0.0F);

        this.BackfinLeft = new ModelRenderer(this, 261, 5);
        this.BackfinLeft.addBox(-4.0F, 0.0F, -6.0F, 13, 3, 24);
        this.BackfinLeft.setRotationPoint(5.0F, 5.0F, 59.0F);
        this.BackfinLeft.setTextureSize(512, 64);
        this.BackfinLeft.mirror = true;
        this.setRotation(this.BackfinLeft, -0.1047198F, 0.7330383F, 0.0F);

        this.Middlebody = new ModelRenderer(this, 314, 25);
        this.Middlebody.addBox(-11.0F, -5.0F, -1.0F, 22, 14, 25);
        this.Middlebody.setRotationPoint(0.0F, -1.0F, 14.0F);
        this.Middlebody.setTextureSize(512, 64);
        this.Middlebody.mirror = true;
        this.setRotation(this.Middlebody, -0.0698132F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.FrontBody.render(scale);
        this.RightFin.render(scale);
        this.BottomPartHead.render(scale);
        this.LeftFin.render(scale);
        this.BottomPartMiddlebody.render(scale);
        this.Head.render(scale);
        this.MiddleFin.render(scale);
        this.BackfinRight.render(scale);
        this.BackBody.render(scale);
        this.BackfinLeft.render(scale);
        this.Middlebody.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}