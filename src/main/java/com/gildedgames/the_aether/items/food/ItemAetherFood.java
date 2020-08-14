package com.gildedgames.the_aether.items.food;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import com.gildedgames.the_aether.items.ItemsAether;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;

import net.minecraft.item.ItemStack;

public class ItemAetherFood extends ItemFood
{
	public ItemAetherFood(int healAmmount) 
	{
		super(healAmmount, false);
		this.setCreativeTab(AetherCreativeTabs.food);
	}

	public ItemAetherFood(int healAmmount, float saturationAmmount) 
	{
		super(healAmmount, saturationAmmount, false);
		this.setCreativeTab(AetherCreativeTabs.food);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return stack.getItem() == ItemsAether.enchanted_blueberry ? EnumRarity.RARE : super.getRarity(stack);
	}

}