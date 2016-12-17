package com.legacy.aether.client.renders.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.client.renders.AetherEntityRenderingRegistry;
import com.legacy.aether.server.Aether;
import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.blocks.util.EnumCloudType;
import com.legacy.aether.server.blocks.util.EnumCrystalType;
import com.legacy.aether.server.blocks.util.EnumHolidayType;
import com.legacy.aether.server.blocks.util.EnumLeafType;
import com.legacy.aether.server.blocks.util.EnumLogType;
import com.legacy.aether.server.blocks.util.EnumStoneType;

public class BlockRendering 
{

	public static void initialize()
	{
		register(BlocksAether.aether_grass, "aether_grass");
		register(BlocksAether.aether_dirt, "aether_dirt");
		register(BlocksAether.holystone, "holystone");
		register(BlocksAether.mossy_holystone, "mossy_holystone");
		register(BlocksAether.quicksoil, "quicksoil");
		register(BlocksAether.ambrosium_ore, "ambrosium_ore");
		register(BlocksAether.enchanted_aether_grass, "enchanted_aether_grass");
		register(BlocksAether.holystone_brick, "holystone_brick");
		register(BlocksAether.icestone, "icestone");
		register(BlocksAether.zanite_ore, "zanite_ore");
		register(BlocksAether.gravitite_ore, "gravitite_ore");
		register(BlocksAether.skyroot_plank, "skyroot_plank");
		register(BlocksAether.quicksoil_glass, "quicksoil_glass");
		register(BlocksAether.aerogel, "aerogel");
		register(BlocksAether.enchanted_gravitite, "enchanted_gravitite");
		register(BlocksAether.zanite_block, "zanite_block");
		register(BlocksAether.enchanter, "enchanter");
		register(BlocksAether.freezer, "freezer");
		register(BlocksAether.incubator, "incubator");
		register(BlocksAether.aether_portal, "aether_portal");
		register(BlocksAether.ambrosium_torch, "ambrosium_torch");
		register(BlocksAether.berry_bush, "berry_bush");
		register(BlocksAether.berry_bush_stem, "berry_bush_stem");
		register(BlocksAether.white_flower, "white_flower");
		register(BlocksAether.purple_flower, "purple_flower");
		register(BlocksAether.skyroot_sapling, "skyroot_sapling");
		register(BlocksAether.golden_oak_sapling, "golden_oak_sapling");
		register(BlocksAether.treasure_chest, "treasure_chest");
		register(BlocksAether.chest_mimic, "chest_mimic");
		register(BlocksAether.present, "present");
		register(BlocksAether.pillar, "pillar");
		register(BlocksAether.pillar_top, "pillar_top");

		register(BlocksAether.skyroot_fence, "skyroot_fence");
		register(BlocksAether.skyroot_fence_gate, "skyroot_fence_gate");

		register(BlocksAether.carved_wall, "carved_wall");
		register(BlocksAether.angelic_wall, "angelic_wall");
		register(BlocksAether.hellfire_wall, "hellfire_wall");
		register(BlocksAether.holystone_brick_wall, "holystone_brick_wall");
		register(BlocksAether.mossy_holystone_wall, "mossy_holystone_wall");
		register(BlocksAether.holystone_wall, "holystone_wall");

		register(BlocksAether.skyroot_stairs, "skyroot_stairs");
		register(BlocksAether.carved_stairs, "carved_stairs");
		register(BlocksAether.angelic_stairs, "angelic_stairs");
		register(BlocksAether.hellfire_stairs, "hellfire_stairs");
		register(BlocksAether.holystone_brick_stairs, "holystone_brick_stairs");
		register(BlocksAether.holystone_stairs, "holystone_stairs");
		register(BlocksAether.mossy_holystone_stairs, "mossy_holystone_stairs");

		register(BlocksAether.skyroot_double_slab, "skyroot_double_slab");
		register(BlocksAether.carved_double_slab, "carved_double_slab");
		register(BlocksAether.angelic_double_slab, "angelic_double_slab");
		register(BlocksAether.hellfire_double_slab, "hellfire_double_slab");
		register(BlocksAether.holystone_double_slab, "holystone_double_slab");
		register(BlocksAether.mossy_holystone_double_slab, "mossy_holystone_double_slab");
		register(BlocksAether.holystone_brick_double_slab, "holystone_brick_double_slab");

		register(BlocksAether.skyroot_slab, "skyroot_slab");
		register(BlocksAether.carved_slab, "carved_slab");
		register(BlocksAether.angelic_slab, "angelic_slab");
		register(BlocksAether.hellfire_slab, "hellfire_slab");
		register(BlocksAether.holystone_slab, "holystone_slab");
		register(BlocksAether.mossy_holystone_slab, "mossy_holystone_slab");
		register(BlocksAether.holystone_brick_slab, "holystone_brick_slab");

		for (int meta = 0; meta < EnumCloudType.values().length; ++meta)
		{
			register(BlocksAether.aercloud, meta, EnumCloudType.getType(meta).getName());
		}

		for (int meta = 0; meta < EnumLeafType.values().length; ++meta)
		{
			register(BlocksAether.aether_leaves, meta, EnumLeafType.getType(meta).getName());
		}

		for (int meta = 0; meta < EnumCrystalType.values().length; ++meta)
		{
			register(BlocksAether.crystal_leaves, meta, EnumCrystalType.getType(meta).getName());
		}

		for (int meta = 0; meta < EnumCrystalType.values().length; ++meta)
		{
			register(BlocksAether.holiday_leaves, meta, EnumHolidayType.getType(meta).getName());
		}

		for (int meta = 0; meta < EnumLogType.values().length; ++meta)
		{
			register(BlocksAether.aether_log, meta, EnumLogType.getType(meta).getName());
		}

		for (int meta = 0; meta < EnumStoneType.values().length; ++meta)
		{
			String name = EnumStoneType.getType(meta).getName();

			register(BlocksAether.dungeon_block, meta, name);
			register(BlocksAether.locked_dungeon_block, meta, name);
			register(BlocksAether.dungeon_trap, meta, name);
		}

		registerMetadata(BlocksAether.aercloud, Aether.locate("cold_aercloud"), Aether.locate("blue_aercloud"), Aether.locate("golden_aercloud"), Aether.locate("pink_aercloud"));
		registerMetadata(BlocksAether.aether_leaves, Aether.locate("green_leaves"), Aether.locate("golden_oak_leaves"));
		registerMetadata(BlocksAether.holiday_leaves, Aether.locate("holiday_leaves"), Aether.locate("decorated_holiday_leaves"));
		registerMetadata(BlocksAether.crystal_leaves, Aether.locate("crystal_leaves"), Aether.locate("crystal_fruit_leaves"));
		registerMetadata(BlocksAether.aether_log, Aether.locate("skyroot_log"), Aether.locate("golden_oak_log"));
		registerMetadata(BlocksAether.dungeon_block, Aether.locate("carved_stone"), Aether.locate("sentry_stone"), Aether.locate("angelic_stone"), Aether.locate("light_angelic_stone"), Aether.locate("hellfire_stone"), Aether.locate("light_hellfire_stone"));
		registerMetadata(BlocksAether.locked_dungeon_block, Aether.locate("carved_stone"), Aether.locate("sentry_stone"), Aether.locate("angelic_stone"), Aether.locate("light_angelic_stone"), Aether.locate("hellfire_stone"), Aether.locate("light_hellfire_stone"));
		registerMetadata(BlocksAether.dungeon_trap, Aether.locate("carved_stone"), Aether.locate("sentry_stone"), Aether.locate("angelic_stone"), Aether.locate("light_angelic_stone"), Aether.locate("hellfire_stone"), Aether.locate("light_hellfire_stone"));

		AetherEntityRenderingRegistry.registerTileEntities();
	}

	public static void register(Block block, String model)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Aether.modAddress() + model, "inventory"));
	}

	public static void register(Block block, ItemMeshDefinition definition)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), definition);
	}

	public static void register(Block block, int meta, String model)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Aether.modAddress() + model, "inventory"));
	}

	public static void registerMetadata(Block block, ResourceLocation... model)
	{
		ModelBakery.registerItemVariants(Item.getItemFromBlock(block), model);
	}

}