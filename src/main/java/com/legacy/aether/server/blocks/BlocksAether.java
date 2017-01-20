package com.legacy.aether.server.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.legacy.aether.server.Aether;
import com.legacy.aether.server.blocks.container.BlockEnchanter;
import com.legacy.aether.server.blocks.container.BlockFreezer;
import com.legacy.aether.server.blocks.container.BlockIncubator;
import com.legacy.aether.server.blocks.decorative.BlockAerogel;
import com.legacy.aether.server.blocks.decorative.BlockAetherFence;
import com.legacy.aether.server.blocks.decorative.BlockAetherFenceGate;
import com.legacy.aether.server.blocks.decorative.BlockAetherSlab;
import com.legacy.aether.server.blocks.decorative.BlockAetherStairs;
import com.legacy.aether.server.blocks.decorative.BlockAetherWall;
import com.legacy.aether.server.blocks.decorative.BlockAmbrosiumTorch;
import com.legacy.aether.server.blocks.decorative.BlockPresnet;
import com.legacy.aether.server.blocks.decorative.BlockQuicksoilGlass;
import com.legacy.aether.server.blocks.decorative.BlockSkyrootPlank;
import com.legacy.aether.server.blocks.decorative.BlockZanite;
import com.legacy.aether.server.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.server.blocks.dungeon.BlockDungeonTrap;
import com.legacy.aether.server.blocks.dungeon.BlockMimicChest;
import com.legacy.aether.server.blocks.dungeon.BlockPillar;
import com.legacy.aether.server.blocks.dungeon.BlockTreasureChest;
import com.legacy.aether.server.blocks.natural.BlockAercloud;
import com.legacy.aether.server.blocks.natural.BlockAetherDirt;
import com.legacy.aether.server.blocks.natural.BlockAetherFlower;
import com.legacy.aether.server.blocks.natural.BlockAetherGrass;
import com.legacy.aether.server.blocks.natural.BlockAetherLeaves;
import com.legacy.aether.server.blocks.natural.BlockAetherLog;
import com.legacy.aether.server.blocks.natural.BlockBerryBush;
import com.legacy.aether.server.blocks.natural.BlockBerryBushStem;
import com.legacy.aether.server.blocks.natural.BlockCrystalLeaves;
import com.legacy.aether.server.blocks.natural.BlockHolidayLeaves;
import com.legacy.aether.server.blocks.natural.BlockHolystone;
import com.legacy.aether.server.blocks.natural.BlockIcestone;
import com.legacy.aether.server.blocks.natural.BlockQuicksoil;
import com.legacy.aether.server.blocks.natural.enchanted.BlockEnchantedAetherGrass;
import com.legacy.aether.server.blocks.natural.enchanted.BlockEnchantedGravitite;
import com.legacy.aether.server.blocks.natural.ore.BlockAmbrosiumOre;
import com.legacy.aether.server.blocks.natural.ore.BlockGravititeOre;
import com.legacy.aether.server.blocks.natural.ore.BlockZaniteOre;
import com.legacy.aether.server.blocks.portal.BlockAetherPortal;
import com.legacy.aether.server.items.block.ItemAetherSlab;
import com.legacy.aether.server.items.block.ItemSubtype;
import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.server.world.biome.decoration.AetherGenOakTree;
import com.legacy.aether.server.world.biome.decoration.AetherGenSkyrootTree;

public class BlocksAether
{

	public static Block aether_grass, enchanted_aether_grass, aether_dirt;

	public static Block holystone, mossy_holystone, holystone_brick;

	public static Block aercloud, quicksoil, icestone;

	public static Block ambrosium_ore, zanite_ore, gravitite_ore;

	public static Block aether_leaves, aether_log, skyroot_plank;

	public static Block quicksoil_glass, aerogel;

	public static Block enchanted_gravitite, zanite_block;

	public static Block berry_bush, berry_bush_stem;

	public static Block enchanter, freezer, incubator;

	public static Block ambrosium_torch;

	public static Block aether_portal;

	public static Block chest_mimic, treasure_chest;

	public static Block dungeon_trap, dungeon_block, locked_dungeon_block;

	public static Block purple_flower, white_flower;

	public static Block skyroot_sapling, golden_oak_sapling;

	public static Block crystal_leaves;

	public static Block pillar, pillar_top;

	public static Block skyroot_fence, skyroot_fence_gate;

	public static Block carved_stairs, angelic_stairs, hellfire_stairs, skyroot_stairs, mossy_holystone_stairs, holystone_stairs, holystone_brick_stairs;

	public static Block carved_slab, angelic_slab, hellfire_slab, skyroot_slab, holystone_slab, holystone_brick_slab, mossy_holystone_slab;

	public static Block carved_double_slab, angelic_double_slab, hellfire_double_slab, skyroot_double_slab, holystone_double_slab, holystone_brick_double_slab, mossy_holystone_double_slab;

	public static Block holystone_wall, mossy_holystone_wall, holystone_brick_wall, carved_wall, angelic_wall, hellfire_wall;

	public static Block holiday_leaves, present;

