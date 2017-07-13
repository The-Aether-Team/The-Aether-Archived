package com.legacy.aether.common.tile_entities;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class AetherTileEntities 
{

	public static void initialization()
	{
		GameRegistry.registerTileEntityWithAlternatives(TileEntityEnchanter.class, "enchanter", "aether_legacy_enchanter");
		GameRegistry.registerTileEntityWithAlternatives(TileEntityFreezer.class, "freezer", "aether_legacy_freezer");
		GameRegistry.registerTileEntityWithAlternatives(TileEntityIncubator.class, "incubator", "aether_legacy_incubator");
		GameRegistry.registerTileEntityWithAlternatives(TileEntityTreasureChest.class, "treasure_chest", "aether_legacy_treasure_chest");
		GameRegistry.registerTileEntityWithAlternatives(TileEntityChestMimic.class, "chest_mimic", "aether_legacy_chest_mimic");
	}

}