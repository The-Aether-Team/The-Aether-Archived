package com.gildedgames.the_aether.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.gildedgames.the_aether.registry.AetherLore;

public class SlotLore extends Slot {

    public SlotLore(IInventory inv, int slot, int x, int y) {
        super(inv, slot, x, y);
    }

    public boolean isItemValid(ItemStack stack) {
        // The lore check is deterministic and language-independent enough to run
        // on both sides: an item is placeable only if it actually has a lore
        // entry. Previously the server relied on a client-controlled static
        // (AetherLore.hasKey) that any player could flip for everyone.
        String key = AetherLore.getLoreEntryKey(stack);

        return !StatCollector.translateToLocal(key)
            .contains("lore.");
    }
}
