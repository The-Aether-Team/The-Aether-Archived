package com.legacy.aether.client.renders;

import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityChest;

import com.legacy.aether.tile_entities.TileEntityChestMimic;

public class ChestMimicRenderer extends TileEntityChestRenderer
{

    public void renderTileEntityAt(TileEntityChest te, double x, double y, double z, float partialTicks, int destroyStage)
    {
    	if (te == null)
    	{
    		TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileEntityChestMimic(), 0, 0, 0, 0);
    		return;
    	}
    	
    	super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);
    }

}