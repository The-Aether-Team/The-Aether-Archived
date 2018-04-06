package com.legacy.aether.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.legacy.aether.containers.inventory.InventoryLore;
import com.legacy.aether.registry.AetherLore;

public class ContainerLore extends Container
{

    public IInventory loreSlot;

    public ContainerLore(InventoryPlayer inventory)
    {
        this.loreSlot = new InventoryLore(inventory.player);

        this.addSlotToContainer(new Slot(this.loreSlot, 0, 104, -4));

        for (int j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(inventory, k + j * 9 + 9, 48 + k * 18, 113 + j * 18));
            }
        }

        for (int j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(inventory, j, 48 + j * 18, 171));
        }
    }

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotNumber == 0)
			{
				if (!this.mergeItemStack(itemstack1, 1, 37, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
            else if (slotNumber != 0)
            {
            	if (!AetherLore.getLoreEntry(itemstack).equals("missingno"))
            	{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
            	}
            	else if (slotNumber >= 1 && slotNumber < 28)
				{
					if (!this.mergeItemStack(itemstack1, 28, 37, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (slotNumber >= 28 && slotNumber < 37 && !this.mergeItemStack(itemstack1, 1, 28, false))
				{
					return ItemStack.EMPTY;
				}
            }
			else if (!this.mergeItemStack(itemstack1, 1, 37, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);

		}

		return itemstack;
	}

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