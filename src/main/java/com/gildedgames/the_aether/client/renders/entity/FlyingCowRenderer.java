package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.client.models.entities.FlyingCowModel;
import com.gildedgames.the_aether.client.models.entities.FlyingCowWingModel;
import com.gildedgames.the_aether.entities.passive.mountable.EntityFlyingCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class FlyingCowRenderer extends RenderLiving {

    private static final ResourceLocation TEXTURE = Aether.locate("textures/entities/flying_cow/flying_cow.png");

    private static final ResourceLocation TEXTURE_WINGS = Aether.locate("textures/entities/flying_cow/wings.png");

    private static final ResourceLocation TEXTURE_SADDLE = Aether.locate("textures/entities/flying_cow/saddle.png");

    private final FlyingCowWingModel wingModel = new FlyingCowWingModel();

    private final FlyingCowModel saddleModel = new FlyingCowModel(0.5F);

    public FlyingCowRenderer() {
        super(new FlyingCowModel(0.0F), 0.7F);
    }

    protected int renderLayers(EntityFlyingCow entity, int pass, float particleTicks) {
        if (entity.isInvisible()) {
            return 0;
        } else if (pass == 0) {
            this.setRenderPassModel(this.wingModel);
            this.bindTexture(TEXTURE_WINGS);

            return 1;
        } else if (pass == 1 && entity.isSaddled()) {
            this.setRenderPassModel(this.saddleModel);
            this.bindTexture(TEXTURE_SADDLE);

            return 1;
        }

        return -1;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int pass, float particleTicks) {
        return this.renderLayers((EntityFlyingCow) entity, pass, particleTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

}