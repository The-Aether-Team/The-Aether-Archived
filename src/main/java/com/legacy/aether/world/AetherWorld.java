package com.legacy.aether.world;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.DimensionManager;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.world.biome.AetherBiome;
import com.legacy.aether.world.gen.MapGenGoldenDungeon;
import com.legacy.aether.world.gen.MapGenLargeColdAercloud;
import com.legacy.aether.world.gen.MapGenSilverDungeon;
import com.legacy.aether.world.gen.components.ComponentGoldenDungeon;
import com.legacy.aether.world.gen.components.ComponentGoldenIsland;
import com.legacy.aether.world.gen.components.ComponentGoldenIslandStub;
import com.legacy.aether.world.gen.components.ComponentLargeColdAercloud;
import com.legacy.aether.world.gen.components.ComponentSilverDungeon;

public class AetherWorld {

	public static BiomeGenBase aether_biome = new AetherBiome();

	public static void initialization() {
		MapGenStructureIO.registerStructure(MapGenSilverDungeon.Start.class, "aether_legacy:silver_dungeon_start");
		MapGenStructureIO.registerStructure(MapGenGoldenDungeon.Start.class, "aether_legacy:golden_dungeon_start");

		MapGenStructureIO.registerStructure(MapGenLargeColdAercloud.Start.class, "aether_legacy:large_cold_aercloud_start");

		MapGenStructureIO.func_143031_a(ComponentLargeColdAercloud.class, "aether_legacy:large_cold_aercloud_component");

		MapGenStructureIO.func_143031_a(ComponentSilverDungeon.class, "aether_legacy:silver_dungeon_component");

		MapGenStructureIO.func_143031_a(ComponentGoldenDungeon.class, "aether_legacy:golden_dungeon_component");
		MapGenStructureIO.func_143031_a(ComponentGoldenIsland.class, "aether_legacy:golden_island_component");
		MapGenStructureIO.func_143031_a(ComponentGoldenIslandStub.class, "aether_legacy:golden_island_stub_component");

		DimensionManager.registerProviderType(AetherConfig.getAetherDimensionID(), AetherWorldProvider.class, false);
		DimensionManager.registerDimension(AetherConfig.getAetherDimensionID(), AetherConfig.getAetherDimensionID());
		//aether_dimension_type = DimensionType.register("AetherI", "_aetherI", AetherConfig.getAetherDimensionID(), AetherWorldProvider.class, false);
	}

}