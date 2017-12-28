package com.legacy.aether.client.renders.entities.layer;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.client.models.entities.MoaModel;
import com.legacy.aether.client.renders.entities.MoaRenderer;
import com.legacy.aether.entities.passive.mountable.EntityMoa;
import com.legacy.aether.entities.util.AetherMoaTypes;

@SideOnly(Side.CLIENT)
public class LayerMoaSaddle implements LayerRenderer<EntityMoa>
{
    private static final ResourceLocation SADDLE = new ResourceLocation("aether_legacy", "textures/entities/moa/moa_saddle.png");

    private static final ResourceLocation BLACK_SADDLE = new ResourceLocation("aether_legacy", "textures/entities/moa/black_moa_saddle.png");

    private final MoaRenderer moaRenderer;
    private final MoaModel moaModel = new MoaModel(0.25F);

    public LayerMoaSaddle(MoaRenderer moaRendererIn)
    {
        this.moaRenderer = moaRendererIn;
    }

    public void doRenderLayer(EntityMoa entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.getSaddled() && !(entitylivingbaseIn.getMoaType() == AetherMoaTypes.black))
        {
        	this.moaModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
            this.moaRenderer.bindTexture(SADDLE);
            this.moaModel.setModelAttributes(this.moaRenderer.getMainModel());
            this.moaModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
        
        else if (entitylivingbaseIn.getSaddled())
        {
        	this.moaModel.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
            this.moaRenderer.bindTexture(BLACK_SADDLE);
            this.moaModel.setModelAttributes(this.moaRenderer.getMainModel());
            this.moaModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}