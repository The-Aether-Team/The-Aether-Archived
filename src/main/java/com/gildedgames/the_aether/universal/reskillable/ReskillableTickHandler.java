package com.gildedgames.the_aether.universal.reskillable;

import codersafterdark.reskillable.base.ConfigHandler;
import codersafterdark.reskillable.base.LevelLockHandler;
import codersafterdark.reskillable.network.MessageLockedItem;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;
import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ReskillableTickHandler
{
    @SubscribeEvent
    public void tickHandler(TickEvent.PlayerTickEvent event)
    {
        EntityPlayer player = event.player;
        if (skipPlayer(player))
        {
            return;
        }

        PlayerAether playerAether = (PlayerAether) AetherAPI.getInstance().get(player);
        IAccessoryInventory accessoryInventory = playerAether.getAccessoryInventory();

        for (int i = 0; i < accessoryInventory.getSizeInventory(); i++)
        {
            ItemStack stack = accessoryInventory.getStackInSlot(i);

            if (!LevelLockHandler.canPlayerUseItem(player, stack))
            {
                if (!player.inventory.addItemStackToInventory(stack))
                {
                    player.dropItem(stack, false);
                }

                accessoryInventory.setInventorySlotContents(i, ItemStack.EMPTY);
                LevelLockHandler.tellPlayer(player, stack, MessageLockedItem.MSG_ARMOR_EQUIP_LOCKED);
            }
        }
    }

    public static boolean skipPlayer(EntityPlayer player)
    {
        return player == null || !ConfigHandler.enforceOnCreative && player.isCreative() || !ConfigHandler.enforceFakePlayers && LevelLockHandler.isFake(player);
    }
}
