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

	PICKAXE(2.0F, Sets.newHashSet(new Block[]
			{
			Blocks.COBBLESTONE, Blocks.DOUBLE_STONE_SLAB, Blocks.STONE_SLAB,
			Blocks.STONE, Blocks.SANDSTONE, Blocks.MOSSY_COBBLESTONE, Blocks.IRON_ORE,
			Blocks.IRON_BLOCK, Blocks.COAL_ORE, Blocks.GOLD_ORE, Blocks.GOLD_BLOCK,
			Blocks.DIAMOND_ORE, Blocks.DIAMOND_BLOCK, Blocks.ICE, Blocks.NETHERRACK,
			Blocks.LAPIS_ORE, Blocks.LAPIS_BLOCK, Blocks.REDSTONE_ORE, Blocks.LIT_REDSTONE_ORE,
			Blocks.RAIL, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.ACTIVATOR_RAIL,
			BlocksAether.holystone, BlocksAether.holystone_brick, BlocksAether.mossy_holystone,
			BlocksAether.enchanter, BlocksAether.incubator, BlocksAether.enchanter, BlocksAether.ambrosium_ore,
			BlocksAether.icestone, BlocksAether.aerogel, BlocksAether.dungeon_block
			}))
			{
		@Override
		public boolean canHarvestBlock(ToolMaterial toolMaterial, IBlockState block)
		{
			if (block.getBlock() == BlocksAether.zanite_ore || block.getBlock() == BlocksAether.zanite_block)
			{
				return toolMaterial.getHarvestLevel() >= 1;
			}
			else if (block.getBlock() == BlocksAether.gravitite_ore || block.getBlock() == BlocksAether.enchanted_gravitite)
			{
				return toolMaterial.getHarvestLevel() >= 2;
			}

			return block.getBlock() == Blocks.OBSIDIAN ? toolMaterial.getHarvestLevel() == 3 : (block.getBlock() != Blocks.DIAMOND_BLOCK && block.getBlock() != Blocks.DIAMOND_ORE ? (block.getBlock() != Blocks.EMERALD_ORE && block.getBlock() != Blocks.EMERALD_BLOCK ? (block.getBlock() != Blocks.GOLD_BLOCK && block.getBlock() != Blocks.GOLD_ORE ? (block.getBlock() != Blocks.IRON_BLOCK && block.getBlock() != Blocks.IRON_ORE ? (block.getBlock() != Blocks.LAPIS_BLOCK && block.getBlock() != Blocks.LAPIS_ORE ? (block.getBlock() != Blocks.REDSTONE_ORE && block.getBlock() != Blocks.LIT_REDSTONE_ORE ? (block.getMaterial() == Material.ROCK ? true : (block.getMaterial() == Material.IRON ? true : block.getMaterial() == Material.ANVIL)) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 1) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 2);
		}

		@Override
		public float getStrVsBlock(ItemStack stack, IBlockState block)
		{
			return block != null && (block.getMaterial() == Material.IRON || block.getMaterial() == Material.ANVIL || block.getMaterial() == Material.ROCK) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(stack, block);
		}
			},
	SHOVEL(1.0F, Sets.newHashSet(new Block[]
			{
			Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW,
			Blocks.SNOW_LAYER, Blocks.CLAY, Blocks.FARMLAND, Blocks.SOUL_SAND,
			Blocks.MYCELIUM, BlocksAether.aether_dirt, BlocksAether.aether_grass,
			BlocksAether.aercloud, BlocksAether.enchanted_aether_grass
			}))
			{
		@Override
		public boolean canHarvestBlock(ToolMaterial toolMaterial, IBlockState block)
		{
			return block.getBlock() == Blocks.SNOW ? true : block.getBlock() == Blocks.SNOW_LAYER;
		}
			},

	AXE(3.0F, Sets.newHashSet(new Block[]
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

		private float damageVsEntity;

		private Set<Block> toolBlockSet;

		public float efficiencyOnProperMaterial = 4.0F;

		EnumAetherToolType(float damageVsEntity, Set<Block> toolBlockSet)
		{
			this.damageVsEntity = damageVsEntity;
			this.toolBlockSet = toolBlockSet;
		}

		public float getDamageVsEntity()
		{
			return this.damageVsEntity;
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