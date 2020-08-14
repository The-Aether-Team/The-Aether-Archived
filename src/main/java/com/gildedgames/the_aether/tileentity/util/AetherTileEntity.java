package com.gildedgames.the_aether.tileentity.util;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public abstract class AetherTileEntity extends TileEntity implements ISidedInventory, IInventory {

	private String name = "generic";

	private String customTileName = null;

	public AetherTileEntity(String name) {
		this.name = name;
	}

	public abstract List<ItemStack> getTileInventory();

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customTileName : this.name;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customTileName != null && !this.customTileName.isEmpty();
	}

	public void setCustomName(String customTileName) {
		this.customTileName = customTileName;
	}

	@Override
	public int getSizeInventory() {
		return this.getTileInventory().size();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.getTileInventory().get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = this.getStackInSlot(index);

		if (stack.stackSize <= count) {
			this.setInventorySlotContents(index, null);

			return stack;
		}

		if (stack.stackSize == 0) {
			this.setInventorySlotContents(index, null);
		}

		return this.getTileInventory().get(index).splitStack(count);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = this.getStackInSlot(index);

		if (this.getStackInSlot(index) != null) {
			this.setInventorySlotContents(index, null);
		}

		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		boolean flag = stack != null && this.getStackInSlot(index) != null && stack.isItemEqual(this.getStackInSlot(index)) && ItemStack.areItemStackTagsEqual(stack, this.getStackInSlot(index));

		this.getTileInventory().set(index, stack);

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}

		if (!flag) {
			this.onSlotChanged(index);

			this.markDirty();
		}
	}

	public abstract void onSlotChanged(int index);

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList("Items", 10);

		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound stackCompound = list.getCompoundTagAt(i);
			byte slot = stackCompound.getByte("Slot");

			if (slot >= 0 && slot < this.getTileInventory().size()) {
				this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackCompound));
			}
		}

		if (compound.hasKey("CustomName", 8)) {
			this.customTileName = compound.getString("CustomName");
		}

		super.readFromNBT(compound);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.getTileInventory().size(); ++i) {
			ItemStack stack = this.getTileInventory().get(i);

			if (stack != null) {
				NBTTagCompound stackCompound = new NBTTagCompound();
				stackCompound.setByte("Slot", (byte) i);
				stack.writeToNBT(stackCompound);
				list.appendTag(stackCompound);
			}
		}

		compound.setTag("Items", list);

		if (this.hasCustomInventoryName()) {
			compound.setString("CustomName", this.customTileName);
		}

		super.writeToNBT(compound);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return this.isValidSlotItem(index, stack);
	}

	public abstract boolean isValidSlotItem(int slot, ItemStack stackInSlot);

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int direction) {
		return this.isValidSlotItem(index, stack);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int direction) {
		return true;
	}

}