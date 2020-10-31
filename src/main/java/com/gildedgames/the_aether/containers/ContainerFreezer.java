package com.gildedgames.the_aether.containers;

import com.gildedgames.the_aether.containers.slots.SlotFreezer;
import com.gildedgames.the_aether.tile_entities.TileEntityFreezer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.api.AetherAPI;

public class ContainerFreezer extends Container
{

	private TileEntityFreezer freezer;

	public int progress, ticksRequired, powerRemaining;

	public ContainerFreezer(InventoryPlayer par1InventoryPlayer, TileEntityFreezer tileEntityFreezer)
	{
		this.freezer = tileEntityFreezer;

		this.addSlotToContainer(new Slot(tileEntityFreezer, 0, 56, 17));
		this.addSlotToContainer(new Slot(tileEntityFreezer, 1, 56, 53));
		this.addSlotToContainer(new SlotFreezer(tileEntityFreezer, 2, 116, 35));

		int i;

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
		}
	}

    @Override
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);

        listener.sendAllWindowProperties(this, this.freezer);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.progress != this.freezer.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.freezer.getField(0));
            }
            else if (this.powerRemaining != this.freezer.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.freezer.getField(1));
            }
            else if (this.ticksRequired != this.freezer.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.freezer.getField(2));
            }
        }

        this.progress = this.freezer.getField(0);
        this.powerRemaining = this.freezer.getField(1);
        this.ticksRequired = this.freezer.getField(2);
    }

	@Override
	public void updateProgressBar(int id, int value)
	{
		this.freezer.setField(id, value);
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.freezer.isUsableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 2)
			{
				if (!this.mergeItemStack(itemstack1, 3, 39, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if (AetherAPI.getInstance().hasFreezable(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (AetherAPI.getInstance().isFreezableFuel(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 1, 2, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 3 && par2 < 30)
				{
					if (!this.mergeItemStack(itemstack1, 30, 39, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 3, 39, false))
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

			slot.onTake(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

}