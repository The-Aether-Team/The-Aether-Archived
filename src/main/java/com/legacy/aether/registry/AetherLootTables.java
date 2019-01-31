package com.legacy.aether.registry;

import com.legacy.aether.Aether;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class AetherLootTables
{
	// Animals
	public static ResourceLocation phyg = register("entities/phyg");
	public static ResourceLocation flying_cow = register("entities/flying_cow");
	public static ResourceLocation moa = register("entities/moa");
	public static ResourceLocation aerbunny = register("entities/aerbunny");
	public static ResourceLocation aerwhale = register("entities/aerwhale");
	public static ResourceLocation sheepuff = register("entities/sheepuff");
	
	// Monsters
	public static ResourceLocation cockatrice = register("entities/cockatrice");
	public static ResourceLocation aechor_plant = register("entities/aechor_plant");
	public static ResourceLocation zephyr = register("entities/zephyr");
	public static ResourceLocation swet = register("entities/swet");
	public static ResourceLocation sentry = register("entities/sentry");
	public static ResourceLocation valkyrie = register("entities/valkyrie");
	public static ResourceLocation fire_minion = register("entities/fire_minion");
	public static ResourceLocation chest_mimic = register("entities/chest_mimic");
	
	// Bosses
	public static ResourceLocation slider = register("entities/bosses/slider");
	public static ResourceLocation valkyrie_queen = register("entities/bosses/valkyrie_queen");
	public static ResourceLocation sun_spirit = register("entities/bosses/sun_spirit");

	// Chests
	public static ResourceLocation bronze_dungeon = register("chests/bronze_dungeon");
	public static ResourceLocation silver_dungeon = register("chests/silver_dungeon");
	public static ResourceLocation gold_dungeon = register("chests/gold_dungeon");

	private static ResourceLocation register(String location)
    {
        return LootTableList.register(Aether.locate(location));
    }
}