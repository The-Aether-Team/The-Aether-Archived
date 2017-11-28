package com.legacy.aether.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.containers.slots.SlotIncubator;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.tile_entities.TileEntityIncubator;

public class ContainerIncubator extends Container
{

    private TileEntityIncubator incubator;

    public ContainerIncubator(EntityPlayer player, InventoryPlayer inventoryplayer, IInventory tileentityIncubator)
    {
        this.incubator = (TileEntityIncubator) tileentityIncubator;
        this.addSlotToContainer(new SlotIncubator((TileEntityIncubator) tileentityIncubator, 1, 73, 17, player));
        this.addSlotToContainer(new Slot(tileentityIncubator, 0, 73, 53));
        int j;

        for (j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(inventoryplayer, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }
    }

	public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.incubator.isUseableByPlayer(entityplayer);
    }

    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (i > 1)
            {
				if (itemstack.getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch) && this.mergeItemStack(itemstack1, 0, 2, true))
				{
					return itemstack;
				}

				if (itemstack.getItem() == ItemsAether.moa_egg && this.mergeItemStack(itemstack1, 0, 1, true))
				{
					return itemstack;
				}
            }
            else
            {
                if (!this.mergeItemStack(itemstack1, 2, 38, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(entityplayer, itemstack1);
        }

        return itemstack;
    }

}