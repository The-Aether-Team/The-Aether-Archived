package com.legacy.aether.server.items.util;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Sets;
import com.legacy.aether.server.blocks.BlocksAether;

public enum EnumAetherToolType
{

	PICKAXE(Sets.newHashSet(new Block[]
			{
			Blocks.COBBLESTONE, Blocks.DOUBLE_STONE_SLAB, Blocks.STONE_SLAB,
			Blocks.STONE, Blocks.SANDSTONE, Blocks.MOSSY_COBBLESTONE, Blocks.IRON_ORE,
			Blocks.IRON_BLOCK, Blocks.COAL_ORE, Blocks.GOLD_ORE, Blocks.GOLD_BLOCK,
			Blocks.DIAMOND_ORE, Blocks.DIAMOND_BLOCK, Blocks.ICE, Blocks.NETHERRACK,
			Blocks.LAPIS_ORE, Blocks.LAPIS_BLOCK, Blocks.REDSTONE_ORE, Blocks.LIT_REDSTONE_ORE,
			Blocks.RAIL, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.ACTIVATOR_RAIL, Blocks.MOB_SPAWNER,
			BlocksAether.holystone, BlocksAether.holystone_brick, BlocksAether.mossy_holystone,
			BlocksAether.enchanter, BlocksAether.incubator, BlocksAether.enchanter, BlocksAether.ambrosium_ore,
			BlocksAether.icestone, BlocksAether.aerogel, BlocksAether.dungeon_block
			}))
			{
		@Override
		public boolean canHarvestBlock(ToolMaterial toolMaterial, IBlockState state)
		{
			Block block = state.getBlock();

			if (block == BlocksAether.zanite_ore || block == BlocksAether.zanite_block || block == BlocksAether.icestone)
			{
				return toolMaterial.getHarvestLevel() >= 1;
			}
			else if (block == BlocksAether.gravitite_ore || block == BlocksAether.enchanted_gravitite)
			{
				return toolMaterial.getHarvestLevel() >= 2;
			}

			return block == Blocks.OBSIDIAN ? toolMaterial.getHarvestLevel() == 3 : (block != Blocks.DIAMOND_BLOCK && block != Blocks.DIAMOND_ORE ? (block != Blocks.EMERALD_ORE && block != Blocks.EMERALD_BLOCK ? (block != Blocks.GOLD_BLOCK && block != Blocks.GOLD_ORE ? (block != Blocks.IRON_BLOCK && block != Blocks.IRON_ORE ? (block != Blocks.LAPIS_BLOCK && block != Blocks.LAPIS_ORE ? (block != Blocks.REDSTONE_ORE && block != Blocks.LIT_REDSTONE_ORE ? (state.getMaterial() == Material.ROCK ? true : (state.getMaterial() == Material.IRON ? true : state.getMaterial() == Material.ANVIL)) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2);
		}

		@Override
		public float getStrVsBlock(ItemStack stack, IBlockState block)
		{
			return block != null && (block.getMaterial() == Material.IRON || block.getMaterial() == Material.ANVIL || block.getMaterial() == Material.ROCK) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(stack, block);
		}
			},
	SHOVEL(Sets.newHashSet(new Block[]
			{
			Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW,
			Blocks.SNOW_LAYER, Blocks.CLAY, Blocks.FARMLAND, Blocks.SOUL_SAND,
			Blocks.MYCELIUM, BlocksAether.aether_dirt, BlocksAether.aether_grass,
			BlocksAether.aercloud, BlocksAether.enchanted_aether_grass, BlocksAether.quicksoil
			}))
			{
		@Override
		public boolean canHarvestBlock(ToolMaterial toolMaterial, IBlockState block)
		{
			return block.getBlock() == Blocks.SNOW ? true : block.getBlock() == Blocks.SNOW_LAYER;
		}
			},

	AXE(Sets.newHashSet(new Block[]
			{
			Blocks.PLANKS, Blocks.BOOKSHELF, Blocks.LOG, Blocks.CHEST,
			Blocks.DOUBLE_STONE_SLAB, Blocks.STONE_SLAB, Blocks.PUMPKIN,
			Blocks.LIT_PUMPKIN, BlocksAether.aether_log, BlocksAether.skyroot_plank,
			BlocksAether.chest_mimic
			}))
			{
		@Override
		public float getStrVsBlock(ItemStack stack, IBlockState block)
		{
			return block != null && (block.getMaterial() == Material.WOOD || block.getMaterial() == Material.PLANTS || block.getMaterial() == Material.VINE) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(stack, block);
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

		public boolean canHarvestBlock(ToolMaterial toolMaterial, IBlockState block)
		{
			return false;
		}

		public float getStrVsBlock(ItemStack stack, IBlockState block)
		{
			return this.toolBlockSet.contains(block.getBlock()) ? this.efficiencyOnProperMaterial : 1.0F;
		}

}