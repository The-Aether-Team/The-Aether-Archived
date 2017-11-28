package com.legacy.aether.client.renders.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.entities.block.EntityFloatingBlock;

public class FloatingBlockRenderer extends Render<EntityFloatingBlock>
{

    public FloatingBlockRenderer(RenderManager manager)
    {
    	super(manager);
        this.shadowSize = 0.5F;
    }

	public void renderFloatingBlock(EntityFloatingBlock entityFloatingBlock, double d, double d1, double d2, float f, float f1)
    {
        if (entityFloatingBlock.getBlockState() == null)
        {
        	return;
        }

        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        IBlockState iblockstate = entityFloatingBlock.getBlockState();
        BlockPos blockpos = new BlockPos(entityFloatingBlock);
        World world = entityFloatingBlock.worldObj;

        if (iblockstate != world.getBlockState(blockpos) && iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE)
        {
            if (iblockstate.getRenderType() == EnumBlockRenderType.MODEL)
            {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)d, (float)d1, (float)d2);
                GlStateManager.disableLighting();
                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer worldrenderer = tessellator.getBuffer();
                worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
                int i = blockpos.getX();
                int j = blockpos.getY();
                int k = blockpos.getZ();
                worldrenderer.setTranslation((double)((float)(-i) - 0.5F), (double)(-j), (double)((float)(-k) - 0.5F));
                Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel
                (world, Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(iblockstate), 
                		iblockstate, blockpos, worldrenderer, false);
                worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
                tessellator.draw();
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();

                super.doRender(entityFloatingBlock, d, d1, d2, f, f1);
            }
        }
    }

    public void doRender(EntityFloatingBlock entity, double d, double d1, double d2, float f, float f1)
    {
        this.renderFloatingBlock((EntityFloatingBlock)entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFloatingBlock entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

}