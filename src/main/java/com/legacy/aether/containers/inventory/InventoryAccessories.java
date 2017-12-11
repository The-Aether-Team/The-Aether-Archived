package com.legacy.aether.containers.inventory;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.accessories.AccessoryType;
import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.items.accessories.ItemAccessory;
import com.legacy.aether.player.PlayerAether;

public class InventoryAccessories implements IInventory
{

	public EntityPlayer player;

    public NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(8, ItemStack.EMPTY);

    public static final String[] EMPTY_SLOT_NAMES = new String[] {"pendant", "cape", "shield", "misc", "ring", "ring", "gloves", "misc"};

    public AccessoryType[] slotTypes = new AccessoryType[] {AccessoryType.PENDANT, AccessoryType.CAPE, AccessoryType.SHIELD, AccessoryType.MISC, AccessoryType.RING, AccessoryType.RING, AccessoryType.GLOVE, AccessoryType.MISC};

    public InventoryAccessories(EntityPlayer thePlayer)
    {
        this.player = thePlayer;
    }

	public void dropAllItems()
	{
		for (int slot = 0; slot < this.stacks.size(); ++slot)
		{
			if (this.stacks.get(slot) != ItemStack.EMPTY)
			{
				this.player.dropItem(this.stacks.get(slot), true, true);

				this.stacks.set(slot, ItemStack.EMPTY);
			}
		}
	}

	public void damageItemStackIfWearing(ItemStack itemStack)
	{
		ItemStack currentAccessory = this.getStackFromItem(itemStack.getItem());

		if (currentAccessory != ItemStack.EMPTY)
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

		for (int i = 0; i < this.stacks.size(); ++i)
		{
			itemstack = this.stacks.get(i);

			if (itemstack != ItemStack.EMPTY && itemstack == this.getStackFromItem(item) && (item != null || itemstack.getItem() == item))
			{
				if (itemstack.getTagCompound() != null && itemstack.getTagCompound().getBoolean("Unbreakable"))
				{
					return count;
				}
				else
				{
					count += itemstack.getCount();
					this.stacks.set(i, ItemStack.EMPTY);
				}
			}
		}

		return count;
	}

	@Override
	public int getSizeInventory()
	{
		return this.stacks.size();
	}

	public boolean setInventoryAccessory(ItemStack stack)
	{
		if (stack != ItemStack.EMPTY && AetherAPI.getInstance().isAccessory(stack))
		{
			AetherAccessory accessory = AetherAPI.getInstance().getAccessory(stack);

			int stackIndex = 0;

			for (AccessoryType type : this.slotTypes)
			{
				if (accessory.getAccessoryType() == type && this.stacks.get(stackIndex) == ItemStack.EMPTY)
				{
					this.stacks.set(stackIndex, stack);

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
		if (slotID < this.stacks.size())
		{
			this.stacks.set(slotID, stack);

			this.markDirty();
		}
	}

	@Override
	public ItemStack getStackInSlot(int slotID)
	{
		return this.stacks.get(slotID);
	}

	public ItemStack getStackFromItem(Item item)
	{
		for (ItemStack stack : this.stacks)
		{
			if (stack.getItem() instanceof ItemAccessory && stack.getItem() == item)
			{
				return stack;
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int slotID, int decreaseSize)
	{
		if (this.stacks.get(slotID) != ItemStack.EMPTY)
		{
			ItemStack itemstack = ItemStack.EMPTY;

			if (this.stacks.get(slotID).getCount() <= decreaseSize)
			{
				itemstack = this.stacks.get(slotID);

				this.stacks.set(slotID, ItemStack.EMPTY);

				this.markDirty();

				return itemstack;
			}
			else
			{
				itemstack = this.stacks.get(slotID).splitStack(decreaseSize);

				if (this.stacks.get(slotID).getCount() == 0)
				{
					this.stacks.set(slotID, ItemStack.EMPTY);
				}

				this.markDirty();

				return itemstack;
			}

		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int slotID)
	{
		if (this.stacks.get(slotID) != ItemStack.EMPTY)
		{
			ItemStack itemstack = this.stacks.get(slotID);

			this.stacks.set(slotID, ItemStack.EMPTY);

			return itemstack;
		}

		return ItemStack.EMPTY;
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
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return !player.isDead && player.getDistanceSq(this.player) <= 64.0D;
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
		for (int size = 0; size < this.stacks.size(); ++size)
		{
			this.stacks.set(size, accessories.stacks.get(size).copy());
		}
	}

    public void writeToNBT(NBTTagCompound compound)
    {
    	ItemStackHelper.saveAllItems(compound, this.stacks);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
    	ItemStackHelper.loadAllItems(compound, this.stacks);
    }

	public void writeData(ByteBuf dataOutput)
	{
		for (ItemStack stack : this.stacks)
		{
			PacketBuffer pb = new PacketBuffer(dataOutput);
			try
			{
				pb.writeItemStack(stack);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void readData(ByteBuf dataInput)
	{
		for (int i = 0; i < this.stacks.size(); ++i)
		{
			PacketBuffer pb = new PacketBuffer(dataInput);
			try
			{
				this.stacks.set(i, pb.readItemStack());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setField(int id, int value) { }

	@Override
	public int getField(int id) { return id; }

	@Override
	public int getFieldCount()
	{
		int count = 0;

		for (int slot = 0; slot < this.stacks.size(); ++slot)
		{
			if (this.stacks.get(slot) != ItemStack.EMPTY)
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

		for (int slot = 0; slot < this.stacks.size(); ++slot)
		{
			itemstack = this.stacks.get(slot);

			if (itemstack != ItemStack.EMPTY)
			{
				this.stacks.set(slot, ItemStack.EMPTY);
			}
		}

		this.markDirty();
	}

	@Override
	public boolean isEmpty() 
	{
		for (ItemStack stacks : this.stacks)
		{
			if (!stacks.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

}