package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.client.models.entities.AerbunnyModel;
import com.gildedgames.the_aether.entities.passive.mountable.EntityAerbunny;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class AerbunnyRenderer extends RenderLiving {

    private static final ResourceLocation TEXTURE = Aether.locate("textures/entities/aerbunny/aerbunny.png");

    public AerbunnyModel model;

    public AerbunnyRenderer() {
        super(new AerbunnyModel(), 0.3F);
        this.model = (AerbunnyModel) this.mainModel;
    }

    protected void rotateAerbunny(EntityAerbunny entitybunny) {
        if (!entitybunny.isRiding()) {
            GL11.glTranslated(0.0D, 0.2D, 0.0D);
        } else if (entitybunny.ridingEntity == Minecraft.getMinecraft().thePlayer) {
            GL11.glTranslated(0.0D, 1.7D, 0.0D);
        }

        if (!entitybunny.onGround) {
            if (entitybunny.motionY > 0.5D) {
                GL11.glRotatef(15.0F, -1.0F, 0.0F, 0.0F);
            } else if (entitybunny.motionY < -0.5D) {
                GL11.glRotatef(-15.0F, -1.0F, 0.0F, 0.0F);
            } else {
                GL11.glRotatef((float) (entitybunny.motionY * 30.0D), -1.0F, 0.0F, 0.0F);
            }
        }

        this.model.puffiness = (float) (entitybunny.ridingEntity != null ? entitybunny.getPuffinessClient() : entitybunny.getPuffiness()) / 10.0F;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entitybunny, float f) {
        this.rotateAerbunny((EntityAerbunny) entitybunny);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

}
