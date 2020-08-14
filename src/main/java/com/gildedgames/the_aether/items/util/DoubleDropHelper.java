package com.gildedgames.the_aether.items.util;

import com.gildedgames.the_aether.items.tools.ItemSkyrootTool;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;

public class DoubleDropHelper 
{

	public static void dropBlock(EntityPlayer player, IBlockState state, BlockPos pos, PropertyBool property)
	{
		Block block = state.getBlock();

        player.addStat(StatList.getBlockStats(block));
        player.addExhaustion(0.025F);

        int size = state.getValue(property).equals(true) ? 2 : 1;
        ItemStack stack = player.inventory.getCurrentItem();
        boolean flag = true;

        if (stack == null || !(stack.getItem() instanceof ItemSkyrootTool))
        {
        	flag = false;
        }

        if (block.canSilkHarvest(player.world, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
        {
        	Block.spawnAsEntity(player.world, pos, new ItemStack(block.getDefaultState().getBlock()));
        	return;
        }

        if (!flag)
        {
        	block.dropBlockAsItem(player.world, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
        	return;
        }

        ItemSkyrootTool skyrootTool = (ItemSkyrootTool) stack.getItem();

        if (skyrootTool.getDestroySpeed(stack, state) == skyrootTool.getEffectiveSpeed())
        {
            for (int i = 0; i < size; ++i)
            {
            	block.dropBlockAsItem(player.world, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
            }
        }
        else
        {
        	block.dropBlockAsItem(player.world, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
        }
	}

}