package com.legacy.aether.client.renders.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.renders.items.definitions.NotchHammerDefinition;
import com.legacy.aether.client.renders.items.definitions.PhoenixBowDefinition;
import com.legacy.aether.client.renders.items.util.AetherColor;
import com.legacy.aether.common.Aether;
import com.legacy.aether.common.entities.util.MoaColor;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.items.util.EnumDartShooterType;
import com.legacy.aether.common.items.util.EnumDartType;
import com.legacy.aether.common.items.util.EnumDungeonKeyType;
import com.legacy.aether.common.items.util.EnumGummySwetType;
import com.legacy.aether.common.items.util.EnumSkyrootBucketType;

public class ItemRendering
{

	public static void initialize()
	{
		register(ItemsAether.zanite_gemstone, "zanite_gemstone");
		register(ItemsAether.ambrosium_shard, "ambrosium_shard");
		register(ItemsAether.golden_amber, "golden_amber");
		register(ItemsAether.aechor_petal, "aechor_petal");
		register(ItemsAether.skyroot_stick, "skyroot_stick");
		register(ItemsAether.swetty_ball, "swetty_ball");

		register(ItemsAether.skyroot_pickaxe, "skyroot_pickaxe");
		register(ItemsAether.skyroot_axe, "skyroot_axe");
		register(ItemsAether.skyroot_shovel, "skyroot_shovel");
		register(ItemsAether.holystone_pickaxe, "holystone_pickaxe");
		register(ItemsAether.holystone_axe, "holystone_axe");
		register(ItemsAether.holystone_shovel, "holystone_shovel");
		register(ItemsAether.zanite_pickaxe, "zanite_pickaxe");
		register(ItemsAether.zanite_axe, "zanite_axe");
		register(ItemsAether.zanite_shovel, "zanite_shovel");
		register(ItemsAether.gravitite_pickaxe, "gravitite_pickaxe");
		register(ItemsAether.gravitite_axe, "gravitite_axe");
		register(ItemsAether.gravitite_shovel, "gravitite_shovel");
		register(ItemsAether.valkyrie_pickaxe, "valkyrie_pickaxe");
		register(ItemsAether.valkyrie_axe, "valkyrie_axe");
		register(ItemsAether.valkyrie_shovel, "valkyrie_shovel");

		register(ItemsAether.zanite_helmet, "zanite_helmet");
		registerColor(ItemsAether.zanite_helmet);

		register(ItemsAether.zanite_chestplate, "zanite_chestplate");
		registerColor(ItemsAether.zanite_chestplate);

		register(ItemsAether.zanite_leggings, "zanite_leggings");
		registerColor(ItemsAether.zanite_leggings);

		register(ItemsAether.zanite_boots, "zanite_boots");
		registerColor(ItemsAether.zanite_boots);

		register(ItemsAether.gravitite_helmet, "gravitite_helmet");
		registerColor(ItemsAether.gravitite_helmet);

		register(ItemsAether.gravitite_chestplate, "gravitite_chestplate");
		registerColor(ItemsAether.gravitite_chestplate);

		register(ItemsAether.gravitite_leggings, "gravitite_leggings");
		registerColor(ItemsAether.gravitite_leggings);

		register(ItemsAether.gravitite_boots, "gravitite_boots");
		registerColor(ItemsAether.gravitite_boots);

		register(ItemsAether.neptune_helmet, "neptune_helmet");
		registerColor(ItemsAether.neptune_helmet);

		register(ItemsAether.neptune_chestplate, "neptune_chestplate");
		registerColor(ItemsAether.neptune_chestplate);

		register(ItemsAether.neptune_leggings, "neptune_leggings");
		registerColor(ItemsAether.neptune_leggings);

		register(ItemsAether.neptune_boots, "neptune_boots");
		registerColor(ItemsAether.neptune_boots);

		register(ItemsAether.phoenix_helmet, "phoenix_helmet");
		registerColor(ItemsAether.phoenix_helmet);

		register(ItemsAether.phoenix_chestplate, "phoenix_chestplate");
		registerColor(ItemsAether.phoenix_chestplate);

		register(ItemsAether.phoenix_leggings, "phoenix_leggings");
		registerColor(ItemsAether.phoenix_leggings);

		register(ItemsAether.phoenix_boots, "phoenix_boots");
		registerColor(ItemsAether.phoenix_boots);

		register(ItemsAether.valkyrie_helmet, "valkyrie_helmet");
		registerColor(ItemsAether.valkyrie_helmet);

		register(ItemsAether.valkyrie_chestplate, "valkyrie_chestplate");
		registerColor(ItemsAether.valkyrie_chestplate);

		register(ItemsAether.valkyrie_leggings, "valkyrie_leggings");
		registerColor(ItemsAether.valkyrie_leggings);

		register(ItemsAether.valkyrie_boots, "valkyrie_boots");
		registerColor(ItemsAether.valkyrie_boots);

		register(ItemsAether.obsidian_helmet, "obsidian_helmet");
		registerColor(ItemsAether.obsidian_helmet);

		register(ItemsAether.obsidian_chestplate, "obsidian_chestplate");
		registerColor(ItemsAether.obsidian_chestplate);

		register(ItemsAether.obsidian_leggings, "obsidian_leggings");
		registerColor(ItemsAether.obsidian_leggings);

		register(ItemsAether.obsidian_boots, "obsidian_boots");
		registerColor(ItemsAether.obsidian_boots);

		register(ItemsAether.blue_berry, "blue_berry");
		register(ItemsAether.white_apple, "white_apple");
		register(ItemsAether.healing_stone, "healing_stone");
		register(ItemsAether.candy_cane, "candy_cane");
		register(ItemsAether.ginger_bread_man, "ginger_bread_man");
		register(ItemsAether.enchanted_blueberry, "enchanted_blueberry");

		register(ItemsAether.skyroot_sword, "skyroot_sword");
		register(ItemsAether.holystone_sword, "holystone_sword");
		register(ItemsAether.zanite_sword, "zanite_sword");
		register(ItemsAether.gravitite_sword, "gravitite_sword");

		register(ItemsAether.leather_gloves, "leather_gloves");
		registerColor(ItemsAether.leather_gloves);

		register(ItemsAether.iron_gloves, "iron_gloves");
		registerColor(ItemsAether.iron_gloves);

		register(ItemsAether.golden_gloves, "golden_gloves");
		registerColor(ItemsAether.golden_gloves);

		register(ItemsAether.chain_gloves, "chain_gloves");
		registerColor(ItemsAether.chain_gloves);

		register(ItemsAether.diamond_gloves, "diamond_gloves");
		registerColor(ItemsAether.diamond_gloves);

		register(ItemsAether.zanite_gloves, "zanite_gloves");
		registerColor(ItemsAether.zanite_gloves);

		register(ItemsAether.gravitite_gloves, "gravitite_gloves");
		registerColor(ItemsAether.gravitite_gloves);

		register(ItemsAether.neptune_gloves, "neptune_gloves");
		registerColor(ItemsAether.neptune_gloves);

		register(ItemsAether.phoenix_gloves, "phoenix_gloves");
		registerColor(ItemsAether.phoenix_gloves);

		register(ItemsAether.obsidian_gloves, "obsidian_gloves");
		registerColor(ItemsAether.obsidian_gloves);

		register(ItemsAether.valkyrie_gloves, "valkyrie_gloves");
		registerColor(ItemsAether.valkyrie_gloves);

		register(ItemsAether.iron_ring, "iron_ring");
		registerColor(ItemsAether.iron_ring);

		register(ItemsAether.golden_ring, "golden_ring");
		registerColor(ItemsAether.golden_ring);

		register(ItemsAether.zanite_ring, "zanite_ring");
		registerColor(ItemsAether.zanite_ring);

		register(ItemsAether.ice_ring, "ice_ring");
		registerColor(ItemsAether.ice_ring);

		register(ItemsAether.iron_pendant, "iron_pendant");
		registerColor(ItemsAether.iron_pendant);

		register(ItemsAether.golden_pendant, "golden_pendant");
		registerColor(ItemsAether.golden_pendant);

		register(ItemsAether.zanite_pendant, "zanite_pendant");
		registerColor(ItemsAether.zanite_pendant);

		register(ItemsAether.ice_pendant, "ice_pendant");
		registerColor(ItemsAether.ice_pendant);

		register(ItemsAether.red_cape, "red_cape");
		registerColor(ItemsAether.red_cape);

		register(ItemsAether.blue_cape, "blue_cape");
		registerColor(ItemsAether.blue_cape);

		register(ItemsAether.yellow_cape, "yellow_cape");
		registerColor(ItemsAether.yellow_cape);

		register(ItemsAether.swet_cape, "swet_cape");

		register(ItemsAether.white_cape, "white_cape");
		registerColor(ItemsAether.white_cape);

		register(ItemsAether.agility_cape, "agility_cape");
		register(ItemsAether.invisibility_cape, "invisibility_cape");

		register(ItemsAether.iron_bubble, "iron_bubble");
		register(ItemsAether.regeneration_stone, "regeneration_stone");
		register(ItemsAether.golden_feather, "golden_feather");
		register(ItemsAether.life_shard, "life_shard");

		register(ItemsAether.lightning_sword, "lightning_sword");
		register(ItemsAether.flaming_sword, "flaming_sword");
		register(ItemsAether.holy_sword, "holy_sword");
		register(ItemsAether.vampire_blade, "vampire_blade");
		register(ItemsAether.pig_slayer, "pig_slayer");
		register(ItemsAether.candy_cane_sword, "candy_cane_sword");

		register(ItemsAether.victory_medal, "victory_medal");

		register(ItemsAether.cloud_staff, "cloud_staff");
		register(ItemsAether.nature_staff, "nature_staff");

		register(ItemsAether.lightning_knife, "lightning_knife");
		register(ItemsAether.valkyrie_lance, "valkyrie_lance");

		register(ItemsAether.sentry_boots, "sentry_boots");

		register(ItemsAether.aether_tune, "aether_tune");
		register(ItemsAether.ascending_dawn, "ascending_dawn");
		register(ItemsAether.welcoming_skies, "welcoming_skies");

		register(ItemsAether.repulsion_shield, "repulsion_shield");

		register(ItemsAether.cloud_parachute, "cold_parachute");
		register(ItemsAether.golden_parachute, "golden_parachute");
		register(ItemsAether.lore_book, "lore_book");

		registerDefinition(ItemsAether.phoenix_bow, new PhoenixBowDefinition());
		registerDefinition(ItemsAether.notch_hammer, new NotchHammerDefinition());

		for (int meta = 0; meta < EnumGummySwetType.values().length; ++meta)
		{
			register(ItemsAether.gummy_swet, meta, EnumGummySwetType.values()[meta].toString() + "_gummy_swet");
		}

		for (int meta = 0; meta < EnumDungeonKeyType.values().length; ++meta)
		{
			register(ItemsAether.dungeon_key, meta, EnumDungeonKeyType.values()[meta].toString() + "_key");
		}

		for (int meta = 0; meta < EnumSkyrootBucketType.values().length; ++meta)
		{
			String base = EnumSkyrootBucketType.values()[meta].toString();
			String name = base == "empty" ? "skyroot_bucket" : "skyroot_" + base + "_bucket";
			register(ItemsAether.skyroot_bucket, meta, name);
		}

		for (int meta = 0; meta < MoaColor.colors.size(); ++meta)
		{
			register(ItemsAether.moa_egg, meta, "moa_egg");
		}

		registerColor(ItemsAether.moa_egg);

		for (int meta = 0; meta < EnumDartShooterType.values().length; ++meta)
		{
			register(ItemsAether.dart_shooter, meta, EnumDartShooterType.values()[meta].toString() + "_dart_shooter");
		}

		for (int meta = 0; meta < EnumDartType.values().length; ++meta)
		{
			register(ItemsAether.dart, meta, EnumDartType.values()[meta].toString() + "_dart");
		}

		registerMeta(ItemsAether.gummy_swet, Aether.locate("blue_gummy_swet"), Aether.locate("golden_gummy_swet"));
		registerMeta(ItemsAether.dungeon_key, Aether.locate("bronze_key"), Aether.locate("silver_key"), Aether.locate("golden_key"));
		registerMeta(ItemsAether.skyroot_bucket, Aether.locate("skyroot_bucket"), Aether.locate("skyroot_water_bucket"), Aether.locate("skyroot_poison_bucket"), Aether.locate("skyroot_remedy_bucket"), Aether.locate("skyroot_milk_bucket"));
		registerMeta(ItemsAether.dart_shooter, Aether.locate("golden_dart_shooter"), Aether.locate("poison_dart_shooter"), Aether.locate("enchanted_dart_shooter"));
		registerMeta(ItemsAether.dart, Aether.locate("golden_dart"), Aether.locate("poison_dart"), Aether.locate("enchanted_dart"));
		registerMeta(ItemsAether.phoenix_bow, Aether.locate("phoenix_bow"), Aether.locate("phoenix_bow_pulling_0"), Aether.locate("phoenix_bow_pulling_1"), Aether.locate("phoenix_bow_pulling_2"));
		registerMeta(ItemsAether.notch_hammer, Aether.locate("notch_hammer"), Aether.locate("hammer_projectile"));
	}

	public static void register(Item item, int meta, String model)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(Aether.modAddress() + model, "inventory"));
	}

	public static void register(Item item, String model)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Aether.modAddress() + model, "inventory"));
	}

	public static void registerDefinition(Item item, ItemMeshDefinition definition)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, definition);
	}

	public static void registerColor(Item item)
	{
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new AetherColor(item), item);
	}

	public static void registerMeta(Item item, ResourceLocation... model)
	{
		ModelBakery.registerItemVariants(item, model);
	}

}