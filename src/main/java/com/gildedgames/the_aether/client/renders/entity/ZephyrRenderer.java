package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.client.models.entities.OldZephyrModel;
import com.gildedgames.the_aether.client.models.entities.ZephyrModel;
import com.gildedgames.the_aether.entities.hostile.EntityZephyr;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class ZephyrRenderer extends RenderLiving {

    private static final ResourceLocation TEXTURE = Aether.locate("textures/entities/zephyr/zephyr_main.png");

    private static final ResourceLocation TEXTURE_ADDON = Aether.locate("textures/entities/zephyr/zephyr_layer.png");

    private static final ResourceLocation TEXTURE_OLD = Aether.locate("textures/entities/zephyr/zephyr_old.png");

    public ZephyrRenderer() {
        super(AetherConfig.oldMobsEnabled() ? new OldZephyrModel() : new ZephyrModel(), 0.5F);
    }

    protected void renderZephyrMovement(EntityZephyr zephyr, float partialTickTime) {
        float f1 = ((float) zephyr.prevAttackCounter + (float) (zephyr.attackCounter - zephyr.prevAttackCounter) * partialTickTime) / 20.0F;

        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        f1 = 1.0F / (f1 * f1 * f1 * f1 * f1 * 2.0F + 1.0F);

        float f2 = (8.0F + f1) / 2.0F;
        float f3 = (8.0F + 1.0F / f1) / 2.0F;
        GL11.glScalef(f3, f2, f3);
        GL11.glTranslated(0.0D, 0.5D, 0.0D);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (AetherConfig.oldMobsEnabled()) {
            GL11.glScalef(0.8F, 0.8F, 0.8F);
            GL11.glTranslated(0.0D, -0.1D, 0.0D);
        }
    }

    protected int renderLayers(EntityZephyr entity, int pass, float particleTicks) {
        if (entity.isInvisible()) {
            return 0;
        } else if (pass == 1) {
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            if (this.renderPassModel != this.mainModel) {
                this.setRenderPassModel(this.mainModel);
            }

            this.bindTexture(TEXTURE_ADDON);

            return 1;
        }

        return -1;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int pass, float particleTicks) {
        return this.renderLayers((EntityZephyr) entity, pass, particleTicks);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase zephyr, float partialTickTime) {
        this.renderZephyrMovement((EntityZephyr) zephyr, partialTickTime);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity zephyr) {
        return AetherConfig.oldMobsEnabled() ? TEXTURE_OLD : TEXTURE;
    }

}