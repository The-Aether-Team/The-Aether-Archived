package com.gildedgames.the_aether.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;

public class AetherTileEntities {

	public static void initialization() {
		GameRegistry.registerTileEntity(TileEntityEnchanter.class, "enchanter");
		GameRegistry.registerTileEntity(TileEntityFreezer.class, "freezer");
		GameRegistry.registerTileEntity(TileEntityIncubator.class, "incubator");
		GameRegistry.registerTileEntity(TileEntityTreasureChest.class, "treasure_chest");
		GameRegistry.registerTileEntity(TileEntityChestMimic.class, "chest_mimic");
	}

}