package com.gildedgames.the_aether.blocks;

import com.gildedgames.the_aether.blocks.container.BlockEnchanter;
import com.gildedgames.the_aether.blocks.container.BlockFreezer;
import com.gildedgames.the_aether.blocks.container.BlockIncubator;
import com.gildedgames.the_aether.blocks.natural.enchanted.BlockEnchantedAetherGrass;
import com.gildedgames.the_aether.blocks.natural.enchanted.BlockEnchantedGravitite;
import com.gildedgames.the_aether.blocks.natural.ore.BlockAmbrosiumOre;
import com.gildedgames.the_aether.blocks.natural.ore.BlockGravititeOre;
import com.gildedgames.the_aether.blocks.natural.ore.BlockZaniteOre;
import com.gildedgames.the_aether.blocks.portal.BlockAetherPortal;
import com.gildedgames.the_aether.items.block.ItemEnchanter;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.blocks.decorative.*;
import com.gildedgames.the_aether.blocks.dungeon.BlockDungeonBase;
import com.gildedgames.the_aether.blocks.dungeon.BlockDungeonTrap;
import com.gildedgames.the_aether.blocks.dungeon.BlockMimicChest;
import com.gildedgames.the_aether.blocks.dungeon.BlockPillar;
import com.gildedgames.the_aether.blocks.dungeon.BlockTreasureChest;
import com.gildedgames.the_aether.blocks.natural.*;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.blocks.util.EnumLeafType;
import com.gildedgames.the_aether.items.block.ItemAetherSlab;
import com.gildedgames.the_aether.items.block.ItemRarity;
import com.gildedgames.the_aether.items.block.ItemSubtype;
import com.gildedgames.the_aether.world.biome.decoration.AetherGenOakTree;
import com.gildedgames.the_aether.world.biome.decoration.AetherGenSkyrootTree;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

public class BlocksAether
{

	public static Block aether_grass, enchanted_aether_grass, aether_dirt, aether_tall_grass, aether_grass_path;

	public static Block holystone, mossy_holystone, holystone_brick;

	public static Block aercloud, quicksoil, icestone;

	public static Block ambrosium_ore, zanite_ore, gravitite_ore;

	public static Block aether_leaves, aether_leaves_2, aether_log, skyroot_plank;

	public static Block quicksoil_glass, aerogel;

	public static Block enchanted_gravitite, zanite_block;

	public static Block berry_bush, berry_bush_stem;

	public static Block enchanter, freezer, incubator;

	public static Block ambrosium_torch;

	public static Block aether_portal;

	public static Block chest_mimic, treasure_chest;

	public static Block dungeon_trap, dungeon_block, locked_dungeon_block;

	public static Block purple_flower, white_flower;

	public static Block skyroot_sapling, golden_oak_sapling, blue_sapling, dark_blue_sapling, purple_sapling;

	public static Block crystal_leaves;

	public static Block pillar, pillar_top;

	public static Block skyroot_fence, skyroot_fence_gate;

	public static Block carved_stairs, angelic_stairs, hellfire_stairs, skyroot_stairs, mossy_holystone_stairs, holystone_stairs, holystone_brick_stairs, aerogel_stairs;

	public static Block carved_slab, angelic_slab, hellfire_slab, skyroot_slab, holystone_slab, holystone_brick_slab, mossy_holystone_slab, aerogel_slab;

	public static Block carved_double_slab, angelic_double_slab, hellfire_double_slab, skyroot_double_slab, holystone_double_slab, holystone_brick_double_slab, mossy_holystone_double_slab, aerogel_double_slab;

	public static Block holystone_wall, mossy_holystone_wall, holystone_brick_wall, carved_wall, angelic_wall, hellfire_wall, aerogel_wall, skyroot_wall;

	public static Block holiday_leaves, present;

	public static Block sun_altar;

	public static Block skyroot_bookshelf;

	public static Block skyroot_bed;

	public static Block ambrosium_lamp;

	public static BlockChest dungeon_chest = Blocks.CHEST;

	private static int availableId;

	public static Block[] blockList = new Block[84];

	public static Item[] itemList = new Item[83];

	public static void registerBlocks(IForgeRegistry<Block> blockRegistry)
	{
		blockRegistry.registerAll(blockList);
	}

	public static void registerItems(IForgeRegistry<Item> blockRegistry)
	{
		blockRegistry.registerAll(itemList);
	}

