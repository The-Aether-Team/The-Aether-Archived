package com.legacy.aether.dictionary;

import com.legacy.aether.items.util.EnumSkyrootBucketType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.items.ItemsAether;

import java.util.Arrays;

public class AetherDictionary 
{

	private static final int WILDCARD = OreDictionary.WILDCARD_VALUE;

	public static void initialization()
	{
		//tree and wood-related things
		register("logWood", new ItemStack(BlocksAether.aether_log, 1, WILDCARD));
		register("slabWood", BlocksAether.skyroot_slab);
		register("stairWood", BlocksAether.skyroot_stairs);
		register("treeSapling", BlocksAether.golden_oak_sapling);
		register("treeSapling", BlocksAether.skyroot_sapling);
		register("treeLeaves", new ItemStack(BlocksAether.aether_leaves, 1, WILDCARD));
		register("treeLeaves", new ItemStack(BlocksAether.crystal_leaves, 1, WILDCARD));
		register("treeLeaves", new ItemStack(BlocksAether.holiday_leaves, 1, WILDCARD));

		//ores
		register("oreZanite", BlocksAether.zanite_ore);
		register("oreAmbrosium", BlocksAether.ambrosium_ore);
		register("oreGravitite", BlocksAether.gravitite_ore);

		//gems
		register("gemZanite", ItemsAether.zanite_gemstone);

        // crops
		register("cropBlueberry", ItemsAether.blue_berry);
		register("cropBlueberryEnchanted", ItemsAether.enchanted_blueberry);

		//blocks
		register("blockZanite", BlocksAether.zanite_block);
		register("blockEnchantedGravitite", BlocksAether.enchanted_gravitite);
		register("torch", BlocksAether.ambrosium_torch);
		register("blockGlass", BlocksAether.quicksoil_glass);

		//records
		register("record", ItemsAether.aether_tune);
		register("record", ItemsAether.ascending_dawn);
		register("record", ItemsAether.welcoming_skies);

		//chests
		register("chest", BlocksAether.treasure_chest);
		register("chest", BlocksAether.chest_mimic);
		register("chestTreasure", BlocksAether.treasure_chest);
		register("chestMimic", BlocksAether.chest_mimic);

		//eggs
		register("egg", ItemsAether.moa_egg);
		register("listAllegg", ItemsAether.moa_egg);

		//buckets
		register("milkBucket", new ItemStack(ItemsAether.skyroot_bucket, EnumSkyrootBucketType.Milk.getMeta()));
		register("listAllmilk", new ItemStack(ItemsAether.skyroot_bucket, EnumSkyrootBucketType.Milk.getMeta()));
	}

	public static void register(String name, Block block)
	{
		OreDictionary.registerOre(name, block);
	}

	public static void register(String name, Item item)
	{
		OreDictionary.registerOre(name, item);
	}

	public static void register(String name, ItemStack stack)
	{
		OreDictionary.registerOre(name, stack);
	}

}