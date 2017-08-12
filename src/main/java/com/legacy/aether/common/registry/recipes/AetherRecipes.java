package com.legacy.aether.common.registry.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.items.ItemsAether;

public class AetherRecipes 
{

	public static void initialization()
	{
		initializeRecipes();
		initializeShapelessRecipes();

		FurnaceRecipes.instance().addSmeltingRecipeForBlock(BlocksAether.aether_log, new ItemStack(Items.COAL, 1, 1), 0.15F);
	}

	public static void initializeShapelessRecipes()
	{
		registerShapeless(new ItemStack(ItemsAether.blue_cape), ItemsAether.white_cape, new ItemStack(Items.DYE, 1, 4));
		registerShapeless(new ItemStack(ItemsAether.red_cape), ItemsAether.white_cape, new ItemStack(Items.DYE, 1, 1));
		registerShapeless(new ItemStack(ItemsAether.yellow_cape), ItemsAether.white_cape, new ItemStack(Items.DYE, 1, 11));
		registerShapeless(new ItemStack(ItemsAether.dart_shooter, 1, 1), new ItemStack(ItemsAether.dart_shooter, 1, 0), new ItemStack(ItemsAether.skyroot_bucket, 1, 2));
		registerShapeless(new ItemStack(Items.DYE, 2, 5), BlocksAether.purple_flower);
		registerShapeless(new ItemStack(BlocksAether.skyroot_plank, 4), new ItemStack(BlocksAether.aether_log));
	}

	public static void initializeRecipes()
	{
		register(new ItemStack(BlocksAether.skyroot_plank, 4), "X", 'X', BlocksAether.aether_log);
		register(new ItemStack(ItemsAether.nature_staff), "Y", "X", 'Y', ItemsAether.zanite_gemstone, 'X', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.skyroot_stick, 4), "X", "X", 'X', BlocksAether.skyroot_plank);
		register(new ItemStack(Blocks.TRAPDOOR, 2), "XXX", "XXX", 'X', BlocksAether.skyroot_plank);
		register(new ItemStack(BlocksAether.holystone_brick, 4), "XX", "XX", 'X', BlocksAether.holystone);
		register(new ItemStack(BlocksAether.zanite_block), "XXX", "XXX", "XXX", 'X', ItemsAether.zanite_gemstone);
		register(new ItemStack(ItemsAether.zanite_gemstone, 9), "X", 'X', BlocksAether.zanite_block);
		register(new ItemStack(ItemsAether.dart_shooter, 1), "X  ", " Y ", "  Y", 'X', ItemsAether.golden_amber, 'Y', BlocksAether.skyroot_plank);
		register(new ItemStack(ItemsAether.dart, 1), "X", "Y", "Z", 'X', Items.FEATHER, 'Y', ItemsAether.skyroot_stick, 'Z', ItemsAether.golden_amber);
		register(new ItemStack(ItemsAether.dart_shooter, 1, 1), "X", "Y", 'X', new ItemStack(ItemsAether.dart_shooter, 1), 'Y', new ItemStack(ItemsAether.skyroot_bucket, 1, 2));
		register(new ItemStack(ItemsAether.dart, 8, 1), "XXX", "XYX", "XXX", 'X', new ItemStack(ItemsAether.dart, 1), 'Y', new ItemStack(ItemsAether.skyroot_bucket, 1, 2));
		register(new ItemStack(BlocksAether.incubator), "XXX", "XZX", "XXX", 'X', BlocksAether.holystone, 'Z', BlocksAether.ambrosium_torch);
		register(new ItemStack(BlocksAether.freezer), "XXX", "XYX", "ZZZ", 'X', BlocksAether.holystone, 'Y', BlocksAether.icestone, 'Z', BlocksAether.skyroot_plank);
		register(new ItemStack(BlocksAether.enchanter), "XXX", "XYX", "XXX", 'X', BlocksAether.holystone, 'Y', ItemsAether.zanite_gemstone);
		register(new ItemStack(Blocks.LADDER, 4), "X X", "XXX", "X X", 'X', ItemsAether.skyroot_stick);
		register(new ItemStack(Blocks.JUKEBOX), "XXX", "XYX", "XXX", 'X', BlocksAether.skyroot_plank, 'Y', BlocksAether.enchanted_gravitite);
		register(new ItemStack(Items.OAK_DOOR, 3), "XX", "XX", "XX", 'X', BlocksAether.skyroot_plank);
		register(new ItemStack(Items.SIGN, 3), "XXX", "XXX", " Y ", 'X', BlocksAether.skyroot_plank, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(BlocksAether.ambrosium_torch, 2), "Z", "Y", 'Z', ItemsAether.ambrosium_shard, 'Y', ItemsAether.skyroot_stick);

