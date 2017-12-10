package com.legacy.aether.client.renders.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.Aether;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.decorative.BlockAetherFenceGate;
import com.legacy.aether.blocks.natural.BlockAetherDirt;
import com.legacy.aether.blocks.natural.BlockAetherGrass;
import com.legacy.aether.blocks.natural.BlockAetherLeaves;
import com.legacy.aether.blocks.natural.BlockAetherLog;
import com.legacy.aether.blocks.natural.BlockCrystalLeaves;
import com.legacy.aether.blocks.natural.BlockHolidayLeaves;
import com.legacy.aether.blocks.natural.BlockHolystone;
import com.legacy.aether.blocks.natural.BlockQuicksoil;
import com.legacy.aether.blocks.natural.ore.BlockAmbrosiumOre;
import com.legacy.aether.blocks.util.EnumCloudType;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.client.renders.AetherEntityRenderingRegistry;
import com.legacy.aether.client.renders.items.util.AetherColor;

public class BlockRendering 
{

	public static void initialize()
	{
        registerBlockWithStateMapper(BlocksAether.aether_grass, (new StateMap.Builder()).ignore(BlockAetherGrass.double_drop).build());
        registerBlockWithStateMapper(BlocksAether.aether_dirt, (new StateMap.Builder()).ignore(BlockAetherDirt.double_drop).build());
        registerBlockWithStateMapper(BlocksAether.holystone, (new StateMap.Builder()).ignore(BlockHolystone.double_drop).build());
        registerBlockWithStateMapper(BlocksAether.mossy_holystone, (new StateMap.Builder()).ignore(BlockHolystone.double_drop).build());
        registerBlockWithStateMapper(BlocksAether.quicksoil, (new StateMap.Builder()).ignore(BlockQuicksoil.double_drop).build());
        registerBlockWithStateMapper(BlocksAether.ambrosium_ore, (new StateMap.Builder()).ignore(BlockAmbrosiumOre.double_drop).build());
        registerBlockWithStateMapper(BlocksAether.aether_log, (new StateMap.Builder()).ignore(BlockAetherLog.double_drop).build());
        registerBlockWithStateMapper(BlocksAether.aether_leaves, (new StateMap.Builder()).ignore(BlockAetherLeaves.CHECK_DECAY).ignore(BlockAetherLeaves.DECAYABLE).build());
        registerBlockWithStateMapper(BlocksAether.crystal_leaves, (new StateMap.Builder()).ignore(BlockCrystalLeaves.CHECK_DECAY).ignore(BlockCrystalLeaves.DECAYABLE).build());
        registerBlockWithStateMapper(BlocksAether.holiday_leaves, (new StateMap.Builder()).ignore(BlockHolidayLeaves.CHECK_DECAY).ignore(BlockHolidayLeaves.DECAYABLE).build());
        registerBlockWithStateMapper(BlocksAether.skyroot_fence_gate, (new StateMap.Builder()).ignore(BlockAetherFenceGate.POWERED).build());

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
		register(BlocksAether.purple_flower,  "purple_flower");
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

		registerColor(BlocksAether.aercloud);

		AetherEntityRenderingRegistry.registerTileEntities();
	}

	public static void registerBlockWithStateMapper(Block block)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getBlockModelShapes().registerBlockWithStateMapper(block, new StateMapperBase() 
		{
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return new ModelResourceLocation(state.getBlock().getRegistryName(), "normal");
			}
			
		});

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), new ItemMeshDefinition() 
		{
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) 
			{
				return new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory");
			}
		});
	}

	public static void registerBlockWithStateMapper(Block block, IStateMapper mapper)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getBlockModelShapes().registerBlockWithStateMapper(block, mapper);

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), new ItemMeshDefinition() 
		{
			@Override
			@SuppressWarnings("deprecation")
			public ModelResourceLocation getModelLocation(ItemStack stack) 
			{
				Block block = Block.getBlockFromItem(stack.getItem());
				IBlockState state = block.getStateFromMeta(stack.getMetadata());

				return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getBlockModelShapes().getBlockStateMapper().getVariants(block).get(state);
			}
		});
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

	public static void registerColor(Block item)
	{
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new AetherColor(Item.getItemFromBlock(item)), item);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new AetherColor(Item.getItemFromBlock(item)), item);
	}

	public static void registerMetadata(Block block, ResourceLocation... model)
	{
		ModelBakery.registerItemVariants(Item.getItemFromBlock(block), model);
	}

}