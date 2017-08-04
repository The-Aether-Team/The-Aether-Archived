package com.legacy.aether.client.models.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class CrystalModel extends ModelBase
{

    public ModelRenderer head[];

	public float sinage[];

	private static final float sponge = (180F / 3.141593F);

    public CrystalModel()
    {
        this(0.0F);
    }

    public CrystalModel(float f) 
    {
        this(f, 0.0F);
    }

    public CrystalModel(float f, float f1)
    {
		sinage = new float[3];
		head = new ModelRenderer[3];

        head[0] = new ModelRenderer(this, 0, 0);
		head[1] = new ModelRenderer(this, 32, 0);
		head[2] = new ModelRenderer(this, 0, 16);
		
		for(int i = 0; i < 3; i ++) {
			head[i].addBox(-4F, -4F, -4F, 8, 8, 8, f);
			head[i].setRotationPoint(0.0F, 0.0F + f1, 0.0F);
		}
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
		GL11.glTranslatef(0F, 0.75F, 0F);

		GL11.glEnable(2977 /*GL_NORMALIZE*/);
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glBlendFunc(770, 771);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glPushMatrix();
		GL11.glRotatef(sinage[0] * sponge, 1F, 0F, 0F);
		head[0].render(scale);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotatef(sinage[1] * sponge, 0F, 1F, 0F);
		head[1].render(scale);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotatef(sinage[2] * sponge, 0F, 0F, 1F);
		head[2].render(scale);
		GL11.glPopMatrix();

		GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        for(int i = 0; i < 3; i ++) 
        {
			head[i].rotateAngleY = netHeadYaw / 57.29578F;
			head[i].rotateAngleX = headPitch / 57.29578F;
		}
    }

}