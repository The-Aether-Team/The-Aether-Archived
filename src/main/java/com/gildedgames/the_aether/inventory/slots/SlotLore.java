package com.gildedgames.the_aether.inventory.slots;

import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.network.packets.PacketCheckKey;
import com.gildedgames.the_aether.registry.AetherLore;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLore extends Slot
{
    public SlotLore(IInventory inv, int slot, int x, int y) {
        super(inv, slot, x, y);
    }

    public boolean isItemValid(ItemStack stack)
    {
        if (FMLCommonHandler.instance().getSide().isClient())
        {
            if (!I18n.format(AetherLore.getLoreEntryKey(stack)).contains("lore."))
            {
                AetherLore.hasKey = true;
                AetherNetwork.sendToServer(new PacketCheckKey(true));
            }
            else
            {
                AetherLore.hasKey = false;
                AetherNetwork.sendToServer(new PacketCheckKey(false));
            }
        }

        return AetherLore.hasKey;
    }
}
