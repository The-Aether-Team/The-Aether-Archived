package com.gildedgames.the_aether.inventory;

import com.gildedgames.the_aether.api.AetherAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.inventory.slots.SlotFreezer;
import com.gildedgames.the_aether.tileentity.TileEntityFreezer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerFreezer extends Container {

	private TileEntityFreezer freezer;

	public int progress, ticksRequired, powerRemaining;

	public ContainerFreezer(InventoryPlayer par1InventoryPlayer, TileEntityFreezer tileEntityFreezer) {
		this.freezer = tileEntityFreezer;

		this.addSlotToContainer(new Slot(tileEntityFreezer, 0, 56, 17));
		this.addSlotToContainer(new Slot(tileEntityFreezer, 1, 56, 53));
		this.addSlotToContainer(new SlotFreezer(tileEntityFreezer, 2, 116, 35));

		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting listener) {
		super.addCraftingToCrafters(listener);

		listener.sendProgressBarUpdate(this, 0, this.freezer.progress);
		listener.sendProgressBarUpdate(this, 1, this.freezer.powerRemaining);
		listener.sendProgressBarUpdate(this, 2, this.freezer.ticksRequired);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.progress != this.freezer.progress) {
				icrafting.sendProgressBarUpdate(this, 0, this.freezer.progress);
			}

			if (this.powerRemaining != this.freezer.powerRemaining) {
				icrafting.sendProgressBarUpdate(this, 1, this.freezer.powerRemaining);
			}

			if (this.ticksRequired != this.freezer.ticksRequired) {
				icrafting.sendProgressBarUpdate(this, 2, this.freezer.ticksRequired);
			}
		}

		this.progress = this.freezer.progress;
		this.powerRemaining = this.freezer.powerRemaining;
		this.ticksRequired = this.freezer.ticksRequired;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0) {
			this.freezer.progress = value;
		}

		if (id == 1) {
			this.freezer.powerRemaining = value;
		}

		if (id == 2) {
			this.freezer.ticksRequired = value;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.freezer.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (par2 != 1 && par2 != 0) {
				if (AetherAPI.instance().hasFreezable(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (AetherAPI.instance().isFreezableFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (par2 >= 3 && par2 < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

}