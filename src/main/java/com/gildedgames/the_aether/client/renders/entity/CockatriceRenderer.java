package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.client.models.entities.CockatriceModel;
import com.gildedgames.the_aether.entities.hostile.EntityCockatrice;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class CockatriceRenderer extends RenderLiving {

    private static final ResourceLocation TEXTURE = Aether.locate("textures/entities/cockatrice/cockatrice.png");

    public CockatriceRenderer() {
        super(new CockatriceModel(), 1.0F);
    }

    protected float getWingRotation(EntityCockatrice cockatrice, float f) {
        float f1 = cockatrice.prevWingRotation + (cockatrice.wingRotation - cockatrice.prevWingRotation) * f;
        float f2 = cockatrice.prevDestPos + (cockatrice.destPos - cockatrice.prevDestPos) * f;

        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase cockatrice, float f) {
        return this.getWingRotation((EntityCockatrice) cockatrice, f);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase cockatrice, float f) {
        GL11.glScalef(1.8F, 1.8F, 1.8F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity cockatrice) {
        return TEXTURE;
    }

}