	public static void initialization()
	{
		aether_grass = register("aether_grass", new BlockAetherGrass());
		enchanted_aether_grass = register("enchanted_aether_grass", new BlockEnchantedAetherGrass());
		aether_dirt = register("aether_dirt", new BlockAetherDirt());
		holystone = register("holystone", new BlockHolystone());
		mossy_holystone = register("mossy_holystone", new BlockHolystone());
		holystone_brick = register("holystone_brick", new Block(Material.ROCK).setHardness(0.5F).setResistance(10.0F));
		aercloud = registerMeta("aercloud", new BlockAercloud());
		quicksoil = register("quicksoil", new BlockQuicksoil());
		icestone = register("icestone", new BlockIcestone());
		ambrosium_ore = register("ambrosium_ore", new BlockAmbrosiumOre());
		zanite_ore = register("zanite_ore", new BlockZaniteOre());
		gravitite_ore = register("gravitite_ore", new BlockGravititeOre());
		aether_leaves = registerMeta("aether_leaves", new BlockAetherLeaves());
		aether_log = registerMeta("aether_log", new BlockAetherLog());
		skyroot_plank = register("skyroot_plank", new BlockSkyrootPlank());
		quicksoil_glass = register("quicksoil_glass", new BlockQuicksoilGlass());
		aerogel = register("aerogel", new BlockAerogel());
		enchanted_gravitite = register("enchanted_gravitite", new BlockEnchantedGravitite());
		zanite_block = register("zanite_block", new BlockZanite());
		berry_bush = register("berry_bush", new BlockBerryBush());
		berry_bush_stem = register("berry_bush_stem", new BlockBerryBushStem());
		enchanter = register("enchanter", new BlockEnchanter());
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
		skyroot_sapling = register("skyroot_sapling", new BlockAetherSapling(new AetherGenSkyrootTree()));
		golden_oak_sapling = register("golden_oak_sapling", new BlockAetherSapling(new AetherGenOakTree()));
		crystal_leaves = registerMeta("crystal_leaves", new BlockCrystalLeaves());
		holiday_leaves = registerMeta("holiday_leaves", new BlockHolidayLeaves());
		present = register("present", new BlockPresnet());
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

		carved_stairs = register("carved_stairs", new BlockAetherStairs(dungeon_block.getDefaultState()));
		angelic_stairs = register("angelic_stairs", new BlockAetherStairs(dungeon_block.getDefaultState()));
		hellfire_stairs = register("hellfire_stairs", new BlockAetherStairs(dungeon_block.getDefaultState()));
		skyroot_stairs = register("skyroot_stairs", new BlockAetherStairs(skyroot_plank.getDefaultState()));
		mossy_holystone_stairs = register("mossy_holystone_stairs", new BlockAetherStairs(mossy_holystone.getDefaultState()));
		holystone_stairs = register("holystone_stairs", new BlockAetherStairs(holystone.getDefaultState()));
		holystone_brick_stairs = register("holystone_brick_stairs", new BlockAetherStairs(holystone_brick.getDefaultState()));

		skyroot_double_slab = register("skyroot_double_slab", new BlockAetherSlab("skyroot_double_slab", true, Material.WOOD).setHardness(2.0F).setResistance(5.0F)).setCreativeTab(null);
		carved_double_slab = register("carved_double_slab", new BlockAetherSlab("carved_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		angelic_double_slab = register("angelic_double_slab", new BlockAetherSlab("angelic_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		hellfire_double_slab = register("hellfire_double_slab", new BlockAetherSlab("hellfire_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		holystone_double_slab = register("holystone_double_slab", new BlockAetherSlab("holystone_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		mossy_holystone_double_slab = register("mossy_holystone_double_slab", new BlockAetherSlab("mossy_holystone_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);
		holystone_brick_double_slab = register("holystone_brick_double_slab", new BlockAetherSlab("holystone_brick_double_slab", true, Material.ROCK).setHardness(2.0F).setResistance(10.0F)).setCreativeTab(null);

		skyroot_slab = registerSlab("skyroot_slab", new BlockAetherSlab("skyroot_slab", false, Material.WOOD).setHardness(2.0F).setResistance(5.0F), skyroot_double_slab);
		carved_slab = registerSlab("carved_slab", new BlockAetherSlab("carved_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), carved_double_slab);
		angelic_slab = registerSlab("angelic_slab", new BlockAetherSlab("angelic_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), angelic_double_slab);
		hellfire_slab = registerSlab("hellfire_slab", new BlockAetherSlab("hellfire_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), hellfire_double_slab);
		holystone_slab = registerSlab("holystone_slab", new BlockAetherSlab("holystone_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), holystone_double_slab);
		mossy_holystone_slab = registerSlab("mossy_holystone_slab", new BlockAetherSlab("mossy_holystone_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), mossy_holystone_double_slab);
		holystone_brick_slab = registerSlab("holystone_brick_slab", new BlockAetherSlab("holystone_brick_slab", false, Material.ROCK).setHardness(0.5F).setResistance(10.0F), holystone_brick_double_slab);
	}

	public static Block registerSlab(String name, Block slab1, Block slab2)
	{
		slab1.setCreativeTab(AetherCreativeTabs.blocks);

		GameRegistry.register(slab1.setRegistryName(Aether.locate(name)));
		GameRegistry.register(new ItemAetherSlab(slab1, (BlockSlab) slab1, (BlockSlab) slab2).setRegistryName(Aether.locate(name)));

		return slab1;
	}

	public static Block register(String name, Block block)
	{
		block.setUnlocalizedName(name);
		block.setCreativeTab(AetherCreativeTabs.blocks);

		GameRegistry.register(block.setRegistryName(Aether.locate(name)));
		GameRegistry.register(new ItemBlock(block).setRegistryName(Aether.locate(name)));

		return block;
	}

	public static Block registerMeta(String name, Block block)
	{
		GameRegistry.register(block.setRegistryName(Aether.locate(name)));
		GameRegistry.register(new ItemSubtype(block).setRegistryName(Aether.locate(name)));

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