package com.legacy.aether.items.util;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;

import com.legacy.aether.items.tools.ItemSkyrootTool;

public class DoubleDropHelper
{

	public static void dropBlock(EntityPlayer player, IBlockState state, BlockPos pos, PropertyBool property)
	{
		Block block = state.getBlock();

        player.addStat(StatList.getBlockStats(block));
        player.addExhaustion(0.025F);

        ItemStack stack = player.inventory.getCurrentItem();
        boolean toolIsSkyroot = stack.getItem() instanceof ItemSkyrootTool;
        boolean silkHarvesting = block.canSilkHarvest(player.world, pos, state, player) &&
			EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0;

        int blocksToDrop = toolIsSkyroot && state.getValue(property).equals(true) ? 2 : 1;

        if (blocksToDrop > 1)
		{
			ItemSkyrootTool skyrootTool = (ItemSkyrootTool) stack.getItem();
			if (skyrootTool.getDestroySpeed(stack, state) != skyrootTool.getEffectiveSpeed())
			{
				blocksToDrop = 1;
			}
		}

        for (int i = 0; i < blocksToDrop; ++i)
		{
			if (silkHarvesting)
			{
				Block.spawnAsEntity(player.world, pos, new ItemStack(block, 1, block.getMetaFromState(state.withProperty(property, false))));
			}
			else
			{
				block.dropBlockAsItem(player.world, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
			}
		}
	}
}