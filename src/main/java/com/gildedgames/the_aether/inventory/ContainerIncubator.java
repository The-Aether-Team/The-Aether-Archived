package com.gildedgames.the_aether.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.inventory.slots.SlotIncubator;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.tileentity.TileEntityIncubator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerIncubator extends Container {

	private int progress, powerRemaining;

	private TileEntityIncubator incubator;

	public ContainerIncubator(EntityPlayer player, InventoryPlayer inventory, TileEntityIncubator incubator) {
		this.incubator = incubator;

		this.addSlotToContainer(new SlotIncubator(incubator, 1, 73, 17, player));
		this.addSlotToContainer(new Slot(incubator, 0, 73, 53));

		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
			}
		}

		for (int j = 0; j < 9; ++j) {
			this.addSlotToContainer(new Slot(inventory, j, 8 + j * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting listener) {
		super.addCraftingToCrafters(listener);

		listener.sendProgressBarUpdate(this, 0, this.incubator.progress);
		listener.sendProgressBarUpdate(this, 1, this.incubator.powerRemaining);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.progress != this.incubator.progress) {
				icrafting.sendProgressBarUpdate(this, 0, this.incubator.progress);
			}

			if (this.powerRemaining != this.incubator.powerRemaining) {
				icrafting.sendProgressBarUpdate(this, 1, this.incubator.powerRemaining);
			}
		}

		this.progress = this.incubator.progress;
		this.powerRemaining = this.incubator.powerRemaining;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {
		if (id == 0) {
			this.incubator.progress = value;
		}

		if (id == 1) {
			this.incubator.powerRemaining = value;
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.incubator.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index != 1 && index != 0) {
				if (itemstack.getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch) && this.mergeItemStack(itemstack1, 1, 2, false)) {
					return itemstack;
				} else if (itemstack.getItem() == ItemsAether.moa_egg && this.mergeItemStack(itemstack1, 0, 1, false)) {
					return itemstack;
				} else if (index >= 2 && index < 29) {
					if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
						return null;
					}
				} else if (index >= 29 && index < 37 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(entityplayer, itemstack1);
		}

		return itemstack;
	}

}