package com.legacy.aether.tile_entities.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class AetherTileEntity extends TileEntity implements ISidedInventory, IInventory, ITickable
{

	private String name = "generic";

	private String customTileName = null;

	public AetherTileEntity(String name)
	{
		this.name = name;
	}

	public abstract NonNullList<ItemStack> getTileInventory();

	@Override
	public String getName() 
	{
		return this.hasCustomName() ? this.customTileName : this.name;
	}

	@Override
	public boolean hasCustomName() 
	{
		return this.customTileName != null && !this.customTileName.isEmpty();
	}

	public void setCustomName(String customTileName)
	{
		this.customTileName = customTileName;
	}

	@Override
    public ITextComponent getDisplayName()
    {
        return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation("container.aether_legacy." + this.getName(), new Object[0]));
    }

	@Override
	public int getSizeInventory()
	{
		return this.getTileInventory().size();
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.getTileInventory().get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
        return ItemStackHelper.getAndSplit(this.getTileInventory(), index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
        return ItemStackHelper.getAndRemove(this.getTileInventory(), index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) 
	{
        boolean flag = stack != null && stack.isItemEqual(this.getStackInSlot(index)) && ItemStack.areItemStackTagsEqual(stack, this.getStackInSlot(index));

		this.getTileInventory().set(index, stack);

		if (stack != null && stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

        if (!flag)
        {
            this.onSlotChanged(index);

            this.markDirty();
        }
	}

	public abstract void onSlotChanged(int index);

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		ItemStackHelper.loadAllItems(nbttagcompound, this.getTileInventory());

        if (nbttagcompound.hasKey("CustomName", 8))
        {
            this.customTileName = nbttagcompound.getString("CustomName");
        }

		super.readFromNBT(nbttagcompound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		ItemStackHelper.saveAllItems(nbttagcompound, this.getTileInventory());

        if (this.hasCustomName())
        {
        	nbttagcompound.setString("CustomName", this.customTileName);
        }

		return super.writeToNBT(nbttagcompound);
	}

	@Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.getTileInventory())
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
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
	public void clear() 
	{ 
		this.getTileInventory().clear();
	}

}