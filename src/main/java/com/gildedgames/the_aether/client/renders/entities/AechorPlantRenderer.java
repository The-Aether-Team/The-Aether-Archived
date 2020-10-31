package com.gildedgames.the_aether.client.renders.entities;

import com.gildedgames.the_aether.entities.hostile.EntityAechorPlant;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.client.models.entities.AechorPlantModel;

public class AechorPlantRenderer extends RenderLiving<EntityAechorPlant>
{

    public static final ResourceLocation TEXTURE = new ResourceLocation("aether_legacy", "textures/entities/aechor_plant/aechor_plant.png");

    public AechorPlantModel mode;

    public AechorPlantRenderer(RenderManager renderManager)
    {
        super(renderManager, new AechorPlantModel(), 0.3F);
        this.mode = (AechorPlantModel) this.mainModel;
    }

    protected void preRenderCallback(EntityAechorPlant b1, float f)
    {
        float f1 = (float)Math.sin((double)b1.sinage);
        float f3;

        if (b1.hurtTime > 0)
        {
            f1 *= 0.45F;
            f1 -= 0.125F;
            f3 = 1.75F + (float)Math.sin((double)(b1.sinage + 2.0F)) * 1.5F;
        }
        else
        {
            f1 *= 0.125F;
            f3 = 1.75F;
        }

        this.mode.sinage = f1;
        this.mode.sinage2 = f3;
        float f2 = 0.625F + (float)b1.size / 6.0F;
        this.mode.size = f2;
        this.shadowSize = f2 - 0.25F;
    }

    protected int a(EntityAechorPlant entityaechorplant, int i, float f)
    {
        if (i != 0)
        {
            return -1;
        }
        else
        {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.325F);
            return 1;
        }
    }

    protected int shouldRenderPass(EntityAechorPlant entityliving, int i, float f)
    {
        return this.a(entityliving, i, f);
    }

    protected ResourceLocation getEntityTexture(EntityAechorPlant entity)
    {
        return TEXTURE;
    }

}