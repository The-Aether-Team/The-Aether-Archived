package com.legacy.aether.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;

import com.legacy.aether.api.accessories.AccessoryType;
import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.enchantments.AetherEnchantmentFuel;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.api.freezables.AetherFreezableFuel;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.items.ItemsAether;

public class AetherRegistries 
{

	public static void initializeAccessories(IForgeRegistry<AetherAccessory> registry)
	{
		registry.register(new AetherAccessory(ItemsAether.leather_gloves, AccessoryType.GLOVE));
		registry.register(new AetherAccessory(ItemsAether.iron_gloves, AccessoryType.GLOVE));
		registry.register(new AetherAccessory(ItemsAether.golden_gloves, AccessoryType.GLOVE));
		registry.register(new AetherAccessory(ItemsAether.chain_gloves, AccessoryType.GLOVE));
		registry.register(new AetherAccessory(ItemsAether.diamond_gloves, AccessoryType.GLOVE));

		registry.register(new AetherAccessory(ItemsAether.zanite_gloves, AccessoryType.GLOVE));
		registry.register(new AetherAccessory(ItemsAether.gravitite_gloves, AccessoryType.GLOVE));
		registry.register(new AetherAccessory(ItemsAether.neptune_gloves, AccessoryType.GLOVE));
		registry.register(new AetherAccessory(ItemsAether.phoenix_gloves, AccessoryType.GLOVE));
		registry.register(new AetherAccessory(ItemsAether.obsidian_gloves, AccessoryType.GLOVE));
		registry.register(new AetherAccessory(ItemsAether.valkyrie_gloves, AccessoryType.GLOVE));

		registry.register(new AetherAccessory(ItemsAether.iron_ring, AccessoryType.RING));
		registry.register(new AetherAccessory(ItemsAether.golden_ring, AccessoryType.RING));
		registry.register(new AetherAccessory(ItemsAether.zanite_ring, AccessoryType.RING));
		registry.register(new AetherAccessory(ItemsAether.ice_ring, AccessoryType.RING));

		registry.register(new AetherAccessory(ItemsAether.iron_pendant, AccessoryType.PENDANT));
		registry.register(new AetherAccessory(ItemsAether.golden_pendant, AccessoryType.PENDANT));
		registry.register(new AetherAccessory(ItemsAether.zanite_pendant, AccessoryType.PENDANT));
		registry.register(new AetherAccessory(ItemsAether.ice_pendant, AccessoryType.PENDANT));

		registry.register(new AetherAccessory(ItemsAether.red_cape, AccessoryType.CAPE));
		registry.register(new AetherAccessory(ItemsAether.blue_cape, AccessoryType.CAPE));
		registry.register(new AetherAccessory(ItemsAether.yellow_cape, AccessoryType.CAPE));
		registry.register(new AetherAccessory(ItemsAether.white_cape, AccessoryType.CAPE));
		registry.register(new AetherAccessory(ItemsAether.swet_cape, AccessoryType.CAPE));
		registry.register(new AetherAccessory(ItemsAether.invisibility_cape, AccessoryType.CAPE));
		registry.register(new AetherAccessory(ItemsAether.agility_cape, AccessoryType.CAPE));
		registry.register(new AetherAccessory(ItemsAether.valkyrie_cape, AccessoryType.CAPE));
		
		registry.register(new AetherAccessory(ItemsAether.golden_feather, AccessoryType.MISC));
		registry.register(new AetherAccessory(ItemsAether.regeneration_stone, AccessoryType.MISC));
		registry.register(new AetherAccessory(ItemsAether.iron_bubble, AccessoryType.MISC));

		registry.register(new AetherAccessory(ItemsAether.repulsion_shield, AccessoryType.SHIELD));
	}

