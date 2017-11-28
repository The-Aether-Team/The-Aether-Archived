package com.legacy.aether.client.renders;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.legacy.aether.blocks.dungeon.BlockTreasureChest;
import com.legacy.aether.tile_entities.TileEntityTreasureChest;

public class TreasureChestRenderer extends TileEntitySpecialRenderer<TileEntityTreasureChest>
{

	private static final ResourceLocation TEXTURE_DOUBLE = new ResourceLocation("aether_legacy", "textures/tile_entities/treasure_chest_large.png");

	private static final ResourceLocation TEXTURE_SINGLE = new ResourceLocation("aether_legacy", "textures/tile_entities/treasure_chest.png");

	private final ModelChest chestModel = new ModelChest();

	private final ModelChest largeChestModel = new ModelLargeChest();

	@Override
	public void renderTileEntityAt(@Nullable TileEntityTreasureChest par1TileEntityChest, double posX, double posZ, double par7, float par8, int p_180535_9_) 
	{
		int var9;

		if (par1TileEntityChest == null)
		{
			TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityTreasureChest(), 0.0, 0.0, 0.0, 0f);
            return;
		}

		if (!par1TileEntityChest.hasWorldObj())
		{
			var9 = 0;
		}
		else
		{
			Block var10 = par1TileEntityChest.getBlockType();
			var9 = par1TileEntityChest.getBlockMetadata();

			if (var10 != null && var10 instanceof BlockTreasureChest)
			{
				((BlockTreasureChest) var10).checkForSurroundingChests(par1TileEntityChest.getWorld(), par1TileEntityChest.getPos(), par1TileEntityChest.getWorld().getBlockState(par1TileEntityChest.getPos()));
				var9 = par1TileEntityChest.getBlockMetadata();
			}

			par1TileEntityChest.checkForAdjacentChests();
		}

		if (par1TileEntityChest.adjacentChestZNeg == null && par1TileEntityChest.adjacentChestXNeg == null)
		{
			ModelChest var14;

			if (par1TileEntityChest.adjacentChestXPos == null && par1TileEntityChest.adjacentChestZPos == null)
			{
				var14 = this.chestModel;
				this.bindTexture(TEXTURE_SINGLE);
			}
			else
			{
				var14 = this.largeChestModel;
				this.bindTexture(TEXTURE_DOUBLE);
			}

			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslatef((float) posX, (float) posZ + 1.0F, (float) par7 + 1.0F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			short var11 = 0;

			if (var9 == 2)
			{
				var11 = 180;
			}

			if (var9 == 3)
			{
				var11 = 0;
			}

			if (var9 == 4)
			{
				var11 = 90;
			}

			if (var9 == 5)
			{
				var11 = -90;
			}

			if (var9 == 2 && par1TileEntityChest.adjacentChestXPos != null)
			{
				GL11.glTranslatef(1.0F, 0.0F, 0.0F);
			}

			if (var9 == 5 && par1TileEntityChest.adjacentChestZPos != null)
			{
				GL11.glTranslatef(0.0F, 0.0F, -1.0F);
			}

			GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			float var12 = par1TileEntityChest.prevLidAngle + (par1TileEntityChest.lidAngle - par1TileEntityChest.prevLidAngle) * par8;
			float var13;

			if (par1TileEntityChest.adjacentChestZNeg != null)
			{
				var13 = par1TileEntityChest.adjacentChestZNeg.prevLidAngle + (par1TileEntityChest.adjacentChestZNeg.lidAngle - par1TileEntityChest.adjacentChestZNeg.prevLidAngle) * par8;

				if (var13 > var12)
				{
					var12 = var13;
				}
			}

			if (par1TileEntityChest.adjacentChestXNeg != null)
			{
				var13 = par1TileEntityChest.adjacentChestXNeg.prevLidAngle + (par1TileEntityChest.adjacentChestXNeg.lidAngle - par1TileEntityChest.adjacentChestXNeg.prevLidAngle) * par8;

				if (var13 > var12)
				{
					var12 = var13;
				}
			}

			var12 = 1.0F - var12;
			var12 = 1.0F - var12 * var12 * var12;
			var14.chestLid.rotateAngleX = -(var12 * (float) Math.PI / 2.0F);
			var14.renderAll();
			GL11.glPopMatrix();
		}
	}

}
