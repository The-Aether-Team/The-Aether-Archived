package com.legacy.aether.client.renders.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.Aether;
import com.legacy.aether.client.models.entities.AechorPlantModel;
import com.legacy.aether.entities.hostile.EntityAechorPlant;

public class AechorPlantRenderer extends RenderLiving {

    public static final ResourceLocation TEXTURE = Aether.locate("textures/entities/aechor_plant/aechor_plant.png");

    public AechorPlantModel mode;

    public AechorPlantRenderer() {
        super(new AechorPlantModel(), 0.3F);
        this.mode = ((AechorPlantModel) this.mainModel);

        this.setRenderPassModel(this.mainModel);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f) {
        EntityAechorPlant plant = (EntityAechorPlant) entity;

        float f1 = (float) Math.sin((double) plant.sinage);
        float f3;

        if (plant.hurtTime > 0) {
            f1 *= 0.45F;
            f1 -= 0.125F;
            f3 = 1.75F + (float) Math.sin((double) (plant.sinage + 2.0F)) * 1.5F;
        } else {
            f1 *= 0.125F;
            f3 = 1.75F;
        }

        ((AechorPlantModel) this.mainModel).sinage = f1;
        ((AechorPlantModel) this.mainModel).sinage2 = f3;
        float f2 = 0.625F + (float) plant.getSize() / 6.0F;
        ((AechorPlantModel) this.mainModel).size = f2;
        this.shadowSize = f2 - 0.25F + f1;
    }

    protected int doAechorPlantRender(EntityAechorPlant entityaechorplant, int i, float f) {
        if (i != 0) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entityliving, int i, float f) {
        return this.doAechorPlantRender((EntityAechorPlant) entityliving, i, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

}