	public static void initializeEnchantments(IForgeRegistry<AetherEnchantment> registry)
	{
		registry.register(new AetherEnchantment(ItemsAether.skyroot_pickaxe, 225));
		registry.register(new AetherEnchantment(ItemsAether.skyroot_axe, 225));
		registry.register(new AetherEnchantment(ItemsAether.skyroot_shovel, 225));
		registry.register(new AetherEnchantment(ItemsAether.skyroot_sword, 225));

		registry.register(new AetherEnchantment(ItemsAether.holystone_pickaxe, 550));
		registry.register(new AetherEnchantment(ItemsAether.holystone_axe, 550));
		registry.register(new AetherEnchantment(ItemsAether.holystone_shovel, 550));
		registry.register(new AetherEnchantment(ItemsAether.holystone_sword, 550));

		registry.register(new AetherEnchantment(ItemsAether.zanite_pickaxe, 2250));
		registry.register(new AetherEnchantment(ItemsAether.zanite_axe, 2250));
		registry.register(new AetherEnchantment(ItemsAether.zanite_shovel, 2250));
		registry.register(new AetherEnchantment(ItemsAether.zanite_sword, 2250));

		registry.register(new AetherEnchantment(ItemsAether.gravitite_pickaxe, 5500));
		registry.register(new AetherEnchantment(ItemsAether.gravitite_axe, 5500));
		registry.register(new AetherEnchantment(ItemsAether.gravitite_shovel, 5500));
		registry.register(new AetherEnchantment(ItemsAether.gravitite_sword, 5500));

		registry.register(new AetherEnchantment(ItemsAether.zanite_helmet, 6000));
		registry.register(new AetherEnchantment(ItemsAether.zanite_chestplate, 6000));
		registry.register(new AetherEnchantment(ItemsAether.zanite_leggings, 6000));
		registry.register(new AetherEnchantment(ItemsAether.zanite_boots, 6000));

		registry.register(new AetherEnchantment(ItemsAether.gravitite_helmet, 13000));
		registry.register(new AetherEnchantment(ItemsAether.gravitite_chestplate, 13000));
		registry.register(new AetherEnchantment(ItemsAether.gravitite_leggings, 13000));
		registry.register(new AetherEnchantment(ItemsAether.gravitite_boots, 13000));

		registry.register(new AetherEnchantment(ItemsAether.dart, new ItemStack(ItemsAether.dart, 1, 2), 250));
		registry.register(new AetherEnchantment(ItemsAether.dart_shooter, new ItemStack(ItemsAether.dart_shooter, 1, 2), 500));

		registry.register(new AetherEnchantment(new ItemStack(ItemsAether.skyroot_bucket, 1, 2), new ItemStack(ItemsAether.skyroot_bucket, 1, 3), 1000));

		registry.register(new AetherEnchantment(BlocksAether.holystone, ItemsAether.healing_stone, 750));
		registry.register(new AetherEnchantment(BlocksAether.gravitite_ore, BlocksAether.enchanted_gravitite, 1000));
		registry.register(new AetherEnchantment(BlocksAether.quicksoil, BlocksAether.quicksoil_glass, 250));

		registry.register(new AetherEnchantment(ItemsAether.blue_berry, ItemsAether.enchanted_blueberry, 300));

		registry.register(new AetherEnchantment(Items.BOW, 4000));
		registry.register(new AetherEnchantment(Items.FISHING_ROD, 600));

		registry.register(new AetherEnchantment(Items.RECORD_11, ItemsAether.aether_tune, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_13, ItemsAether.aether_tune, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_BLOCKS, ItemsAether.aether_tune, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_CAT, ItemsAether.legacy, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_FAR, ItemsAether.aether_tune, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_MALL, ItemsAether.aether_tune, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_MELLOHI, ItemsAether.aether_tune, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_STAL, ItemsAether.aether_tune, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_STRAD, ItemsAether.aether_tune, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_WAIT, ItemsAether.aether_tune, 2500));
		registry.register(new AetherEnchantment(Items.RECORD_WARD, ItemsAether.aether_tune, 2500));

		registry.register(new AetherEnchantment(Items.WOODEN_PICKAXE, 225));
		registry.register(new AetherEnchantment(Items.WOODEN_AXE, 225));
		registry.register(new AetherEnchantment(Items.WOODEN_SHOVEL, 225));
		registry.register(new AetherEnchantment(Items.WOODEN_HOE, 225));

		registry.register(new AetherEnchantment(Items.STONE_PICKAXE, 550));
		registry.register(new AetherEnchantment(Items.STONE_AXE, 550));
		registry.register(new AetherEnchantment(Items.STONE_SHOVEL, 550));
		registry.register(new AetherEnchantment(Items.STONE_HOE, 550));

		registry.register(new AetherEnchantment(Items.IRON_PICKAXE, 2250));
		registry.register(new AetherEnchantment(Items.IRON_AXE, 2250));
		registry.register(new AetherEnchantment(Items.IRON_SHOVEL, 2250));
		registry.register(new AetherEnchantment(Items.IRON_HOE, 2250));

		registry.register(new AetherEnchantment(Items.DIAMOND_PICKAXE, 5500));
		registry.register(new AetherEnchantment(Items.DIAMOND_AXE, 5500));
		registry.register(new AetherEnchantment(Items.DIAMOND_SHOVEL, 5500));
		registry.register(new AetherEnchantment(Items.DIAMOND_HOE, 5500));

		registry.register(new AetherEnchantment(Items.LEATHER_HELMET, 550));
		registry.register(new AetherEnchantment(Items.LEATHER_CHESTPLATE, 550));
		registry.register(new AetherEnchantment(Items.LEATHER_LEGGINGS, 550));
		registry.register(new AetherEnchantment(Items.LEATHER_BOOTS, 550));

		registry.register(new AetherEnchantment(Items.IRON_HELMET, 6000));
		registry.register(new AetherEnchantment(Items.IRON_CHESTPLATE, 6000));
		registry.register(new AetherEnchantment(Items.IRON_LEGGINGS, 6000));
		registry.register(new AetherEnchantment(Items.IRON_BOOTS, 6000));

		registry.register(new AetherEnchantment(Items.GOLDEN_HELMET, 2250));
		registry.register(new AetherEnchantment(Items.GOLDEN_CHESTPLATE, 2250));
		registry.register(new AetherEnchantment(Items.GOLDEN_LEGGINGS, 2250));
		registry.register(new AetherEnchantment(Items.GOLDEN_BOOTS, 2250));

		registry.register(new AetherEnchantment(Items.CHAINMAIL_HELMET, 2250));
		registry.register(new AetherEnchantment(Items.CHAINMAIL_CHESTPLATE, 2250));
		registry.register(new AetherEnchantment(Items.CHAINMAIL_LEGGINGS, 2250));
		registry.register(new AetherEnchantment(Items.CHAINMAIL_BOOTS, 2250));

		registry.register(new AetherEnchantment(Items.DIAMOND_HELMET, 10000));
		registry.register(new AetherEnchantment(Items.DIAMOND_CHESTPLATE, 10000));
		registry.register(new AetherEnchantment(Items.DIAMOND_LEGGINGS, 10000));
		registry.register(new AetherEnchantment(Items.DIAMOND_BOOTS, 10000));
	}

