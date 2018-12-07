package com.legacy.aether.items.util;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;

import com.legacy.aether.items.tools.ItemSkyrootTool;

public class DoubleDropHelper 
{

	public static void dropBlock(EntityPlayer player, int x, int y, int z, Block block, int meta)
	{
        player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(block)], 1);
        player.addExhaustion(0.025F);

        int size = meta == 0 ? 2 : 1;
        ItemStack stack = player.inventory.getCurrentItem();
        boolean flag = true;

        if (stack == null || !(stack.getItem() instanceof ItemSkyrootTool))
        {
        	flag = false;
        }

        if (block.canSilkHarvest(player.worldObj, player, x, y, z, meta) && EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack) > 0)
        {
        	block.harvestBlock(player.worldObj, player, x, y, z, meta);

        	return;
        }

        if (!flag)
        {
        	block.dropBlockAsItem(player.worldObj, x, y, z, meta, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));

        	return;
        }

        ItemSkyrootTool skyrootTool = (ItemSkyrootTool) stack.getItem();

        if (skyrootTool.getDigSpeed(stack, block, meta) == skyrootTool.getEffectiveSpeed())
        {
            for (int i = 0; i < size; ++i)
            {
            	block.dropBlockAsItem(player.worldObj, x, y, z, meta, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
            }
        }
        else
        {
        	block.dropBlockAsItem(player.worldObj, x, y, z, meta, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
        }
	}

}