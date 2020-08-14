package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.client.models.entities.CrystalModel;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityCrystal;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class CrystalRenderer extends RenderLiving {

    public CrystalRenderer() {
        super(new CrystalModel(), 0.25F);
    }

    @Override
    public void preRenderCallback(EntityLivingBase entity, float f) {
        for (int i = 0; i < 3; i++) {
            ((CrystalModel) this.mainModel).sinage[i] = ((EntityCrystal) entity).sinage[i];
        }

        GL11.glTranslatef(0.0F, 0.3F, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return Aether.locate("textures/entities/crystals/" + ((EntityCrystal) entity).getCrystalType().name().toLowerCase() + ".png");
    }

}
