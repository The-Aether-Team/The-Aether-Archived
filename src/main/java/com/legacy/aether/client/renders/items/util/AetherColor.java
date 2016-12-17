package com.legacy.aether.client.renders.items.util;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.legacy.aether.server.items.ItemMoaEgg;
import com.legacy.aether.server.items.accessories.ItemAccessory;
import com.legacy.aether.server.items.armor.ItemAetherArmor;

public class AetherColor implements IItemColor
{

	private Item item;

	public AetherColor(Item item)
	{
		this.item = item;
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		if (this.item instanceof ItemAccessory)
		{
			return ((ItemAccessory)stack.getItem()).getColorFromItemStack(stack, 0);
		}

		if (this.item instanceof ItemAetherArmor)
		{
			return ((ItemAetherArmor)stack.getItem()).getColorization(stack);
		}

		if (this.item instanceof ItemMoaEgg)
		{
			return ((ItemMoaEgg)stack.getItem()).getColorFromItemStack(stack, 0);
		}

		return 0;
	}

}