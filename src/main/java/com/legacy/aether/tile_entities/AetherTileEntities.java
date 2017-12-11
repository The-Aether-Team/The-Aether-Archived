package com.legacy.aether.tile_entities;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class AetherTileEntities 
{

	public static void initialization()
	{
		GameRegistry.registerTileEntity(TileEntityEnchanter.class, "aether_legacy_enchanter");
		GameRegistry.registerTileEntity(TileEntityFreezer.class, "aether_legacy_freezer");
		GameRegistry.registerTileEntity(TileEntityIncubator.class, "aether_legacy_incubator");
		GameRegistry.registerTileEntity(TileEntityTreasureChest.class, "aether_legacy_treasure_chest");
		GameRegistry.registerTileEntity(TileEntityChestMimic.class, "aether_legacy_chest_mimic");
	}

}