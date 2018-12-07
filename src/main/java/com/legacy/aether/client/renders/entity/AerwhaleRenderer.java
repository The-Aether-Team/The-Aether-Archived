package com.legacy.aether.client.renders.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.legacy.aether.Aether;
import com.legacy.aether.AetherConfig;
import com.legacy.aether.client.models.entities.AerwhaleModel;
import com.legacy.aether.client.models.entities.OldAerwhaleModel;
import com.legacy.aether.entities.passive.EntityAerwhale;

public class AerwhaleRenderer extends Render {

    private static final ResourceLocation AERWHALE_TEXTURE = Aether.locate("textures/entities/aerwhale/aerwhale.png");

    private static final ResourceLocation OLD_AERWHALE_TEXTURE = Aether.locate("textures/entities/aerwhale/old_aerwhale.png");

    private ModelBase model = new AerwhaleModel();

    private ModelBase old_model = new OldAerwhaleModel();

    public AerwhaleRenderer() {
        super();
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        EntityAerwhale aerwhale = (EntityAerwhale) entity;

        GL11.glPushMatrix();

        if (aerwhale.hurtResistantTime > 11) {
            GL11.glColor3f(1.0F, 0.5F, 0.5F);
        } else {
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }


        if (AetherConfig.oldMobsEnabled()) {
            this.renderManager.renderEngine.bindTexture(OLD_AERWHALE_TEXTURE);
        } else {
            this.renderManager.renderEngine.bindTexture(AERWHALE_TEXTURE);
        }

        GL11.glTranslated(x, y + 2.0D, z);
        GL11.glRotatef(90.0F - (float) aerwhale.aerwhaleRotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180.0F - (float) aerwhale.aerwhaleRotationPitch, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(2.0F, 2.0F, 2.0F);

        if (AetherConfig.oldMobsEnabled()) {
            this.old_model.render(aerwhale, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        } else {
            this.model.render(aerwhale, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        }

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return AERWHALE_TEXTURE;
    }

}