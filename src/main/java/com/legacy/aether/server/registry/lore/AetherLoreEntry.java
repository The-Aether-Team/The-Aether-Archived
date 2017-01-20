package com.legacy.aether.server.registry.lore;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.items.ItemsAether;
import com.legacy.aether.server.registry.objects.EntryInformation;
import com.legacy.aether.server.registry.objects.LoreEntry;

public class AetherLoreEntry extends LoreEntry
{
	private ArrayList<EntryInformation> information;

	@Override
	public AetherLoreEntry initEntries() 
	{
		information = new ArrayList<EntryInformation>();
		
		/* Blocks */
		information.add(new EntryInformation(new ItemStack(BlocksAether.aether_grass), "Aether Grass", "A paler grass.", "Skyroot Trees are allowed", "to grow on this grass.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.enchanted_aether_grass), "Enchanted Aether Grass", "Aether Grass enchanted", "which allows for", "increasing harvest rates", "of blue berries.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aether_dirt), "Aether Dirt", "A paler dirt.", "can be revitalized once", "given enough light and", "is near Aether Grass.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.holystone), "Holystone", "Used in various ways such as,", "creating tools, construction,", "as well being able to", "be crafted into", "Holystone Bricks.", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.mossy_holystone), "Mossy Holystone", "Found near dungeons,", "used as decoration blocks.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.holystone_brick), "Holystone Brick", "Used for decoration.", "Created by compacting", "Holystone.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aercloud, 1, 0), "Cold Aercloud", "A cold cloud found", "in the aether dimension.", "Used to make Parachutes", "and break falls.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aercloud, 1, 1), "Blue Aercloud", "A cloud formed", "with bouncy properties.", "Soars entities high", "up into the air.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aercloud, 1, 2), "Golden Aercloud", "A golden cloud", "which can be used", "to craft parachutes", "which have a", "longer durability.", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.quicksoil), "Quicksoil", "A block with extreme", "slipery properties. Be", "cautious when around", "these blocks.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.icestone), "Icestone", "Used to freeze", "surrounding liquids and", "is the fuel", "for Freezers.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.ambrosium_ore), "Ambrosium Ore", "Holystone which", "contains Ambrosium inside.", "Can be double", "dropped with", "Skyroot Tools.", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.zanite_ore), "Zanite Ore", "Holystone which", "contains Zanite inside.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.gravitite_ore), "Gravitite Ore", "Holystone which", "which contains Gravitite.", "Must be enchanted", "to obtain Gravitite.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aether_leaves), "Skyroot Leaves", "Leaves which", "can yield skyroot", "saplings.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aether_leaves, 1, 1), "Golden Oak Leaves", "Leaves which", "can yield golden", "oak saplings.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aether_log), "Skyroot Log", "The main material", "of the Aether. Can", "be crafted to make", "Skyroot Planks.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aether_log, 1, 1), "Golden Oak Log", "Skyroot Log which", "contains Golden Amber.", "Can be extracted", "using a Gravitite", "Axe.", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.skyroot_plank), "Skyroot Plank", "The main material", "of the Aether.", "Used to create Skyroot", "Tools and Skyroot", "Sticks.", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.quicksoil_glass), "Quicksoil Glass", "The result of", "enchanting Quicksoil.", "Used for Decoration.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aerogel), "Aerogel", "The result of the", "Aether's unique climate", "and lava combining.", "Blast resistant.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.enchanted_gravitite), "Enchanted Gravitite", "The result of", "enchanting Gravitite", "in an Enchanter.", "Can float if powered.", "Can be used for", "Gravitite Tools"));
		information.add(new EntryInformation(new ItemStack(BlocksAether.zanite_block), "Zanite Block", "Compacted Zanite which", "can be turned back", "into Zanite.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.berry_bush), "Berry Bush", "A Berry Bush Stem", "which have regained", "it's berries.", "Can be harvested", "for Blue Berries.", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.berry_bush_stem), "Berry Bush Stem", "A Stem which", "can grow blue", "berries.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.enchanter), "Enchanter", "Used to enchant items", "and repair armor.", "Powered by", "Ambrosium.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.freezer), "Freezer", "Used to freeze unique", "item such as aerclouds.", "Powered by", "Icestone.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.incubator), "Incubator", "Ued to incubate Moa's.", "Powered by", "Ambrosium Torches.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.aether_portal), "Aether Portal", "I'm sad you had", "to cheat that in", "to check what it", "does. ~Kino", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.ambrosium_torch), "Ambrosium Torch", "Main light source", "in the Aether.", "Used as fuel for", "Incubators.", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.purple_flower), "Purple Flower", "Aether decoration.", "Can be crafted", "into purple dye.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.white_flower), "White Flower", "Aether decoration.", "", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.skyroot_sapling), "Skyroot Sapling", "A sapling which", "grows a Skyroot Tree.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(BlocksAether.golden_oak_sapling), "Golden Oak Sapling", "A sapling which", "grows a Golden Oak", "Tree.", "", "", ""));

		/* Items */
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_gemstone), "Zanite Gemstone", "obtained by mining", "Zanite Ore. Used", "for crafting Zanite", "Tools.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.ambrosium_shard), "Ambrosium Shard", "obtained by mining", "Ambrosium Ore. Used", "as enchanter fuel.", "Can be eaten", "to restore a", "little health."));
		information.add(new EntryInformation(new ItemStack(ItemsAether.golden_amber), "Golden Amber", "obtained by mining", "Golden Oak Logs", "with a Gravitite", "Axe. Used to", "craft Golden darts.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.aechor_petal), "Aechor Petal", "obtained by Aechor", "Plants. Used to", "feed Moa's in their", "baby form.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.skyroot_pickaxe), "Skyroot Pickaxe", "Contains a special", "double drop ability.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.skyroot_axe), "Skyroot Axe", "Contains a special", "double drop ability.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.skyroot_shovel), "Skyroot Shovel", "Contains a special", "double drop ability.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.holystone_pickaxe), "Holystone Pickaxe", "Has a chance", "of dropping", "random Ambrosium", "Shards.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.holystone_axe), "Holystone Axe", "Has a chance", "of dropping", "random Ambrosium", "Shards.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.holystone_shovel), "Holystone Shovel", "Has a chance", "of dropping", "random Ambrosium", "Shards.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_pickaxe), "Zanite Pickaxe", "Slow at first,", "after some uses", "its speed increases.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_axe), "Zanite Axe", "Slow at first,", "after some uses", "its speed increases.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_shovel), "Zanite Shovel", "Slow at first,", "after some uses", "its speed increases.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gravitite_pickaxe), "Gravitite Pickaxe", "Has the special", "ability to invert", "gravity on blocks.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gravitite_axe), "Gravitite Axe", "Has the special", "ability to invert", "gravity on blocks.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gravitite_shovel), "Gravitite Shovel", "Has the special", "ability to invert", "gravity on blocks.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.valkyrie_pickaxe), "Valkyrie Pickaxe", "Has a far greater", "reach than any", "other tool", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.valkyrie_axe), "Valkyrie Axe", "Has a far greater", "reach than any", "other tool", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.valkyrie_shovel), "Valkyrie Shovel", "Has a far greater", "reach than any", "other tool", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_helmet), "Zanite Helmet", "Has fair ammount", "of protection.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_chestplate), "Zanite Chestplate", "Has fair ammount", "of protection.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_leggings), "Zanite Leggings", "Has fair ammount", "of protection.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_boots), "Zanite Boots", "Has fair ammount", "of protection.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_gloves), "Zanite Gloves", "Has fair ammount", "of protection", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gravitite_helmet), "Gravitite Helmet", "Has high ammount", "of protection.", "When fully worn,", "high jump is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gravitite_chestplate), "Gravitite Chestplate", "Has high ammount", "of protection.", "When fully worn,", "high jump is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gravitite_leggings), "Gravitite Leggings", "Has high ammount", "of protection.", "When fully worn,", "high jump is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gravitite_boots), "Gravitite Boots", "Has high ammount", "of protection.", "When fully worn,", "high jump is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gravitite_gloves), "Gravitite Gloves", "Has high ammount", "of protection.", "When fully worn,", "high jump is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.neptune_helmet), "Neptune Helmet", "Has high ammount", "of protection.", "When fully worn,", "water walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.neptune_chestplate), "Neptune Chestplate", "Has high ammount", "of protection.", "When fully worn,", "water walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.neptune_leggings), "Neptune Leggings", "Has high ammount", "of protection.", "When fully worn,", "water walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.neptune_boots), "Neptune Boots", "Has high ammount", "of protection.", "When fully worn,", "water walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.neptune_gloves), "Neptune Gloves", "Has high ammount", "of protection.", "When fully worn,", "water walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.phoenix_helmet), "Phoenix Helmet", "Has high ammount", "of protection", "When fully worn", "lava walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.phoenix_chestplate), "Phoenix Chestplate", "Has high ammount", "of protection", "When fully worn", "lava walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.phoenix_leggings), "Phoenix Leggings", "Has high ammount", "of protection", "When fully worn", "lava walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.phoenix_boots), "Phoenix Boots", "Has high ammount", "of protection", "When fully worn", "lava walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.phoenix_gloves), "Phoenix Gloves", "Has high ammount", "of protection", "When fully worn", "lava walking is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.obsidian_helmet), "Obsidian Helmet", "Has high ammount", "of protection.", "Extra protection is", "given when fully", "worn.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.obsidian_chestplate), "Obsidian Chestplate", "Has high ammount", "of protection.", "Extra protection is", "given when fully", "worn.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.obsidian_leggings), "Obsidian Leggings", "Has high ammount", "of protection.", "Extra protection is", "given when fully", "worn.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.obsidian_boots), "Obsidian Boots", "Has high ammount", "of protection.", "Extra protection is", "given when fully", "worn.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.obsidian_gloves), "Obsidian Gloves", "Has high ammount", "of protection.", "Extra protection is", "given when fully", "worn.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.valkyrie_helmet), "Valkyrie Helmet", "Has high ammount", "of protection.", "When fully worn,", "temporary flight is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.valkyrie_chestplate), "Valkyrie Chestplate", "Has high ammount", "of protection.", "When fully worn,", "temporary flight is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.valkyrie_leggings), "Valkyrie Leggings", "Has high ammount", "of protection.", "When fully worn,", "temporary flight is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.valkyrie_boots), "Valkyrie Boots", "Has high ammount", "of protection.", "When fully worn,", "temporary flight is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.valkyrie_gloves), "Valkyrie Gloves", "Has high ammount", "of protection.", "When fully worn,", "temporary flight is", "unlocked.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.blue_berry), "Blueberry", "The main food", "source in the Aether.", "Easy to obtain,", "minimal hunger", "restoration.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.white_apple), "White Apple", "The only known", "cure for deady poison.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.regeneration_stone), "Regeneration Stone", "Enchanted Holystone", "which gives off", "regenerative effects.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.candy_cane), "Candy Cane", "Holiday goodies", "from presents.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.ginger_bread_man), "Ginger Bread Man", "Holiday goodies", "from presents.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gummy_swet, 1, 0), "Blue Gummy Swet", "Rare healing item.", "Heals players health", "to max.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gummy_swet, 1, 1), "Golden Gummy Swet", "Rare healing item.", "Heals players health", "to max.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.skyroot_stick), "Skyroot Stick", "cCrafted from Skyroot", "Planks. Used to", "create various", "aether tools.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.victory_medal), "Victory Medal", "Proof of defeating", "a lesser Valkyrie.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.cloud_parachute), "Cloud Parachute", "A parachute which", "negates fall damage", "and provides", "a safe landing.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.golden_parachute), "Golden Parachute", "A long lasting", "parachute which", "negates fall damage", "and provides", "a safe landing.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dungeon_key, 1, 0), "Bronze Key", "A key which unlocks", "bronze tier rewards.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dungeon_key, 1, 1), "Silver Key", "A key which unlocks", "silver tier rewards.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dungeon_key, 1, 2), "Golden Key", "A key which unlocks", "golden tier rewards.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dungeon_key, 1, 3), "Platinum Key", "Only the true", "holder knows what", "unlocks with this", "key.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.skyroot_bucket, 1, 0), "Skyroot Bucket", "Used to contain", "poison, a remedy or", "even water.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.skyroot_bucket, 1, 1), "Skyroot Water Bucket", "A Skyroot Bucket", "containing water.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.skyroot_bucket, 1, 2), "Skyroot Poison Bucket", "A Skyroot Bucket", "containing poison", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.skyroot_bucket, 1, 3), "Skyroot Remedy Bucket", "A Skyroot Bucket", "containing a remedy.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.moa_egg, 1, 0), "Blue Moa Egg", "A Moa Egg", "containing a blue", "moa inside.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.moa_egg, 1, 1), "Orange Moa Egg", "A Moa Egg", "containing an orange", "moa inside.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.moa_egg, 1, 2), "White Moa Egg", "A Moa Egg", "containing a White", "moa inside.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.moa_egg, 1, 3), "Black Moa Egg", "A Moa Egg", "containing a Black", "moa inside.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dart_shooter, 1, 0), "Golden Dart Shooter", "A Dart Shooter", "which shoots golden", "darts.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dart_shooter, 1, 1), "Poison Dart Shooter", "A Dart Shooter", "which shoots poison", "darts.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dart_shooter, 1, 2), "Enchanted Dart Shooter", "A Dart Shooter", "which shoots", "enchanted darts.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.phoenix_bow), "Phoenix Bow", "A Scorching bow", "flames any arrows", "it shoots.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dart, 1, 0), "Golden Dart", "Ammo for Golden", "Dart Shooters", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dart, 1, 1), "Poison Dart", "Ammo for Poison", "Dart Shooters", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.dart, 1, 2), "Enchanted Dart", "Ammo for Enchanted", "Dart Shooters", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.skyroot_sword), "Skyroot Sword", "A sword made", "of skyroot planks.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.holystone_sword), "Holystone Sword", "A sword made", "of Holystone.", "Randomly drops", "ambrosium when used.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_sword), "Zanite Sword", "A sword made", "of Zanite.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.gravitite_sword), "Gravitite Sword", "A sword made", "of Gravitite.", "Sends foes", "soaring to the", "skies.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.flaming_sword), "Flaming Sword", "An ancient sword", "which flames its", "foes to a", "burning crisp.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.lightning_sword), "Lightning Sword", "An ancient sword", "which summons lightning", "to its foes.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.holy_sword), "Holy Sword", "An ancient sword", "which does heavy", "ammounts of damage", "to undead foes.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.vampire_blade), "Vampire Blade", "A Vamperic sword", "with life stealing", "abilities.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.pig_slayer), "Pig Slayers", "Kills any pig", "type mobs into", "nothing with a", "single blow.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.candy_cane_sword), "Candy Cane Sword", "sword made from", "decorative candy.", "Randomly drops", "candy canes when", "used.", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.notch_hammer), "Hammer of Notch", "a mighty hammer", "which shoots heavy", "projectiles at mobs.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.leather_gloves), "Leather Gloves", "Has low ammount", "of protection", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.iron_gloves), "Iron Gloves", "Has fair ammount", "of protection", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.golden_gloves), "Golden Gloves", "Has low ammount", "of protection", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.chain_gloves), "Chain Gloves", "Has low ammount", "of protectoin", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.diamond_gloves), "Diamond Gloves", "Has high ammount", "of protection", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.iron_ring), "Iron Ring", "An astetic accessory", "made of iron.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.golden_ring), "Golden Ring", "An astetic accessory", "made of gold.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_ring), "Zanite Ring", "A ring which brings", "faster mining to", "the wearer.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.ice_ring), "Ice Ring", "A ring which brings", "water freezing to", "the wearer", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.iron_pendant), "Iron Pendant", "An astetic accessory", "made of iron.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.golden_pendant), "Golden Pendant", "An astetic accessory", "made of gold.", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.zanite_pendant), "Zanite Pendant", "A pendant which brings", "faster mining to", "the wearer.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.ice_pendant), "Ice Pendant", "A pendant which brings", "water freezing to", "the wearer", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.red_cape), "Red Cape", "An astetic accessory.", "", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.blue_cape), "Blue Cape", "An astetic accessory.", "", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.yellow_cape), "Yellow Cape", "An astetic accessory.", "", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.white_cape), "White Cape", "An astetic accessory.", "", "", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.swet_cape), "Swet Cape", "A cape which allows", "the wearer to ride", "swets with", "complete control.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.invisibility_cape), "Invisibility Cape", "A cape which allows", "the wearer to be", "invisible.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.agility_cape), "Agility Cape", "A cape which allows", "the wearer double", "their usual step", "height.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.golden_feather), "Golden Feather", "A feather which", "allows the user", "a slow decent.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.regeneration_stone), "Regeneration Stone", "A stone with", "regenerative properties", "overtime it will", "heal the user.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.iron_bubble), "Iron Bubble", "A bubble which", "grants its user", "infinite breath", "underwater.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.life_shard), "Life Shard", "A shard which", "grants the user", "extra life.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.cloud_staff), "Cloud Staff", "A staff that", "grants the user", "two mini clouds", "to protect it.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.nature_staff), "Nature Staff", "A staff that", "changes the behavior", "of moas.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.lightning_knife), "Lightning Knife", "An unusual knife", "which, when thrown", "summons lightning", "where it lands.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.valkyrie_lance), "Valkyrie Lance", "A lance with a", "much greater", "mob reach than", "any other weapon.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.sentry_boots), "Sentry Boots", "Boots which negate", "fall damage to", "whoever is wearing", "these.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.aether_tune), "Aether Tune", "A dubstep song", "from the famous", "artist Noise Storm.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.ascending_dawn), "Ascending Dawn", "A calming song", "from the main", "musician of the Aether", "Emile.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.welcoming_skies), "Welcoming Skies", "A mixed song", "from a companion", "Voyed.", "", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.repulsion_shield), "Repulsion Shield", "A shield which", "repels almost all", "projectiles back to", "their owners.", "", ""));
		information.add(new EntryInformation(new ItemStack(ItemsAether.lore_book), "Book of Lore", "A collection of", "information written down", "by one person.", "", "", ""));

		return this;
	}

	@Override
	public ArrayList<EntryInformation> EntryInformation()
	{
		return information;
	}
}
