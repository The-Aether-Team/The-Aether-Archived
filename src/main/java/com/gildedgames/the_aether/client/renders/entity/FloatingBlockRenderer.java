package com.gildedgames.the_aether.client.renders.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.gildedgames.the_aether.entities.block.EntityFloatingBlock;

public class FloatingBlockRenderer extends Render {

    private final RenderBlocks renderBlocks = new RenderBlocks();

    public FloatingBlockRenderer() {
        super();
        this.shadowSize = 0.5F;
    }

    public void renderFloatingBlock(EntityFloatingBlock entityFloatingBlock, double d, double d1, double d2, float f, float f1) {
        if (entityFloatingBlock.getBlock() == null || entityFloatingBlock.getBlock() == Blocks.air) {
            return;
        }

        this.bindTexture(TextureMap.locationBlocksTexture);
        Block block = entityFloatingBlock.getBlock();
        World world = entityFloatingBlock.worldObj;
        int i = MathHelper.floor_double(entityFloatingBlock.posX);
        int j = MathHelper.floor_double(entityFloatingBlock.posY);
        int k = MathHelper.floor_double(entityFloatingBlock.posZ);

        if (block != world.getBlock(i, j, k)) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) d, (float) d1 + 0.48F, (float) d2);
            this.bindEntityTexture(entityFloatingBlock);
            GL11.glDisable(GL11.GL_LIGHTING);

            this.renderBlocks.setRenderBoundsFromBlock(block);
            this.renderBlocks.renderBlockSandFalling(block, world, i, j, k, entityFloatingBlock.getMetadata());

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        this.renderFloatingBlock((EntityFloatingBlock) entity, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TextureMap.locationBlocksTexture;
    }

}