	public static void initializeEnchantmentFuel(IForgeRegistry<AetherEnchantmentFuel> registry)
	{
		registry.register(new AetherEnchantmentFuel(ItemsAether.ambrosium_shard, 500));
	}

	public static void initializeFreezables(IForgeRegistry<AetherFreezable> registry)
	{
		registry.register(new AetherFreezable(BlocksAether.aercloud, new ItemStack(BlocksAether.aercloud, 1, 1), 100));
		registry.register(new AetherFreezable(BlocksAether.aether_leaves, BlocksAether.crystal_leaves, 150));
		registry.register(new AetherFreezable(new ItemStack(ItemsAether.skyroot_bucket, 1, 1), Blocks.ICE, 500));
		registry.register(new AetherFreezable(ItemsAether.ascending_dawn, ItemsAether.welcoming_skies, 800));
		registry.register(new AetherFreezable(Blocks.ICE, Blocks.PACKED_ICE, 750));
		registry.register(new AetherFreezable(Items.WATER_BUCKET, Blocks.ICE, 500));
		registry.register(new AetherFreezable(Items.LAVA_BUCKET, Blocks.OBSIDIAN, 500));
		registry.register(new AetherFreezable(ItemsAether.iron_ring, ItemsAether.ice_ring, 2500));
		registry.register(new AetherFreezable(ItemsAether.golden_ring, ItemsAether.ice_ring, 2500));
		registry.register(new AetherFreezable(ItemsAether.iron_pendant, ItemsAether.ice_pendant, 2500));
		registry.register(new AetherFreezable(ItemsAether.golden_pendant, ItemsAether.ice_pendant, 2500));
	}

	public static void initializeFreezableFuel(IForgeRegistry<AetherFreezableFuel> registry)
	{
		registry.register(new AetherFreezableFuel(BlocksAether.icestone, 500));
	}

}