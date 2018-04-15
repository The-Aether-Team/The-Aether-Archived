package com.legacy.aether.enchantment;

import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import com.legacy.aether.player.PlayerAether;

public class AetherEnchantmentHelper 
{

    public static ItemStack getEnchantedAccessory(Enchantment enchantment, PlayerAether playerAether)
    {
    	if (playerAether == null)
    	{
    		return ItemStack.EMPTY;
    	}

        List<ItemStack> list1 = Lists.<ItemStack>newArrayList();

        for (ItemStack itemstack : playerAether.getAccessoryStacks())
        {
            if (!itemstack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(enchantment, itemstack) > 0)
            {
                list1.add(itemstack);
            }
        }

        return list1.isEmpty() ? ItemStack.EMPTY : (ItemStack)list1.get(playerAether.thePlayer.getRNG().nextInt(list1.size()));
    }

}