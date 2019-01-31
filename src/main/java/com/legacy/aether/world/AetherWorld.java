package com.legacy.aether.world;

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

import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DimensionManager;

public class AetherWorld
{

	public static Biome aether_biome = new AetherBiome();

	public static DimensionType aether_dimension_type;

	public static void initialization()
	{
		MapGenStructureIO.registerStructure(MapGenSilverDungeon.Start.class, "aether_legacy:silver_dungeon_start");
		MapGenStructureIO.registerStructure(MapGenGoldenDungeon.Start.class, "aether_legacy:golden_dungeon_start");

		MapGenStructureIO.registerStructure(MapGenLargeColdAercloud.Start.class, "aether_legacy:large_cold_aercloud_start");

		MapGenStructureIO.registerStructureComponent(ComponentLargeColdAercloud.class, "aether_legacy:large_cold_aercloud_component");

		MapGenStructureIO.registerStructureComponent(ComponentSilverDungeon.class, "aether_legacy:silver_dungeon_component");

		MapGenStructureIO.registerStructureComponent(ComponentGoldenDungeon.class, "aether_legacy:golden_dungeon_component");
		MapGenStructureIO.registerStructureComponent(ComponentGoldenIsland.class, "aether_legacy:golden_island_component");
		MapGenStructureIO.registerStructureComponent(ComponentGoldenIslandStub.class, "aether_legacy:golden_island_stub_component");

		aether_dimension_type = DimensionType.register("AetherI", "_aetherI", AetherConfig.dimension.aether_dimension_id, AetherWorldProvider.class, false);

		DimensionManager.registerDimension(AetherConfig.dimension.aether_dimension_id, aether_dimension_type);
		
		BiomeDictionary.addTypes(aether_biome, 
                BiomeDictionary.Type.VOID,
                BiomeDictionary.Type.COLD,
                BiomeDictionary.Type.MAGICAL
                );
	}

}