	public static void initialization()
	{
		aether_grass = registerMeta("aether_grass", new BlockAetherGrass());
		enchanted_aether_grass = registerRarity("enchanted_aether_grass", new BlockEnchantedAetherGrass(), EnumRarity.RARE);
		aether_dirt = register("aether_dirt", new BlockAetherDirt());
		aether_tall_grass = registerMeta("aether_tall_grass", new BlockTallAetherGrass());
		aether_grass_path = register("aether_grass_path", new BlockAetherGrassPath());
		holystone = register("holystone", new BlockHolystone());
		mossy_holystone = register("mossy_holystone", new BlockHolystone());
		holystone_brick = register("holystone_brick", new Block(Material.ROCK).setHardness(0.5F).setResistance(10.0F));
		aercloud = registerMeta("aercloud", new BlockAercloud());
		quicksoil = register("quicksoil", new BlockQuicksoil());
		icestone = register("icestone", new BlockIcestone());
		ambrosium_ore = register("ambrosium_ore", new BlockAmbrosiumOre());
		zanite_ore = register("zanite_ore", new BlockZaniteOre());
		gravitite_ore = register("gravitite_ore", new BlockGravititeOre());
		aether_leaves = registerMeta("aether_leaves", new BlockAetherLeaves(EnumLeafType.Green));
		aether_leaves_2 = registerMeta("aether_leaves_2", new BlockAetherLeaves(EnumLeafType.Purple));
		aether_log = registerMeta("aether_log", new BlockAetherLog());
		skyroot_plank = register("skyroot_plank", new BlockSkyrootPlank());
		quicksoil_glass = registerRarity("quicksoil_glass", new BlockQuicksoilGlass(), EnumRarity.RARE);
		aerogel = registerRarity("aerogel", new BlockAerogel(), ItemsAether.aether_loot);
		enchanted_gravitite = registerRarity("enchanted_gravitite", new BlockEnchantedGravitite(), EnumRarity.RARE);
		zanite_block = register("zanite_block", new BlockZanite());
		berry_bush = register("berry_bush", new BlockBerryBush());
		berry_bush_stem = register("berry_bush_stem", new BlockBerryBushStem());
		enchanter = registerEnchanter("enchanter", new BlockEnchanter());
		freezer = register("freezer", new BlockFreezer());
		incubator = register("incubator", new BlockIncubator());
		dungeon_block = registerMeta("dungeon_block", new BlockDungeonBase(false));
		locked_dungeon_block = registerMeta("locked_dungeon_block", new BlockDungeonBase(true).setResistance(2500F));
		dungeon_trap = registerMeta("dungeon_trap", new BlockDungeonTrap());
		aether_portal = register("aether_portal", new BlockAetherPortal()).setCreativeTab(null);
		ambrosium_torch = register("ambrosium_torch", new BlockAmbrosiumTorch());
		chest_mimic = register("chest_mimic", new BlockMimicChest());
		treasure_chest = register("treasure_chest", new BlockTreasureChest());
		purple_flower = register("purple_flower", new BlockAetherFlower());
		white_flower = register("white_flower", new BlockAetherFlower());
		skyroot_sapling = register("skyroot_sapling", new BlockAetherSapling(EnumLeafType.Green));
		golden_oak_sapling = register("golden_oak_sapling", new BlockAetherSapling(EnumLeafType.Golden));
		blue_sapling = register("blue_sapling", new BlockAetherSapling(EnumLeafType.Blue));
		dark_blue_sapling = register("dark_blue_sapling", new BlockAetherSapling(EnumLeafType.DarkBlue));
		purple_sapling = register("purple_sapling", new BlockAetherSapling(EnumLeafType.Purple));
		crystal_leaves = registerMeta("crystal_leaves", new BlockCrystalLeaves());
		holiday_leaves = registerMeta("holiday_leaves", new BlockHolidayLeaves());
		present = register("present", new BlockPresent());
		pillar = register("pillar", new BlockPillar());
		pillar_top = register("pillar_top", new BlockPillar());

		skyroot_fence = register("skyroot_fence", new BlockAetherFence());
		skyroot_fence_gate = register("skyroot_fence_gate", new BlockAetherFenceGate());

		carved_wall = register("carved_wall", new BlockAetherWall(dungeon_block.getDefaultState()));
		angelic_wall = register("angelic_wall", new BlockAetherWall(dungeon_block.getDefaultState()));
		hellfire_wall = register("hellfire_wall", new BlockAetherWall(dungeon_block.getDefaultState()));
		holystone_wall = register("holystone_wall", new BlockAetherWall(holystone.getDefaultState()));
		holystone_brick_wall = register("holystone_brick_wall", new BlockAetherWall(holystone_brick.getDefaultState()));
		mossy_holystone_wall = register("mossy_holystone_wall", new BlockAetherWall(mossy_holystone.getDefaultState()));
		aerogel_wall = registerRarity("aerogel_wall", new BlockAerogelWall(aerogel.getDefaultState()), ItemsAether.aether_loot);
		skyroot_wall = register("skyroot_wall", new BlockSkyrootWall(aether_log.getDefaultState()));

		carved_stairs = register("carved_stairs", new BlockAetherStairs(dungeon_block.getDefaultState()));
		angelic_stairs = register("angelic_stairs", new BlockAetherStairs(dungeon_block.getDefaultState()));
		hellfire_stairs = register("hellfire_stairs", new BlockAetherStairs(dungeon_block.getDefaultState()));
		skyroot_stairs = register("skyroot_stairs", new BlockAetherStairs(skyroot_plank.getDefaultState()));
		mossy_holystone_stairs = register("mossy_holystone_stairs", new BlockAetherStairs(mossy_holystone.getDefaultState()));
		holystone_stairs = register("holystone_stairs", new BlockAetherStairs(holystone.getDefaultState()));
		holystone_brick_stairs = register("holystone_brick_stairs", new BlockAetherStairs(holystone_brick.getDefaultState()));
		aerogel_stairs = registerRarity("aerogel_stairs", new BlockAerogelStairs(aerogel.getDefaultState()), ItemsAether.aether_loot);

		skyroot_double_slab = register("skyroot_double_slab", new BlockAetherSlab("skyroot_double_slab", true, Material.WOOD).setHardness(2.0F).setResistance(5.0F)).setCreativeTab(null);
		carved_double_slab = register("carved_double_slab", new BlockAetherSlab("carved_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		angelic_double_slab = register("angelic_double_slab", new BlockAetherSlab("angelic_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		hellfire_double_slab = register("hellfire_double_slab", new BlockAetherSlab("hellfire_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		holystone_double_slab = register("holystone_double_slab", new BlockAetherSlab("holystone_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		mossy_holystone_double_slab = register("mossy_holystone_double_slab", new BlockAetherSlab("mossy_holystone_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		holystone_brick_double_slab = register("holystone_brick_double_slab", new BlockAetherSlab("holystone_brick_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		aerogel_double_slab = register("aerogel_double_slab", new BlockAerogelSlab("aerogel_double_slab", true, Material.IRON).setHardness(2.0F).setResistance(999F)).setCreativeTab(null);

		skyroot_slab = registerSlab("skyroot_slab", new BlockAetherSlab("skyroot_slab", false, Material.WOOD).setHardness(2.0F).setResistance(5.0F), skyroot_double_slab);
		carved_slab = registerSlab("carved_slab", new BlockAetherSlab("carved_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), carved_double_slab);
		angelic_slab = registerSlab("angelic_slab", new BlockAetherSlab("angelic_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), angelic_double_slab);
		hellfire_slab = registerSlab("hellfire_slab", new BlockAetherSlab("hellfire_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), hellfire_double_slab);
		holystone_slab = registerSlab("holystone_slab", new BlockAetherSlab("holystone_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), holystone_double_slab);
		mossy_holystone_slab = registerSlab("mossy_holystone_slab", new BlockAetherSlab("mossy_holystone_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), mossy_holystone_double_slab);
		holystone_brick_slab = registerSlab("holystone_brick_slab", new BlockAetherSlab("holystone_brick_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), holystone_brick_double_slab);
		aerogel_slab = registerSlab("aerogel_slab", new BlockAerogelSlab("aerogel_slab", false, Material.IRON).setHardness(0.5F).setResistance(999F), aerogel_double_slab);
		sun_altar = register("sun_altar", new BlockSunAltar());
		skyroot_bookshelf = register("skyroot_bookshelf", new BlockSkyrootBookshelf());
		ambrosium_lamp = register("ambrosium_lamp", new BlockAmbrosiumLamp());
		skyroot_bed = registerBed("skyroot_bed", new BlockSkyrootBed());
	}

	public static void initializeHarvestLevels()
	{
		BlocksAether.aether_grass.setHarvestLevel("shovel", 0);
		BlocksAether.enchanted_aether_grass.setHarvestLevel("shovel", 0);
		BlocksAether.aether_dirt.setHarvestLevel("shovel", 0);
		BlocksAether.holystone.setHarvestLevel("pickaxe", 0);
		BlocksAether.mossy_holystone.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_brick.setHarvestLevel("pickaxe", 0);
		BlocksAether.aercloud.setHarvestLevel("shovel", 0);
		BlocksAether.quicksoil.setHarvestLevel("shovel", 0);
		BlocksAether.icestone.setHarvestLevel("pickaxe", 1);
		BlocksAether.ambrosium_ore.setHarvestLevel("pickaxe", 0);
		BlocksAether.zanite_ore.setHarvestLevel("pickaxe", 1);
		BlocksAether.gravitite_ore.setHarvestLevel("pickaxe", 2);
		BlocksAether.aether_log.setHarvestLevel("axe", 0);
		BlocksAether.skyroot_plank.setHarvestLevel("axe", 0);
		BlocksAether.aerogel.setHarvestLevel("pickaxe", 3);
		BlocksAether.enchanted_gravitite.setHarvestLevel("pickaxe", 2);
		BlocksAether.zanite_block.setHarvestLevel("pickaxe", 1);
		BlocksAether.berry_bush_stem.setHarvestLevel("axe", 0);
		BlocksAether.enchanter.setHarvestLevel("pickaxe", 0);
		BlocksAether.freezer.setHarvestLevel("pickaxe", 0);
		BlocksAether.incubator.setHarvestLevel("pickaxe", 0);
		BlocksAether.dungeon_block.setHarvestLevel("pickaxe", 0);
		BlocksAether.chest_mimic.setHarvestLevel("axe", 0);
		BlocksAether.pillar.setHarvestLevel("pickaxe", 0);
		BlocksAether.pillar_top.setHarvestLevel("pickaxe", 0);
		BlocksAether.skyroot_fence.setHarvestLevel("axe", 0);
		BlocksAether.skyroot_fence_gate.setHarvestLevel("axe", 0);
		BlocksAether.carved_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.angelic_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.angelic_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.hellfire_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_brick_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.mossy_holystone_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.aerogel_wall.setHarvestLevel("pickaxe", 0);
		BlocksAether.carved_stairs.setHarvestLevel("pickaxe", 0);
		BlocksAether.angelic_stairs.setHarvestLevel("pickaxe", 0);
		BlocksAether.hellfire_stairs.setHarvestLevel("pickaxe", 0);
		BlocksAether.skyroot_stairs.setHarvestLevel("axe", 0);
		BlocksAether.mossy_holystone_stairs.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_stairs.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_brick_stairs.setHarvestLevel("pickaxe", 0);
		BlocksAether.aerogel_stairs.setHarvestLevel("pickaxe", 0);
		BlocksAether.skyroot_double_slab.setHarvestLevel("axe", 0);
		BlocksAether.carved_double_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.angelic_double_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.hellfire_double_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_double_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.mossy_holystone_double_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_brick_double_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.aerogel_double_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.skyroot_slab.setHarvestLevel("axe", 0);
		BlocksAether.carved_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.angelic_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.hellfire_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.mossy_holystone_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.holystone_brick_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.aerogel_slab.setHarvestLevel("pickaxe", 0);
		BlocksAether.sun_altar.setHarvestLevel("pickaxe", 0);
		BlocksAether.skyroot_bookshelf.setHarvestLevel("axe", 0);
		BlocksAether.skyroot_bed.setHarvestLevel("axe", 0);
	}

	public static Block registerSlab(String name, Block slab1, Block slab2)
	{
		slab1.setTranslationKey(name);
		slab1.setCreativeTab(AetherCreativeTabs.blocks);

		blockList[availableId] = slab1.setRegistryName(Aether.locate(name));
		itemList[availableId] = new ItemAetherSlab(slab1, (BlockSlab) slab1, (BlockSlab) slab2).setRegistryName(Aether.locate(name));

		++availableId;

		return slab1;
	}

	public static Block register(String name, Block block)
	{
		block.setTranslationKey(name);
		block.setCreativeTab(AetherCreativeTabs.blocks);

		blockList[availableId] = block.setRegistryName(Aether.locate(name));
		itemList[availableId] = new ItemBlock(block).setRegistryName(Aether.locate(name));

		++availableId;

		return block;
	}

	public static Block registerRarity(String name, Block block, EnumRarity rarity)
	{
		block.setTranslationKey(name);
		block.setCreativeTab(AetherCreativeTabs.blocks);

		blockList[availableId] = block.setRegistryName(Aether.locate(name));
		itemList[availableId] = new ItemRarity(block, rarity).setRegistryName(Aether.locate(name));

		++availableId;

		return block;
	}

	public static Block registerMeta(String name, Block block)
	{
		blockList[availableId] = block.setRegistryName(Aether.locate(name));
		itemList[availableId] = new ItemSubtype(block).setRegistryName(Aether.locate(name));

		++availableId;

		return block;
	}

	public static Block registerBed(String name, Block block)
	{
		blockList[availableId] = block.setRegistryName(Aether.locate(name));

		++availableId;

		return block;
	}

	public static Block registerEnchanter(String name, Block block)
	{
		block.setTranslationKey(name);
		block.setCreativeTab(AetherCreativeTabs.blocks);

		blockList[availableId] = block.setRegistryName(Aether.locate(name));
		itemList[availableId] = new ItemEnchanter(block).setRegistryName(Aether.locate(name));

		++availableId;

		return block;
	}

	public static boolean isGood(IBlockState state)
	{
		Block block = state.getBlock();
        return block == Blocks.AIR || block == aercloud;
    }

	public static boolean isEarth(IBlockState state)
	{
		Block block = state.getBlock();
        return block == aether_dirt || block == aether_grass || block == holystone || block == mossy_holystone;
    }

}