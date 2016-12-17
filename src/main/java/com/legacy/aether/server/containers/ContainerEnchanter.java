package com.legacy.aether.server.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.server.containers.slots.SlotEnchanter;
import com.legacy.aether.server.tile_entities.TileEntityEnchanter;

public class ContainerEnchanter extends Container
{

	private TileEntityEnchanter enchanter;

	private int lastCookTime;

	private int lastBurnTime;

	private int lastItemBurnTime;

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
	public void addListener(IContainerListener par1ICrafting)
	{
		super.addListener(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, this.enchanter.enchantmentTimeRemaining);
		par1ICrafting.sendProgressBarUpdate(this, 1, this.enchanter.enchantmentProgress);
		par1ICrafting.sendProgressBarUpdate(this, 2, this.enchanter.enchantmentTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener icrafting = (IContainerListener) this.listeners.get(i);

			if (this.lastCookTime != this.enchanter.enchantmentTimeRemaining)
			{
				icrafting.sendProgressBarUpdate(this, 0, this.enchanter.enchantmentTimeRemaining);
			}

			if (this.lastBurnTime != this.enchanter.enchantmentProgress)
			{
				icrafting.sendProgressBarUpdate(this, 1, this.enchanter.enchantmentProgress);
			}

			if (this.lastItemBurnTime != this.enchanter.enchantmentTime)
			{
				icrafting.sendProgressBarUpdate(this, 2, this.enchanter.enchantmentTime);
			}
		}

		this.lastCookTime = this.enchanter.enchantmentTimeRemaining;
		this.lastBurnTime = this.enchanter.enchantmentProgress;
		this.lastItemBurnTime = this.enchanter.enchantmentTime;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			this.enchanter.enchantmentTimeRemaining = par2;
		}

		if (par1 == 1)
		{
			this.enchanter.enchantmentProgress = par2;
		}

		if (par1 == 2)
		{
			this.enchanter.enchantmentTime = par2;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.enchanter.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 2)
			{
				if (!this.mergeItemStack(itemstack1, 3, 39, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if (TileEntityEnchanter.getEnchantmentResult(itemstack1) != null)
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return null;
					}
				}
				else if (TileEntityEnchanter.isItemFuel(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 1, 2, false))
					{
						return null;
					}
				}
				else if (par2 >= 3 && par2 < 30)
				{
					if (!this.mergeItemStack(itemstack1, 30, 39, false))
					{
						return null;
					}
				}
				else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 3, 39, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

}