		register(new ItemStack(ItemsAether.cloud_parachute, 1), "XX", "XX", 'X', new ItemStack(BlocksAether.aercloud, 1));
		register(new ItemStack(ItemsAether.golden_parachute, 1), "XX", "XX", 'X', new ItemStack(BlocksAether.aercloud, 1, 2));
		register(new ItemStack(Items.SADDLE, 1), "XXX", "XZX", 'X', Items.LEATHER, 'Z', Items.STRING);
		register(new ItemStack(Blocks.CHEST, 1), "XXX", "X X", "XXX", 'X', BlocksAether.skyroot_plank);
		register(new ItemStack(ItemsAether.skyroot_bucket, 1, 0), "Z Z", " Z ", 'Z', BlocksAether.skyroot_plank);
		register(new ItemStack(Blocks.CRAFTING_TABLE, 1), "XX", "XX", 'X', BlocksAether.skyroot_plank);

		register(new ItemStack(ItemsAether.gravitite_helmet, 1), "XXX", "X X", 'X', BlocksAether.enchanted_gravitite);
		register(new ItemStack(ItemsAether.gravitite_chestplate, 1), "X X", "XXX", "XXX", 'X', BlocksAether.enchanted_gravitite);
		register(new ItemStack(ItemsAether.gravitite_leggings, 1), "XXX", "X X", "X X", 'X', BlocksAether.enchanted_gravitite);
		register(new ItemStack(ItemsAether.gravitite_boots, 1), "X X", "X X", 'X', BlocksAether.enchanted_gravitite);
		register(new ItemStack(ItemsAether.zanite_helmet, 1), "XXX", "X X", 'X', ItemsAether.zanite_gemstone);
		register(new ItemStack(ItemsAether.zanite_chestplate, 1), "X X", "XXX", "XXX", 'X', ItemsAether.zanite_gemstone);
		register(new ItemStack(ItemsAether.zanite_leggings, 1), "XXX", "X X", "X X", 'X', ItemsAether.zanite_gemstone);
		register(new ItemStack(ItemsAether.zanite_boots, 1), "X X", "X X", 'X', ItemsAether.zanite_gemstone);

