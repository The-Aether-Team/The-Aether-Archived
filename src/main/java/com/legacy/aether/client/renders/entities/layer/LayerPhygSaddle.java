package com.legacy.aether.client.renders.entities.layer;

import com.legacy.aether.client.renders.entities.PhygRenderer;
import com.legacy.aether.entities.passive.mountable.EntityPhyg;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerPhygSaddle implements LayerRenderer<EntityPhyg>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    private final PhygRenderer phygRenderer;
    private final ModelPig pigModel = new ModelPig(0.5F);

    public LayerPhygSaddle(PhygRenderer phygRendererIn)
    {
        this.phygRenderer = phygRendererIn;
    }

    public void doRenderLayer(EntityPhyg entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.getSaddled())
        {
            this.phygRenderer.bindTexture(TEXTURE);
            this.pigModel.setModelAttributes(this.phygRenderer.getMainModel());
            this.pigModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}