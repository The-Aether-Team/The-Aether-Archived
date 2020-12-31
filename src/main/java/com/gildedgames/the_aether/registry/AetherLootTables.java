package com.gildedgames.the_aether.registry;

import com.gildedgames.the_aether.Aether;

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
	public static ResourceLocation sheepuff_white = register("entities/sheepuff/white");
	public static ResourceLocation sheepuff_orange = register("entities/sheepuff/orange");
	public static ResourceLocation sheepuff_magenta = register("entities/sheepuff/magenta");
	public static ResourceLocation sheepuff_light_blue = register("entities/sheepuff/light_blue");
	public static ResourceLocation sheepuff_yellow = register("entities/sheepuff/yellow");
	public static ResourceLocation sheepuff_lime = register("entities/sheepuff/lime");
	public static ResourceLocation sheepuff_pink = register("entities/sheepuff/pink");
	public static ResourceLocation sheepuff_gray = register("entities/sheepuff/gray");
	public static ResourceLocation sheepuff_silver = register("entities/sheepuff/silver");
	public static ResourceLocation sheepuff_cyan = register("entities/sheepuff/cyan");
	public static ResourceLocation sheepuff_purple = register("entities/sheepuff/purple");
	public static ResourceLocation sheepuff_blue = register("entities/sheepuff/blue");
	public static ResourceLocation sheepuff_brown = register("entities/sheepuff/brown");
	public static ResourceLocation sheepuff_green = register("entities/sheepuff/green");
	public static ResourceLocation sheepuff_red = register("entities/sheepuff/red");
	public static ResourceLocation sheepuff_black = register("entities/sheepuff/black");
	public static ResourceLocation swet_blue = register("entities/swet/blue");
	public static ResourceLocation swet_gold = register("entities/swet/gold");
	
	// Monsters
	public static ResourceLocation cockatrice = register("entities/cockatrice");
	public static ResourceLocation aechor_plant = register("entities/aechor_plant");
	public static ResourceLocation zephyr = register("entities/zephyr");
	public static ResourceLocation valkyrie = register("entities/valkyrie");
	public static ResourceLocation fire_minion = register("entities/fire_minion");
	public static ResourceLocation chest_mimic = register("entities/chest_mimic");
	
	// Bosses
	public static ResourceLocation slider = register("entities/bosses/slider");
	public static ResourceLocation valkyrie_queen = register("entities/bosses/valkyrie_queen");
	public static ResourceLocation sun_spirit = register("entities/bosses/sun_spirit");

	// Chests
	public static ResourceLocation bronze_dungeon_chest = register("chests/bronze_dungeon_chest");
	public static ResourceLocation bronze_dungeon_chest_sub0 = register("chests/bronze_dungeon_chest_sub0");
	public static ResourceLocation bronze_dungeon_chest_sub1 = register("chests/bronze_dungeon_chest_sub1");
	public static ResourceLocation bronze_dungeon_chest_sub2 = register("chests/bronze_dungeon_chest_sub2");
	public static ResourceLocation bronze_dungeon_chest_sub3 = register("chests/bronze_dungeon_chest_sub3");
	public static ResourceLocation silver_dungeon_chest = register("chests/silver_dungeon_chest");
	public static ResourceLocation silver_dungeon_chest_sub0 = register("chests/silver_dungeon_chest_sub0");
	public static ResourceLocation silver_dungeon_chest_sub1 = register("chests/silver_dungeon_chest_sub1");
	public static ResourceLocation silver_dungeon_chest_sub2 = register("chests/silver_dungeon_chest_sub2");
	public static ResourceLocation silver_dungeon_chest_sub3 = register("chests/silver_dungeon_chest_sub3");
	public static ResourceLocation silver_dungeon_chest_sub4 = register("chests/silver_dungeon_chest_sub4");
	public static ResourceLocation silver_dungeon_chest_sub5 = register("chests/silver_dungeon_chest_sub5");
	public static ResourceLocation bronze_dungeon_reward = register("chests/bronze_dungeon_reward");
	public static ResourceLocation silver_dungeon_reward = register("chests/silver_dungeon_reward");
	public static ResourceLocation silver_dungeon_reward_sub0 = register("chests/silver_dungeon_reward_sub0");
	public static ResourceLocation gold_dungeon_reward = register("chests/gold_dungeon_reward");
	public static ResourceLocation gold_dungeon_reward_sub0 = register("chests/gold_dungeon_reward_sub0");
	public static ResourceLocation gold_dungeon_reward_sub1 = register("chests/gold_dungeon_reward_sub1");
	public static ResourceLocation gold_dungeon_reward_sub2 = register("chests/gold_dungeon_reward_sub2");
	public static ResourceLocation gold_dungeon_reward_sub3 = register("chests/gold_dungeon_reward_sub3");
	public static ResourceLocation gold_dungeon_reward_sub4 = register("chests/gold_dungeon_reward_sub4");

	private static ResourceLocation register(String location)
    {
        return LootTableList.register(Aether.locate(location));
    }
}