		register(new ItemStack(ItemsAether.skyroot_pickaxe, 1), "ZZZ", " Y ", " Y ", 'Z', BlocksAether.skyroot_plank, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.holystone_pickaxe, 1), "ZZZ", " Y ", " Y ", 'Z', BlocksAether.holystone, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.zanite_pickaxe, 1), "ZZZ", " Y ", " Y ", 'Z', ItemsAether.zanite_gemstone, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.gravitite_pickaxe, 1), "ZZZ", " Y ", " Y ", 'Z', BlocksAether.enchanted_gravitite, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.skyroot_axe, 1), "ZZ", "ZY", " Y", 'Z', BlocksAether.skyroot_plank, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.holystone_axe, 1), "ZZ", "ZY", " Y", 'Z', BlocksAether.holystone, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.zanite_axe, 1), "ZZ", "ZY", " Y", 'Z', ItemsAether.zanite_gemstone, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.gravitite_axe, 1), "ZZ", "ZY", " Y", 'Z', BlocksAether.enchanted_gravitite, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.skyroot_shovel, 1), "Z", "Y", "Y", 'Z', BlocksAether.skyroot_plank, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.holystone_shovel, 1), "Z", "Y", "Y", 'Z', BlocksAether.holystone, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.zanite_shovel, 1), "Z", "Y", "Y", 'Z', ItemsAether.zanite_gemstone, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.gravitite_shovel, 1), "Z", "Y", "Y", 'Z', BlocksAether.enchanted_gravitite, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.skyroot_sword, 1), "Z", "Z", "Y", 'Z', BlocksAether.skyroot_plank, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.holystone_sword, 1), "Z", "Z", "Y", 'Z', BlocksAether.holystone, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.zanite_sword, 1), "Z", "Z", "Y", 'Z', ItemsAether.zanite_gemstone, 'Y', ItemsAether.skyroot_stick);
		register(new ItemStack(ItemsAether.gravitite_sword, 1), "Z", "Z", "Y", 'Z', BlocksAether.enchanted_gravitite, 'Y', ItemsAether.skyroot_stick);

		register(new ItemStack(ItemsAether.white_cape), "ZZ", "ZZ", "ZZ", 'Z', new ItemStack(Blocks.WOOL, 1, 0));
		register(new ItemStack(ItemsAether.blue_cape), "ZZ", "ZZ", "ZZ", 'Z', new ItemStack(Blocks.WOOL, 1, 11));
		register(new ItemStack(ItemsAether.yellow_cape), "ZZ", "ZZ", "ZZ", 'Z', new ItemStack(Blocks.WOOL, 1, 4));
		register(new ItemStack(ItemsAether.red_cape), "ZZ", "ZZ", "ZZ", 'Z', new ItemStack(Blocks.WOOL, 1, 14));

		register(new ItemStack(ItemsAether.leather_gloves), "C C", 'C', Items.LEATHER);
		register(new ItemStack(ItemsAether.iron_gloves), "C C", 'C', Items.IRON_INGOT);
		register(new ItemStack(ItemsAether.golden_gloves), "C C", 'C', Items.GOLD_INGOT);
		register(new ItemStack(ItemsAether.diamond_gloves), "C C", 'C', Items.DIAMOND);
		register(new ItemStack(ItemsAether.zanite_gloves), "C C", 'C', ItemsAether.zanite_gemstone);
		register(new ItemStack(ItemsAether.gravitite_gloves), "C C", 'C', BlocksAether.enchanted_gravitite);

		register(new ItemStack(BlocksAether.skyroot_fence, 3), "ZXZ", "ZXZ", 'Z', new ItemStack(BlocksAether.skyroot_plank), 'X', new ItemStack(ItemsAether.skyroot_stick));
		register(new ItemStack(BlocksAether.skyroot_fence_gate), "ZXZ", "ZXZ", 'X', new ItemStack(BlocksAether.skyroot_plank), 'Z', new ItemStack(ItemsAether.skyroot_stick));

		register(new ItemStack(BlocksAether.carved_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.dungeon_block, 1, 0));
		register(new ItemStack(BlocksAether.angelic_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.dungeon_block, 1, 2));
		register(new ItemStack(BlocksAether.hellfire_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.dungeon_block, 1, 4));
		register(new ItemStack(BlocksAether.holystone_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.holystone, 1, 1));
		register(new ItemStack(BlocksAether.mossy_holystone_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.mossy_holystone, 1, 1));
		register(new ItemStack(BlocksAether.holystone_brick_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.holystone_brick, 1));
		register(new ItemStack(BlocksAether.skyroot_slab, 6), "ZZZ", 'Z', new ItemStack(BlocksAether.skyroot_plank));

		register(new ItemStack(BlocksAether.carved_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.dungeon_block, 1, 0));
		register(new ItemStack(BlocksAether.angelic_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.dungeon_block, 1, 2));
		register(new ItemStack(BlocksAether.hellfire_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.dungeon_block, 1, 4));
		register(new ItemStack(BlocksAether.holystone_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.holystone, 1, 1));
		register(new ItemStack(BlocksAether.mossy_holystone_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.mossy_holystone, 1, 1));
		register(new ItemStack(BlocksAether.holystone_brick_wall, 6), "ZZZ", "ZZZ", 'Z', new ItemStack(BlocksAether.holystone_brick, 1));

		register(new ItemStack(BlocksAether.carved_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.dungeon_block, 1, 0));
		register(new ItemStack(BlocksAether.angelic_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.dungeon_block, 1, 2));
		register(new ItemStack(BlocksAether.hellfire_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.dungeon_block, 1, 4));
		register(new ItemStack(BlocksAether.holystone_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.holystone, 1, 1));
		register(new ItemStack(BlocksAether.mossy_holystone_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.mossy_holystone, 1, 1));
		register(new ItemStack(BlocksAether.holystone_brick_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.holystone_brick, 1));
		register(new ItemStack(BlocksAether.skyroot_stairs, 4), "Z  ", "ZZ ", "ZZZ", 'Z', new ItemStack(BlocksAether.skyroot_plank));
	}

	public static void register(ItemStack stack, Object... recipe)
	{
		GameRegistry.addRecipe(stack, recipe);
	}

	public static void registerShapeless(ItemStack stack, Object... recipe)
	{
		GameRegistry.addShapelessRecipe(stack, recipe);
	}

}