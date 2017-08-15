package com.legacy.aether.common.tile_entities.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class AetherTileEntity extends TileEntity implements ISidedInventory, IInventory, ITickable
{

	private String name = "generic";

	public AetherTileEntity(String name)
	{
		this.name = name;
	}

	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public boolean hasCustomName() 
	{
		return true;
	}

	@Override
	public ITextComponent getDisplayName() 
	{
		return new TextComponentTranslation("aether_tile." + name + ".name",  new Object[0]);
	}

	@Override
	public int getSizeInventory()
	{
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) 
	{
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) 
	{

	}

	@Override
	public int getInventoryStackLimit()
	{
		return 0;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) { }

	@Override
	public void closeInventory(EntityPlayer player) { }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return this.isValidSlotItem(index, stack);
	}

	public abstract boolean isValidSlotItem(int slot, ItemStack stackInSlot);

	@Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction)
    {
    	return this.isValidSlotItem(index, stack);
    }

	@Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
		return true;
	}

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) { }

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clear() { }

}