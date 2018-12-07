package com.legacy.aether.items.util;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Sets;
import com.legacy.aether.blocks.BlocksAether;

public enum EnumAetherToolType
{

	PICKAXE(Sets.newHashSet(new Block[]
			{
			Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab,
			Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore,
			Blocks.iron_block, Blocks.coal_ore, Blocks.gold_ore, Blocks.gold_block,
			Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack,
			Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore,
			Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail, Blocks.mob_spawner,
			BlocksAether.holystone, BlocksAether.holystone_brick, BlocksAether.mossy_holystone,
			BlocksAether.enchanter, BlocksAether.incubator, BlocksAether.enchanter, BlocksAether.ambrosium_ore,
			BlocksAether.icestone, BlocksAether.aerogel, BlocksAether.carved_stone, BlocksAether.angelic_stone,
			BlocksAether.hellfire_stone, BlocksAether.sentry_stone, BlocksAether.light_angelic_stone,
			BlocksAether.light_hellfire_stone
			}))
			{
		@Override
		public boolean canHarvestBlock(ToolMaterial toolMaterial, Block state)
		{
			Block block = state;

			if (block == BlocksAether.zanite_ore || block == BlocksAether.zanite_block || block == BlocksAether.icestone)
			{
				return toolMaterial.getHarvestLevel() >= 1;
			}
			else if (block == BlocksAether.gravitite_ore || block == BlocksAether.enchanted_gravitite)
			{
				return toolMaterial.getHarvestLevel() >= 2;
			}

			return block == Blocks.obsidian ? toolMaterial.getHarvestLevel() == 3 : (block != Blocks.diamond_block && block != Blocks.diamond_ore ? (block != Blocks.emerald_ore && block != Blocks.emerald_block ? (block != Blocks.gold_block && block != Blocks.gold_ore ? (block != Blocks.iron_block && block != Blocks.iron_ore ? (block != Blocks.lapis_block && block != Blocks.lapis_ore ? (block != Blocks.redstone_ore && block != Blocks.lit_redstone_ore ? (state.getMaterial() == Material.rock ? true : (state.getMaterial() == Material.iron ? true : state.getMaterial() == Material.anvil)) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2);
		}

		@Override
		public float getStrVsBlock(ItemStack stack, Block block)
		{
			return block != null && (block.getMaterial() == Material.iron || block.getMaterial() == Material.anvil || block.getMaterial() == Material.rock) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(stack, block);
		}
			},
	SHOVEL(Sets.newHashSet(new Block[]
			{
			Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow,
			Blocks.snow_layer, Blocks.clay, Blocks.farmland, Blocks.soul_sand,
			Blocks.mycelium, BlocksAether.aether_dirt, BlocksAether.aether_grass,
			BlocksAether.aercloud, BlocksAether.enchanted_aether_grass, BlocksAether.quicksoil
			}))
			{
		@Override
		public boolean canHarvestBlock(ToolMaterial toolMaterial, Block block)
		{
			return block == Blocks.snow ? true : block == Blocks.snow_layer;
		}
			},

	AXE(Sets.newHashSet(new Block[]
			{
			Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.chest,
			Blocks.double_stone_slab, Blocks.stone_slab, Blocks.pumpkin,
			Blocks.lit_pumpkin, BlocksAether.skyroot_log, BlocksAether.golden_oak_log, BlocksAether.skyroot_planks,
			BlocksAether.chest_mimic
			}))
			{
		@Override
		public float getStrVsBlock(ItemStack stack, Block block)
		{
			return block != null && (block.getMaterial() == Material.wood || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(stack, block);
		}
			};

		private Set<Block> toolBlockSet;

		public float efficiencyOnProperMaterial = 4.0F;

		EnumAetherToolType(Set<Block> toolBlockSet)
		{
			this.toolBlockSet = toolBlockSet;
		}

		public Set<Block> getToolBlockSet()
		{
			return this.toolBlockSet;
		}

		public boolean canHarvestBlock(ToolMaterial toolMaterial, Block block)
		{
			return false;
		}

		public float getStrVsBlock(ItemStack stack, Block block)
		{
			return this.toolBlockSet.contains(block) ? this.efficiencyOnProperMaterial : 1.0F;
		}

}