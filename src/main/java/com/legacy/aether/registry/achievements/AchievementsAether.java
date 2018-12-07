package com.legacy.aether.registry.achievements;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.items.ItemsAether;

public class AchievementsAether {

	public static Achievement enter_aether, defeat_bronze, defeat_silver, defeat_gold, enchanter, incubator, grav_tools, blue_cloud, flying_pig, loreception;

	public static AchievementPage ACpage;

	public static void initialization() {
		enter_aether = new AetherAchievement("achievement.enter_aether", "enter_aether", 0, 1, Blocks.glowstone, (Achievement) null).registerStat();
		defeat_bronze = new AetherAchievement("achievement.bronze_dungeon", "bronze_dungeon", -2, 3, new ItemStack(ItemsAether.dungeon_key, 1, 0), enter_aether).registerStat();
		defeat_silver = new AetherAchievement("achievement.silver_dungeon", "silver_dungeon", 0, 4, new ItemStack(ItemsAether.dungeon_key, 1, 1), enter_aether).registerStat();
		defeat_gold = new AetherAchievement("achievement.gold_dungeon", "gold_dungeon", 2, 3, new ItemStack(ItemsAether.dungeon_key, 1, 2), enter_aether).registerStat();
		enchanter = new AetherAchievement("achievement.enchanter", "enchanter", 2, 1, BlocksAether.enchanter, enter_aether).registerStat();
		incubator = new AetherAchievement("achievement.incubator", "incubator", 2, -1, BlocksAether.incubator, enter_aether).registerStat();
		blue_cloud = new AetherAchievement("achievement.blue_aercloud", "blue_aercloud", -2, -1, new ItemStack(BlocksAether.aercloud, 1, 1), enter_aether).registerStat();
		flying_pig = new AetherAchievement("achievement.mount_phyg", "mount_phyg", -2, 1, Items.saddle, enter_aether).registerStat();
		grav_tools = new AetherAchievement("achievement.gravitite_tools", "gravitite_tools", -1, -3, ItemsAether.gravitite_pickaxe, enter_aether).registerStat();
		loreception = new AetherAchievement("achievement.loreception", "loreception", 1, -3, ItemsAether.lore_book, enter_aether).registerStat();

		ACpage = new AchievementPage("Aether I", enter_aether, defeat_bronze, defeat_silver, defeat_gold, enchanter, incubator, blue_cloud, flying_pig, grav_tools, loreception);

		AchievementPage.registerAchievementPage(ACpage);
	}

}