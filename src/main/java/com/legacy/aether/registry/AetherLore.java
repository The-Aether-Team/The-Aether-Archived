package com.legacy.aether.registry;

import com.legacy.aether.items.block.ItemEnchanter;
import net.minecraft.item.ItemStack;

public class AetherLore 
{

	public static String getLoreEntryKey(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemEnchanter)
		{
			return "lore." + stack.getItem().getRegistryName().getNamespace() + ".enchanter";
		}
		else
		{
			return "lore." + stack.getItem().getRegistryName().getNamespace() + "." + stack.getTranslationKey().replace("item.", "").replace("tile.", "").replace(".name", "").replace(".", "_");
		}
	}

}