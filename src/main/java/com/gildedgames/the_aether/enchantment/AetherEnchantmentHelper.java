package com.gildedgames.the_aether.enchantment;

import java.util.List;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import com.gildedgames.the_aether.api.player.IPlayerAether;

public class AetherEnchantmentHelper
{

	public static ItemStack getEnchantedAccessory(Enchantment enchantment, IPlayerAether playerAether)
	{
		if (playerAether == null)
		{
			return ItemStack.EMPTY;
		}

		List<ItemStack> list1 = Lists.<ItemStack> newArrayList();

		for (int i = 0; i < playerAether.getAccessoryInventory().getSizeInventory(); ++i)
		{
			ItemStack accessory = playerAether.getAccessoryInventory().getStackInSlot(i);

			if (EnchantmentHelper.getEnchantmentLevel(enchantment, accessory) > 0)
			{
				list1.add(accessory);
			}
		}

		return list1.isEmpty() ? ItemStack.EMPTY : (ItemStack) list1.get(playerAether.getEntity().getRNG().nextInt(list1.size()));
	}

}