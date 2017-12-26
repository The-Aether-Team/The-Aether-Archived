package com.legacy.aether.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.containers.slots.SlotEnchanter;
import com.legacy.aether.tile_entities.TileEntityEnchanter;

public class ContainerEnchanter extends Container
{

	private TileEntityEnchanter enchanter;

	public int progress, ticksRequired, powerRemaining;

	public ContainerEnchanter(InventoryPlayer par1InventoryPlayer, TileEntityEnchanter tileEntityEnchanter)
	{
		this.enchanter = tileEntityEnchanter;
		this.addSlotToContainer(new Slot(tileEntityEnchanter, 0, 56, 17));
		this.addSlotToContainer(new Slot(tileEntityEnchanter, 1, 56, 53));
		this.addSlotToContainer(new SlotEnchanter(tileEntityEnchanter, 2, 116, 35));
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

        listener.sendAllWindowProperties(this, this.enchanter);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.progress != this.enchanter.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.enchanter.getField(0));
            }
            else if (this.powerRemaining != this.enchanter.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.enchanter.getField(1));
            }
            else if (this.ticksRequired != this.enchanter.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.enchanter.getField(2));
            }
        }

        this.progress = this.enchanter.getField(0);
        this.powerRemaining = this.enchanter.getField(1);
        this.ticksRequired = this.enchanter.getField(2);
    }

	@Override
	public void updateProgressBar(int id, int value)
	{
		this.enchanter.setField(id, value);
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.enchanter.isUsableByPlayer(par1EntityPlayer);
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
				if (AetherAPI.getInstance().hasEnchantment(itemstack))
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (AetherAPI.getInstance().isEnchantmentFuel(itemstack1))
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