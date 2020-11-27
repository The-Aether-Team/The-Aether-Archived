package com.gildedgames.the_aether.inventory;

import com.gildedgames.the_aether.inventory.slots.SlotLore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLore extends Container {

	public IInventory loreSlot;

	public ContainerLore(InventoryPlayer inventory) {
		this.loreSlot = new InventoryLore(inventory.player);

		this.addSlotToContainer(new SlotLore(this.loreSlot, 0, 104, -4));

		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(inventory, k + j * 9 + 9, 48 + k * 18, 113 + j * 18));
			}
		}

		for (int j = 0; j < 9; ++j) {
			this.addSlotToContainer(new Slot(inventory, j, 48 + j * 18, 171));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotNumber == 0) {
				if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotNumber != 0) {
				if (slotNumber >= 1 && slotNumber < 28) {
					if (!this.mergeItemStack(itemstack1, 28, 37, false)) {
						return null;
					}
				} else if (slotNumber >= 28 && slotNumber < 37 && !this.mergeItemStack(itemstack1, 1, 28, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 1, 37, false)) {
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

			slot.onPickupFromSlot(player, itemstack1);

		}

		return itemstack;
	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer) {
		if(!entityplayer.worldObj.isRemote) {
			ItemStack item = this.loreSlot.getStackInSlot(0);

			if (item != null) {
				entityplayer.entityDropItem(item, 1.0F);
			}
		}

		super.onContainerClosed(entityplayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return !player.isDead;
	}

}
