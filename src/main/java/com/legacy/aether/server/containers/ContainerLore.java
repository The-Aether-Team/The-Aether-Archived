package com.legacy.aether.server.containers;

import com.legacy.aether.server.containers.inventory.InventoryLore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLore extends Container
{

    public IInventory loreSlot;

    public ContainerLore(InventoryPlayer inventory)
    {
        int j;

        for (j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(inventory, k + j * 9 + 9, 48 + k * 18, 113 + j * 18));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(inventory, j, 48 + j * 18, 171));
        }

        this.loreSlot = new InventoryLore(inventory.player);

        this.addSlotToContainer(new Slot(this.loreSlot, 0, 82, 66));
    }

    protected void retrySlotClick(int var1, int var2, boolean var3, EntityPlayer var4) {}

    public void onContainerClosed(EntityPlayer entityplayer)
    {
        ItemStack item = this.loreSlot.getStackInSlot(0);

        if (item != null)
        {
        	entityplayer.dropItem(item, false);
        }

        super.onContainerClosed(entityplayer);
    }
    
    public boolean canInteractWith(EntityPlayer var1)
    {
        return true;
    }

}