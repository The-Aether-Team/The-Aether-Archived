package com.gildedgames.the_aether.registry;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.registry.recipe.RecipeAccessoryDyes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.api.accessories.AetherAccessory;
import com.gildedgames.the_aether.api.enchantments.AetherEnchantment;
import com.gildedgames.the_aether.api.enchantments.AetherEnchantmentFuel;
import com.gildedgames.the_aether.api.freezables.AetherFreezable;
import com.gildedgames.the_aether.api.freezables.AetherFreezableFuel;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class AetherRegistries {

	public static void initializeAccessories() {
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.leather_gloves, AccessoryType.GLOVES));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.iron_gloves, AccessoryType.GLOVES));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.golden_gloves, AccessoryType.GLOVES));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.chain_gloves, AccessoryType.GLOVES));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.diamond_gloves, AccessoryType.GLOVES));

		AetherAPI.instance().register(new AetherAccessory(ItemsAether.zanite_gloves, AccessoryType.GLOVES));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.gravitite_gloves, AccessoryType.GLOVES));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.neptune_gloves, AccessoryType.GLOVES));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.phoenix_gloves, AccessoryType.GLOVES));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.obsidian_gloves, AccessoryType.GLOVES));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.valkyrie_gloves, AccessoryType.GLOVES));

		AetherAPI.instance().register(new AetherAccessory(ItemsAether.iron_ring, AccessoryType.RING));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.golden_ring, AccessoryType.RING));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.zanite_ring, AccessoryType.RING));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.ice_ring, AccessoryType.RING));

		AetherAPI.instance().register(new AetherAccessory(ItemsAether.iron_pendant, AccessoryType.PENDANT));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.golden_pendant, AccessoryType.PENDANT));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.zanite_pendant, AccessoryType.PENDANT));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.ice_pendant, AccessoryType.PENDANT));

		AetherAPI.instance().register(new AetherAccessory(ItemsAether.red_cape, AccessoryType.CAPE));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.blue_cape, AccessoryType.CAPE));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.yellow_cape, AccessoryType.CAPE));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.white_cape, AccessoryType.CAPE));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.swet_cape, AccessoryType.CAPE));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.invisibility_cape, AccessoryType.CAPE));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.agility_cape, AccessoryType.CAPE));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.valkyrie_cape, AccessoryType.CAPE));

		AetherAPI.instance().register(new AetherAccessory(ItemsAether.golden_feather, AccessoryType.MISC));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.regeneration_stone, AccessoryType.MISC));
		AetherAPI.instance().register(new AetherAccessory(ItemsAether.iron_bubble, AccessoryType.MISC));

		AetherAPI.instance().register(new AetherAccessory(ItemsAether.repulsion_shield, AccessoryType.SHIELD));
	}

	public static void initializeEnchantments() {
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.skyroot_pickaxe, 225));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.skyroot_axe, 225));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.skyroot_shovel, 225));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.skyroot_sword, 225));

		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.holystone_pickaxe, 550));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.holystone_axe, 550));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.holystone_shovel, 550));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.holystone_sword, 550));

		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_pickaxe, 2250));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_axe, 2250));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_shovel, 2250));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_sword, 2250));

		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.gravitite_pickaxe, 5500));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.gravitite_axe, 5500));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.gravitite_shovel, 5500));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.gravitite_sword, 5500));

		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_helmet, 6000));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_chestplate, 6000));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_leggings, 6000));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_boots, 6000));

		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.gravitite_helmet, 13000));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.gravitite_chestplate, 13000));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.gravitite_leggings, 13000));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.gravitite_boots, 13000));

		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_ring, 2250));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.zanite_pendant, 2250));

		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.dart, new ItemStack(ItemsAether.dart, 1, 2), 250));
		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.dart_shooter, new ItemStack(ItemsAether.dart_shooter, 1, 2), 500));

		AetherAPI.instance().register(new AetherEnchantment(new ItemStack(ItemsAether.skyroot_bucket, 1, 2), new ItemStack(ItemsAether.skyroot_bucket.setContainerItem(null), 1, 3), 1000));

		AetherAPI.instance().register(new AetherEnchantment(BlocksAether.holystone, ItemsAether.healing_stone, 750));
		AetherAPI.instance().register(new AetherEnchantment(BlocksAether.gravitite_ore, BlocksAether.enchanted_gravitite, 1000));
		AetherAPI.instance().register(new AetherEnchantment(BlocksAether.quicksoil, BlocksAether.quicksoil_glass, 250));

		AetherAPI.instance().register(new AetherEnchantment(ItemsAether.blueberry, ItemsAether.enchanted_blueberry, 300));

		AetherAPI.instance().register(new AetherEnchantment(Items.bow, 4000));
		AetherAPI.instance().register(new AetherEnchantment(Items.fishing_rod, 600));

		AetherAPI.instance().register(new AetherEnchantment(Items.record_11, ItemsAether.aether_tune, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_13, ItemsAether.aether_tune, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_blocks, ItemsAether.aether_tune, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_cat, ItemsAether.legacy, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_far, ItemsAether.aether_tune, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_mall, ItemsAether.aether_tune, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_mellohi, ItemsAether.aether_tune, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_stal, ItemsAether.aether_tune, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_strad, ItemsAether.aether_tune, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_wait, ItemsAether.aether_tune, 2500));
		AetherAPI.instance().register(new AetherEnchantment(Items.record_ward, ItemsAether.aether_tune, 2500));

		AetherAPI.instance().register(new AetherEnchantment(Items.wooden_pickaxe, 225));
		AetherAPI.instance().register(new AetherEnchantment(Items.wooden_axe, 225));
		AetherAPI.instance().register(new AetherEnchantment(Items.wooden_shovel, 225));
		AetherAPI.instance().register(new AetherEnchantment(Items.wooden_hoe, 225));
		AetherAPI.instance().register(new AetherEnchantment(Items.wooden_sword, 225));

		AetherAPI.instance().register(new AetherEnchantment(Items.stone_pickaxe, 550));
		AetherAPI.instance().register(new AetherEnchantment(Items.stone_axe, 550));
		AetherAPI.instance().register(new AetherEnchantment(Items.stone_shovel, 550));
		AetherAPI.instance().register(new AetherEnchantment(Items.stone_hoe, 550));
		AetherAPI.instance().register(new AetherEnchantment(Items.stone_sword, 550));

		AetherAPI.instance().register(new AetherEnchantment(Items.iron_pickaxe, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.iron_axe, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.iron_shovel, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.iron_hoe, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.iron_sword, 550));

		AetherAPI.instance().register(new AetherEnchantment(Items.diamond_pickaxe, 5500));
		AetherAPI.instance().register(new AetherEnchantment(Items.diamond_axe, 5500));
		AetherAPI.instance().register(new AetherEnchantment(Items.diamond_shovel, 5500));
		AetherAPI.instance().register(new AetherEnchantment(Items.diamond_hoe, 5500));
		AetherAPI.instance().register(new AetherEnchantment(Items.diamond_sword, 5500));

		AetherAPI.instance().register(new AetherEnchantment(Items.leather_helmet, 550));
		AetherAPI.instance().register(new AetherEnchantment(Items.leather_chestplate, 550));
		AetherAPI.instance().register(new AetherEnchantment(Items.leather_leggings, 550));
		AetherAPI.instance().register(new AetherEnchantment(Items.leather_boots, 550));

		AetherAPI.instance().register(new AetherEnchantment(Items.iron_helmet, 6000));
		AetherAPI.instance().register(new AetherEnchantment(Items.iron_chestplate, 6000));
		AetherAPI.instance().register(new AetherEnchantment(Items.iron_leggings, 6000));
		AetherAPI.instance().register(new AetherEnchantment(Items.iron_boots, 6000));

		AetherAPI.instance().register(new AetherEnchantment(Items.golden_helmet, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.golden_chestplate, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.golden_leggings, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.golden_boots, 2250));

		AetherAPI.instance().register(new AetherEnchantment(Items.chainmail_helmet, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.chainmail_chestplate, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.chainmail_leggings, 2250));
		AetherAPI.instance().register(new AetherEnchantment(Items.chainmail_boots, 2250));

		AetherAPI.instance().register(new AetherEnchantment(Items.diamond_helmet, 10000));
		AetherAPI.instance().register(new AetherEnchantment(Items.diamond_chestplate, 10000));
		AetherAPI.instance().register(new AetherEnchantment(Items.diamond_leggings, 10000));
		AetherAPI.instance().register(new AetherEnchantment(Items.diamond_boots, 10000));
	}

	public static void initializeEnchantmentFuel() {
		AetherAPI.instance().register(new AetherEnchantmentFuel(ItemsAether.ambrosium_shard, 500));
	}

	public static void initializeFreezables() {
		AetherAPI.instance().register(new AetherFreezable(BlocksAether.aercloud, new ItemStack(BlocksAether.aercloud, 1, 1), 100));
		AetherAPI.instance().register(new AetherFreezable(BlocksAether.skyroot_leaves, BlocksAether.crystal_leaves, 150));
		AetherAPI.instance().register(new AetherFreezable(BlocksAether.golden_oak_leaves, BlocksAether.crystal_leaves, 150));
		AetherAPI.instance().register(new AetherFreezable(new ItemStack(ItemsAether.skyroot_bucket, 1, 1), Blocks.ice, 500));
		AetherAPI.instance().register(new AetherFreezable(ItemsAether.ascending_dawn, ItemsAether.welcoming_skies, 2500));
		AetherAPI.instance().register(new AetherFreezable(Blocks.ice, Blocks.packed_ice, 750));
		AetherAPI.instance().register(new AetherFreezable(Items.water_bucket, Blocks.ice, 500));
		AetherAPI.instance().register(new AetherFreezable(Items.lava_bucket, Blocks.obsidian, 500));
		AetherAPI.instance().register(new AetherFreezable(ItemsAether.iron_ring, ItemsAether.ice_ring, 2500));
		AetherAPI.instance().register(new AetherFreezable(ItemsAether.golden_ring, ItemsAether.ice_ring, 2500));
		AetherAPI.instance().register(new AetherFreezable(ItemsAether.iron_pendant, ItemsAether.ice_pendant, 2500));
		AetherAPI.instance().register(new AetherFreezable(ItemsAether.golden_pendant, ItemsAether.ice_pendant, 2500));
	}

	public static void initializeFreezableFuel() {
		AetherAPI.instance().register(new AetherFreezableFuel(BlocksAether.icestone, 500));
	}

	public static void register() {
		initializeAccessories();
		initializeEnchantments();
		initializeEnchantmentFuel();
		initializeFreezables();
		initializeFreezableFuel();

		initializeRecipes();
		initializeShapelessRecipes();

		GameRegistry.addRecipe(new RecipeAccessoryDyes());
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(BlocksAether.skyroot_log, 1, 1), new ItemStack(Items.coal, 1, 1), 0.15F);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(BlocksAether.golden_oak_log, 1, 1), new ItemStack(Items.coal, 1, 1), 0.15F);

		OreDictionary.registerOre("stickWood", ItemsAether.skyroot_stick);
	}

	private static void initializeShapelessRecipes() {
		registerShapeless("poison_dart_shooter", new ItemStack(ItemsAether.dart_shooter, 1, 1), new ItemStack(ItemsAether.dart_shooter, 1, 0), new ItemStack(ItemsAether.skyroot_bucket, 1, 2));
		registerShapeless("purple_dye", new ItemStack(Items.dye, 2, 5), BlocksAether.purple_flower);
		registerShapeless("skyroot_planks", new ItemStack(BlocksAether.skyroot_planks, 4), new ItemStack(BlocksAether.skyroot_log, 1, 1));
		registerShapeless("skyroot_planks", new ItemStack(BlocksAether.skyroot_planks, 4), new ItemStack(BlocksAether.golden_oak_log, 1, 1));
		registerShapeless("book_of_lore", new ItemStack(ItemsAether.lore_book), new ItemStack(Items.book), new ItemStack(ItemsAether.ambrosium_shard));
		registerShapeless("book_of_lore", new ItemStack(ItemsAether.lore_book), new ItemStack(Items.book), new ItemStack(Items.flint));
		registerShapeless("book_of_lore", new ItemStack(ItemsAether.lore_book), new ItemStack(Items.book), new ItemStack(Items.glowstone_dust));
	}

	private static void initializeRecipes() {
		register("nature_staf", new ItemStack(ItemsAether.nature_staff), "Y", "X", 'Y', ItemsAether.zanite_gemstone, 'X', ItemsAether.skyroot_stick);
		register("skyroot_stick", new ItemStack(ItemsAether.skyroot_stick, 4), "X", "X", 'X', BlocksAether.skyroot_planks);
		register("trapdoor", new ItemStack(Blocks.trapdoor, 2), "XXX", "XXX", 'X', BlocksAether.skyroot_planks);
		register("holystone_brick", new ItemStack(BlocksAether.holystone_brick, 4), "XX", "XX", 'X', BlocksAether.holystone);
		register("zanite_block", new ItemStack(BlocksAether.zanite_block), "XXX", "XXX", "XXX", 'X', ItemsAether.zanite_gemstone);
		register("zanite_gemstone", new ItemStack(ItemsAether.zanite_gemstone, 9), "X", 'X', BlocksAether.zanite_block);
		register("golden_dart_shooter", new ItemStack(ItemsAether.dart_shooter, 1), "X  ", " Y ", "  Y", 'X', ItemsAether.golden_amber, 'Y', BlocksAether.skyroot_planks);
		register("golden_dart", new ItemStack(ItemsAether.dart, 1), "X", "Y", "Z", 'X', Items.feather, 'Y', ItemsAether.skyroot_stick, 'Z', ItemsAether.golden_amber);
		register("poison_dart", new ItemStack(ItemsAether.dart, 8, 1), "XXX", "XYX", "XXX", 'X', new ItemStack(ItemsAether.dart, 1), 'Y', new ItemStack(ItemsAether.skyroot_bucket, 1, 2));
		register("incubator", new ItemStack(BlocksAether.incubator), "XXX", "XZX", "XXX", 'X', BlocksAether.holystone, 'Z', BlocksAether.ambrosium_torch);
		register("freezer", new ItemStack(BlocksAether.freezer), "XXX", "XYX", "ZZZ", 'X', BlocksAether.holystone, 'Y', BlocksAether.icestone, 'Z', BlocksAether.skyroot_planks);
		register("enchanter", new ItemStack(BlocksAether.enchanter), "XXX", "XYX", "XXX", 'X', BlocksAether.holystone, 'Y', ItemsAether.zanite_gemstone);
		register("furnace", new ItemStack(Blocks.furnace), "XXX", "X X", "XXX", 'X', BlocksAether.holystone);
		register("ladder", new ItemStack(Blocks.ladder, 4), "X X", "XXX", "X X", 'X', ItemsAether.skyroot_stick);
		register("jukebox", new ItemStack(Blocks.jukebox), "XXX", "XYX", "XXX", 'X', BlocksAether.skyroot_planks, 'Y', BlocksAether.enchanted_gravitite);
		register("oak_door", new ItemStack(Items.wooden_door, 3), "XX", "XX", "XX", 'X', BlocksAether.skyroot_planks);
		register("sign", new ItemStack(Items.sign, 3), "XXX", "XXX", " Y ", 'X', BlocksAether.skyroot_planks, 'Y', ItemsAether.skyroot_stick);
		register("ambrosium_torch", new ItemStack(BlocksAether.ambrosium_torch, 2), "Z", "Y", 'Z', ItemsAether.ambrosium_shard, 'Y', ItemsAether.skyroot_stick);
		register("lead", new ItemStack(Items.lead, 2), "YY ", "YX ", "  Y", 'Y', Items.string, 'X', ItemsAether.swet_ball);

		register("cloud_parachute", new ItemStack(ItemsAether.cloud_parachute, 1), "XX", "XX", 'X', new ItemStack(BlocksAether.aercloud, 1));
		register("golden_parachute", new ItemStack(ItemsAether.golden_parachute, 1), "XX", "XX", 'X', new ItemStack(BlocksAether.aercloud, 1, 2));
		register("saddle", new ItemStack(Items.saddle, 1), "XXX", "XZX", 'X', Items.leather, 'Z', Items.string);
		register("chest", new ItemStack(Blocks.chest, 1), "XXX", "X X", "XXX", 'X', BlocksAether.skyroot_planks);
		register("skyroot_bucket", new ItemStack(ItemsAether.skyroot_bucket, 1, 0), "Z Z", " Z ", 'Z', BlocksAether.skyroot_planks);
		register("crafting_table", new ItemStack(Blocks.crafting_table, 1), "XX", "XX", 'X', BlocksAether.skyroot_planks);

		register("gravitite_helmet", new ItemStack(ItemsAether.gravitite_helmet, 1), "XXX", "X X", 'X', BlocksAether.enchanted_gravitite);
		register("gravitite_chestplate", new ItemStack(ItemsAether.gravitite_chestplate, 1), "X X", "XXX", "XXX", 'X', BlocksAether.enchanted_gravitite);
		register("gravitite_leggings", new ItemStack(ItemsAether.gravitite_leggings, 1), "XXX", "X X", "X X", 'X', BlocksAether.enchanted_gravitite);
		register("gravitite_boots", new ItemStack(ItemsAether.gravitite_boots, 1), "X X", "X X", 'X', BlocksAether.enchanted_gravitite);
		register("zanite_helmet", new ItemStack(ItemsAether.zanite_helmet, 1), "XXX", "X X", 'X', ItemsAether.zanite_gemstone);
		register("zanite_chestplate", new ItemStack(ItemsAether.zanite_chestplate, 1), "X X", "XXX", "XXX", 'X', ItemsAether.zanite_gemstone);
		register("zanite_leggings", new ItemStack(ItemsAether.zanite_leggings, 1), "XXX", "X X", "X X", 'X', ItemsAether.zanite_gemstone);
		register("zanite_boots", new ItemStack(ItemsAether.zanite_boots, 1), "X X", "X X", 'X', ItemsAether.zanite_gemstone);

		register("skyroot_pickaxe", new ItemStack(ItemsAether.skyroot_pickaxe, 1), "ZZZ", " Y ", " Y ", 'Z', BlocksAether.skyroot_planks, 'Y', ItemsAether.skyroot_stick);
		register("holystone_pickaxe", new ItemStack(ItemsAether.holystone_pickaxe, 1), "ZZZ", " Y ", " Y ", 'Z', BlocksAether.holystone, 'Y', ItemsAether.skyroot_stick);
		register("zanite_pickaxe", new ItemStack(ItemsAether.zanite_pickaxe, 1), "ZZZ", " Y ", " Y ", 'Z', ItemsAether.zanite_gemstone, 'Y', ItemsAether.skyroot_stick);
		register("gravitite_pickaxe", new ItemStack(ItemsAether.gravitite_pickaxe, 1), "ZZZ", " Y ", " Y ", 'Z', BlocksAether.enchanted_gravitite, 'Y', ItemsAether.skyroot_stick);
		register("skyroot_axe", new ItemStack(ItemsAether.skyroot_axe, 1), "ZZ", "ZY", " Y", 'Z', BlocksAether.skyroot_planks, 'Y', ItemsAether.skyroot_stick);
		register("holystone_axe", new ItemStack(ItemsAether.holystone_axe, 1), "ZZ", "ZY", " Y", 'Z', BlocksAether.holystone, 'Y', ItemsAether.skyroot_stick);
		register("zanite_axe", new ItemStack(ItemsAether.zanite_axe, 1), "ZZ", "ZY", " Y", 'Z', ItemsAether.zanite_gemstone, 'Y', ItemsAether.skyroot_stick);
		register("gravitite_axe", new ItemStack(ItemsAether.gravitite_axe, 1), "ZZ", "ZY", " Y", 'Z', BlocksAether.enchanted_gravitite, 'Y', ItemsAether.skyroot_stick);
		register("skyroot_shovel", new ItemStack(ItemsAether.skyroot_shovel, 1), "Z", "Y", "Y", 'Z', BlocksAether.skyroot_planks, 'Y', ItemsAether.skyroot_stick);
		register("holystone_shovel", new ItemStack(ItemsAether.holystone_shovel, 1), "Z", "Y", "Y", 'Z', BlocksAether.holystone, 'Y', ItemsAether.skyroot_stick);
		register("zanite_shovel", new ItemStack(ItemsAether.zanite_shovel, 1), "Z", "Y", "Y", 'Z', ItemsAether.zanite_gemstone, 'Y', ItemsAether.skyroot_stick);
		register("gravitite_shovel", new ItemStack(ItemsAether.gravitite_shovel, 1), "Z", "Y", "Y", 'Z', BlocksAether.enchanted_gravitite, 'Y', ItemsAether.skyroot_stick);
		register("skyroot_sword", new ItemStack(ItemsAether.skyroot_sword, 1), "Z", "Z", "Y", 'Z', BlocksAether.skyroot_planks, 'Y', ItemsAether.skyroot_stick);
		register("holystone_sword", new ItemStack(ItemsAether.holystone_sword, 1), "Z", "Z", "Y", 'Z', BlocksAether.holystone, 'Y', ItemsAether.skyroot_stick);
		register("zanite_sword", new ItemStack(ItemsAether.zanite_sword, 1), "Z", "Z", "Y", 'Z', ItemsAether.zanite_gemstone, 'Y', ItemsAether.skyroot_stick);
		register("gravitite_sword", new ItemStack(ItemsAether.gravitite_sword, 1), "Z", "Z", "Y", 'Z', BlocksAether.enchanted_gravitite, 'Y', ItemsAether.skyroot_stick);

		register("white_cape", new ItemStack(ItemsAether.white_cape), "ZZ", "ZZ", "ZZ", 'Z', new ItemStack(Blocks.wool, 1, 0));

		register("iron_pendant", new ItemStack(ItemsAether.iron_pendant), " Z ", "Z Z", " ZS", 'Z', new ItemStack(Items.iron_ingot), 'S', new ItemStack(Items.string));
		register("golden_pendant", new ItemStack(ItemsAether.golden_pendant), " Z ", "Z Z", " ZS", 'Z', new ItemStack(Items.gold_ingot), 'S', new ItemStack(Items.string));

		register("leather_gloves", new ItemStack(ItemsAether.leather_gloves), "C C", 'C', Items.leather);
		register("iron_gloves", new ItemStack(ItemsAether.iron_gloves), "C C", 'C', Items.iron_ingot);
		register("golden_gloves", new ItemStack(ItemsAether.golden_gloves), "C C", 'C', Items.gold_ingot);
		register("diamond_gloves", new ItemStack(ItemsAether.diamond_gloves), "C C", 'C', Items.diamond);
		register("zanite_gloves", new ItemStack(ItemsAether.zanite_gloves), "C C", 'C', ItemsAether.zanite_gemstone);
		register("gravitite_gloves", new ItemStack(ItemsAether.gravitite_gloves), "C C", 'C', BlocksAether.enchanted_gravitite);

		register("skyroot_fence", new ItemStack(BlocksAether.skyroot_fence, 3), "ZXZ", "ZXZ", 'Z', new ItemStack(BlocksAether.skyroot_planks), 'X', new ItemStack(ItemsAether.skyroot_stick));
		register("skyroot_fence_gate", new ItemStack(BlocksAether.skyroot_fence_gate), "ZXZ", "ZXZ", 'X', new ItemStack(BlocksAether.skyroot_planks), 'Z', new ItemStack(ItemsAether.skyroot_stick));

		register("carved_stone_slab", new ItemStack(BlocksAether.carved_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.carved_stone));
		register("angelic_stone_slab", new ItemStack(BlocksAether.angelic_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.angelic_stone));
		register("hellfire_stone_slab", new ItemStack(BlocksAether.hellfire_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.hellfire_stone));
		register("holystone_slab", new ItemStack(BlocksAether.holystone_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.holystone, 1, 1));
		register("mossy_holystone_slab", new ItemStack(BlocksAether.mossy_holystone_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.mossy_holystone, 1, 1));
		register("holystone_brick_slab", new ItemStack(BlocksAether.holystone_brick_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.holystone_brick, 1));
		register("skyroot_slab", new ItemStack(BlocksAether.skyroot_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.skyroot_planks));

		register("carved_stone_wall", new ItemStack(BlocksAether.carved_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.carved_stone));
		register("angelic_stone_wall", new ItemStack(BlocksAether.angelic_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.angelic_stone));
		register("hellfire_stone_wall", new ItemStack(BlocksAether.hellfire_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.hellfire_stone));
		register("holystone_wall", new ItemStack(BlocksAether.holystone_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.holystone, 1, 1));
		register("mossy_holystone_wall", new ItemStack(BlocksAether.mossy_holystone_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.mossy_holystone, 1, 1));
		register("holystone_brick_wall", new ItemStack(BlocksAether.holystone_brick_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.holystone_brick, 1));

		register("carved_stone_stairs", new ItemStack(BlocksAether.carved_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.carved_stone));
		register("angelic_stone_stairs", new ItemStack(BlocksAether.angelic_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.angelic_stone));
		register("hellfire_stone_stairs", new ItemStack(BlocksAether.hellfire_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.hellfire_stone));
		register("holystone_stairs", new ItemStack(BlocksAether.holystone_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.holystone, 1, 1));
		register("mossy_holystone_stairs", new ItemStack(BlocksAether.mossy_holystone_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.mossy_holystone, 1, 1));
		register("holystone_brick_stairs", new ItemStack(BlocksAether.holystone_brick_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.holystone_brick, 1));
		register("skyroot_stairs", new ItemStack(BlocksAether.skyroot_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.skyroot_planks));

		register("skyroot_bookshelf", new ItemStack(BlocksAether.skyroot_bookshelf, 1),  "ZZZ", "XXX", "ZZZ", 'Z', new ItemStack(BlocksAether.skyroot_planks), 'X', new ItemStack(Items.book));

		register("skyroot_bed_item", new ItemStack(ItemsAether.skyroot_bed_item, 1),  "XXX", "ZZZ", 'Z', new ItemStack(BlocksAether.skyroot_planks), 'X', Blocks.wool);
	}

	private static void register(String name, ItemStack stack, Object... recipe) {
		GameRegistry.addRecipe(stack, recipe);
	}

	private static void registerShapeless(String name, ItemStack stack, Object... recipe) {
		GameRegistry.addShapelessRecipe(stack, recipe);
	}

}