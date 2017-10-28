package com.legacy.aether.common.containers.inventory;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

import com.legacy.aether.api.AetherRegistry;
import com.legacy.aether.api.accessories.AccessoryType;
import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.common.player.PlayerAether;

public class InventoryAccessories implements IInventory
{

	public EntityPlayer player;

    public ItemStack[] stacks = new ItemStack[8];

    public static final String[] EMPTY_SLOT_NAMES = new String[] {"pendant", "cape", "shield", "misc", "ring", "ring", "gloves", "misc"};

    public AccessoryType[] slotTypes = new AccessoryType[] {AccessoryType.PENDANT, AccessoryType.CAPE, AccessoryType.SHIELD, AccessoryType.MISC, AccessoryType.RING, AccessoryType.RING, AccessoryType.GLOVE, AccessoryType.MISC};

    public InventoryAccessories(PlayerAether playerAether)
    {
        this.player = playerAether.thePlayer;
    }

	public void dropAllItems()
	{
		for (int slot = 0; slot < this.stacks.length; ++slot)
		{
			if (this.stacks[slot] != null)
			{
				this.player.dropItem(this.stacks[slot], true, true);

				this.stacks[slot] = null;
			}
		}
	}

	public void damageItemStackIfWearing(ItemStack itemStack)
	{
		ItemStack currentAccessory = this.getStackFromItem(itemStack.getItem());

		if (currentAccessory != null)
		{
			if (!this.player.capabilities.isCreativeMode)
			{
				currentAccessory.damageItem(1, this.player);

				if (currentAccessory.getItemDamage() >= currentAccessory.getMaxDamage())
				{
					this.breakItem(currentAccessory.getItem());
				}
			}
		}
	}

	public int breakItem(Item item)
	{
		int count = 0;

		ItemStack itemstack;

		for (int i = 0; i < this.stacks.length; ++i)
		{
			itemstack = this.stacks[i];

			if (itemstack != null && itemstack == this.getStackFromItem(item) && (item != null || itemstack.getItem() == item))
			{
				if (itemstack.getTagCompound() != null && itemstack.getTagCompound().getBoolean("Unbreakable"))
				{
					return count;
				}
				else
				{
					count += itemstack.stackSize;
					this.stacks[i] = null;
				}
			}
		}

		return count;
	}

	@Override
	public int getSizeInventory()
	{
		return this.stacks.length;
	}

	public boolean setInventoryAccessory(ItemStack stack)
	{
		if (stack != null && AetherRegistry.getInstance().isAccessory(stack))
		{
			AetherAccessory accessory = AetherRegistry.getInstance().getAccessory(stack);

			int stackIndex = 0;

			for (AccessoryType type : this.slotTypes)
			{
				if (accessory.getAccessoryType() == type && this.stacks[stackIndex] == null)
				{
					this.stacks[stackIndex] = stack;

					this.markDirty();

					return true;
				}

				stackIndex++;
			}
		}

		return false;
	}

	@Override
	public void setInventorySlotContents(int slotID, ItemStack stack)
	{
		if (slotID < this.stacks.length)
		{
			this.stacks[slotID] = stack;
			this.markDirty();
		}
	}

	@Override
	public ItemStack getStackInSlot(int slotID)
	{
		return this.stacks[slotID];
	}

	public ItemStack getStackFromItem(Item item)
	{
		for (ItemStack stack : this.stacks)
		{
			if (stack != null && stack.getItem() == item)
			{
				return stack;
			}
		}

		return null;
	}

	@Override
	public ItemStack decrStackSize(int slotID, int decreaseSize)
	{
		ItemStack[] accessories = this.stacks;

		if (accessories[slotID] != null)
		{
			ItemStack itemstack;

			if (accessories[slotID].stackSize <= decreaseSize)
			{
				itemstack = accessories[slotID];
				accessories[slotID] = null;

				this.markDirty();
				return itemstack;
			}
			else
			{
				itemstack = accessories[slotID].splitStack(decreaseSize);

				if (accessories[slotID].stackSize == 0)
				{
					accessories[slotID] = null;
				}

				this.markDirty();
				return itemstack;
			}

		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slotID)
	{
		ItemStack[] accessories = this.stacks;

		if (accessories[slotID] != null)
		{
			ItemStack itemstack = accessories[slotID];
			accessories[slotID] = null;

			return itemstack;
		}
		else
		{
			return null;
		}
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}

	@Override
    public String getName()
    {
        return "accessories";
    }

	@Override
    public boolean hasCustomName()
    {
        return true;
    }

	@Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

	@Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return !player.isDead && player.getDistanceSqToEntity(this.player) <= 64.0D;
    }

	@Override
    public void openInventory(EntityPlayer player) {}

	@Override
    public void closeInventory(EntityPlayer player) {}

	@Override
    public boolean isItemValidForSlot(int slotID, ItemStack stack)
    {
		return true;
    }

	@Override
	public void markDirty()
	{
		PlayerAether.get(player).updateAccessories();
	}

	public void copyAccessories(InventoryAccessories accessories)
	{
		for (int size = 0; size < this.stacks.length; ++size)
		{
			this.stacks[size] = ItemStack.copyItemStack(accessories.stacks[size]);
		}
	}

    public NBTTagList writeToNBT(NBTTagList tagList)
    {
    	NBTTagCompound tag;

    	for (int size = 0; size < this.stacks.length; ++size)
    	{
    		ItemStack stack = this.stacks[size];
    		
    		if (stack != null)
    		{
    			tag = new NBTTagCompound();
        		tag.setByte("accessories", (byte)size);
        		stack.writeToNBT(tag);
        		tagList.appendTag(tag);
    		}
    	}

    	return tagList;
    }

    public void readFromNBT(NBTTagList tagList)
    {
        this.stacks = new ItemStack[8];
        
        for (int size = 0; size < tagList.tagCount(); ++size)
        {
        	NBTTagCompound tag = tagList.getCompoundTagAt(size);
        	int slot = tag.getByte("accessories") & 255;
        	ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
        	
        	if (stack != null)
        	{
        		if (slot >= 0 && slot < this.stacks.length)
        		{
        			this.setInventorySlotContents(slot, stack);
        		}
        	}
        }
    }

	public void writeData(ByteBuf dataOutput)
	{
		dataOutput.writeInt(this.stacks.length);

		for (ItemStack stack : this.stacks)
		{
			PacketBuffer pb = new PacketBuffer(dataOutput);
			try
			{
				pb.writeItemStackToBuffer(stack);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void readData(ByteBuf dataInput)
	{
		int amount = dataInput.readInt();

		ItemStack[] stackArray = new ItemStack[amount];

		for (int i = 0; i < amount; ++i)
		{
			PacketBuffer pb = new PacketBuffer(dataInput);
			try
			{
				stackArray[i] = pb.readItemStackFromBuffer();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		this.stacks = stackArray;
	}

	@Override
	public void setField(int id, int value) { }

	@Override
	public int getField(int id) { return id; }

	@Override
	public int getFieldCount()
	{
		int count = 0;

		for (int slot = 0; slot < this.stacks.length; ++slot)
		{
			if (this.stacks[slot] != null)
			{
				++count;
			}
		}

		return count;
	}

	@Override
	public void clear()
	{
		ItemStack itemstack;

		for (int slot = 0; slot < this.stacks.length; ++slot)
		{
			itemstack = this.stacks[slot];

			if (itemstack != null)
			{
				this.stacks[slot] = null;
			}
		}

		this.markDirty();
	}

}