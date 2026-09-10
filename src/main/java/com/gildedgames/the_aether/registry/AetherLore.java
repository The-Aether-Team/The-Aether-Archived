package com.gildedgames.the_aether.registry;

import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.items.block.ItemBlockEnchanter;

import cpw.mods.fml.common.registry.GameRegistry;

public class AetherLore {

    /**
     * Retained for API compatibility. No longer used by the lore slot: the
     * server computes lore validity deterministically (see SlotLore).
     */
    @Deprecated
    public static boolean hasKey;

    public static String getLoreEntryKey(ItemStack stack) {

        if (stack.getItem() instanceof ItemBlockEnchanter) {
            return "lore." + GameRegistry.findUniqueIdentifierFor(stack.getItem()).modId + ".enchanter";
        } else {
            return "lore." + GameRegistry.findUniqueIdentifierFor(stack.getItem()).modId
                + "."
                + stack.getUnlocalizedName()
                    .replace("item.", "")
                    .replace("tile.", "")
                    .replace(".name", "")
                    .replace(".", "_");
        }
    }
}
