package com.gildedgames.the_aether.client.renders.entity;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.client.models.entities.SheepuffModel;
import com.gildedgames.the_aether.client.models.entities.SheepuffWoolModel;
import com.gildedgames.the_aether.client.models.entities.SheepuffedModel;
import com.gildedgames.the_aether.entities.passive.EntitySheepuff;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class SheepuffRenderer extends RenderLiving {

    private static final ResourceLocation TEXTURE = Aether.locate("textures/entities/sheepuff/sheepuff.png");

    private static final ResourceLocation TEXTURE_FUR = Aether.locate("textures/entities/sheepuff/fur.png");

    public SheepuffRenderer() {
        super(new SheepuffWoolModel(), 0.7F);
    }

    protected int renderLayer(EntitySheepuff entity, int pass, float particleTicks) {
        if (entity.isInvisible()) {
            return 0;
        } else if (pass == 0 && !entity.getSheared()) {
            this.bindTexture(TEXTURE_FUR);
            this.setRenderPassModel(entity.getPuffed() ? new SheepuffedModel() : new SheepuffModel());

            if (this.renderPassModel != null) {
                this.renderPassModel.isChild = this.mainModel.isChild;
            }

            if (entity.hasCustomNameTag() && "jeb_".equals(entity.getCustomNameTag())) {
                int k = entity.ticksExisted / 25 + entity.getEntityId();
                int l = k % EntitySheep.fleeceColorTable.length;
                int i1 = (k + 1) % EntitySheep.fleeceColorTable.length;
                float f1 = ((float) (entity.ticksExisted % 25) + particleTicks) / 25.0F;
                GL11.glColor3f(EntitySheep.fleeceColorTable[l][0] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][0] * f1, EntitySheep.fleeceColorTable[l][1] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][1] * f1, EntitySheep.fleeceColorTable[l][2] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][2] * f1);
            } else {
                int j = entity.getFleeceColor();
                GL11.glColor3f(EntitySheep.fleeceColorTable[j][0], EntitySheep.fleeceColorTable[j][1], EntitySheep.fleeceColorTable[j][2]);
            }

            return 1;
        }

        return -1;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase entity, int pass, float particleTicks) {
        return this.renderLayer((EntitySheepuff) entity, pass, particleTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

}