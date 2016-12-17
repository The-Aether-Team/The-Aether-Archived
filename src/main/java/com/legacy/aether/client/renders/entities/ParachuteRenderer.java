package com.legacy.aether.client.renders.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.blocks.natural.BlockAercloud;
import com.legacy.aether.server.blocks.util.EnumCloudType;
import com.legacy.aether.server.entities.passive.mountable.EntityParachute;

public class ParachuteRenderer extends Render<EntityParachute>
{

	public ParachuteRenderer(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void renderParachute(EntityParachute entityParachute, double d, double d1, double d2, float f, float f1)
    {
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        int meta = entityParachute.isGoldenParachute ? 2 : 0;
        IBlockState state = BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.cloud_type, EnumCloudType.getType(meta));

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)d - 0.5F, (float)d1 - 0.5F, (float)d2 + 0.5F);
        Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(state, entityParachute.getBrightness(f1));
        GlStateManager.popMatrix();

        super.doRender(entityParachute, d, d1, d2, f, f1);
    }

    public void doRender(EntityParachute entity, double d, double d1, double d2, float f, float f1)
    {
        this.renderParachute(entity, d, d1, d2, f, f1);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityParachute entity)
	{
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}