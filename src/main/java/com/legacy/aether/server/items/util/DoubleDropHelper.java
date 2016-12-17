package com.legacy.aether.server.items.util;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;

import com.legacy.aether.server.items.tools.ItemSkyrootTool;

public class DoubleDropHelper 
{

	public static void dropBlock(EntityPlayer player, IBlockState state, BlockPos pos, PropertyBool property)
	{
        player.addStat(StatList.getBlockStats(state.getBlock()));
        player.addExhaustion(0.025F);

        int size = state.getValue(property).equals(true) ? 2 : 1;
        ItemStack stack = player.inventory.getCurrentItem();

        if (stack == null || !(stack.getItem() instanceof ItemSkyrootTool))
        {
        	state.getBlock().dropBlockAsItem(player.worldObj, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
        	return;
        }

        if (state.getBlock() != Blocks.AIR && state.getBlock().canHarvestBlock(player.worldObj, pos, player))
        {
        	boolean tool = state.getBlock().isToolEffective((String) stack.getItem().getToolClasses(stack).toArray()[0], state);

        	if (!tool)
        	{
            	state.getBlock().dropBlockAsItem(player.worldObj, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
        		return;
        	}

            for (int i = 0; i < size; ++i)
            {
            	state.getBlock().dropBlockAsItem(player.worldObj, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
            }
        }
	}

}