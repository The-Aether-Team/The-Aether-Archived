package com.legacy.aether.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.containers.slots.SlotIncubator;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.tile_entities.TileEntityIncubator;

public class ContainerIncubator extends Container
{

	private int progress, powerRemaining;

    private TileEntityIncubator incubator;

    public ContainerIncubator(EntityPlayer player, InventoryPlayer inventory, TileEntityIncubator incubator)
    {
        this.incubator = incubator;

        this.addSlotToContainer(new SlotIncubator(incubator, 1, 73, 17, player));
        this.addSlotToContainer(new Slot(incubator, 0, 73, 53));

        for (int j = 0; j < 3; ++j)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for (int j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(inventory, j, 8 + j * 18, 142));
        }
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);

        listener.sendAllWindowProperties(this, this.incubator);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.progress != this.incubator.getField(0))
            {
                icontainerlistener.sendProgressBarUpdate(this, 0, this.incubator.getField(0));
            }
            else if (this.powerRemaining != this.incubator.getField(1))
            {
                icontainerlistener.sendProgressBarUpdate(this, 1, this.incubator.getField(1));
            }
        }

        this.progress = this.incubator.getField(0);
        this.powerRemaining = this.incubator.getField(1);
    }

    @Override
    public void updateProgressBar(int id, int data)
    {
        this.incubator.setField(id, data);
    }

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return this.incubator.isUsableByPlayer(entityplayer);
    }

	@Override
    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index != 1 && index != 0)
            {
				if (itemstack.getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch) && this.mergeItemStack(itemstack1, 1, 2, false))
				{
					return itemstack;
				}
				else if (itemstack.getItem() == ItemsAether.moa_egg && this.mergeItemStack(itemstack1, 0, 1, false))
				{
					return itemstack;
				}
				else if (index >= 2 && index < 29)
                {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 29 && index < 37 && !this.mergeItemStack(itemstack1, 2, 29, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 2, 38, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0)
            {
                slot.putStack((ItemStack)ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(entityplayer, itemstack1);
        }

        return itemstack;
    }

}