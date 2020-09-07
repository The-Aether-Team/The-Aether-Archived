package com.gildedgames.the_aether.registry;

import com.gildedgames.the_aether.items.block.ItemBlockEnchanter;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

public class AetherLore {

	public static boolean hasKey;

	public static String getLoreEntryKey(ItemStack stack) {

		if (stack.getItem() instanceof ItemBlockEnchanter)
		{
			return "lore." + GameRegistry.findUniqueIdentifierFor(stack.getItem()).modId + ".enchanter";
		}
		else
		{
			return "lore." + GameRegistry.findUniqueIdentifierFor(stack.getItem()).modId + "." + stack.getUnlocalizedName().replace("item.", "").replace("tile.", "").replace(".name", "").replace(".", "_");
		}
	}
}