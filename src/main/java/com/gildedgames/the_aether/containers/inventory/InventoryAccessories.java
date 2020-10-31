package com.gildedgames.the_aether.containers.inventory;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.api.accessories.AetherAccessory;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.accessories.ItemAccessory;
import com.gildedgames.the_aether.player.PlayerAether;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class InventoryAccessories implements IAccessoryInventory
{

	public EntityPlayer player;

    public NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(8, ItemStack.EMPTY);

    public static final String[] EMPTY_SLOT_NAMES = new String[] {"pendant", "cape", "shield", "misc", "ring", "ring", "gloves", "misc"};

    private AccessoryType[] slotTypes = new AccessoryType[] {AccessoryType.PENDANT, AccessoryType.CAPE, AccessoryType.SHIELD, AccessoryType.MISC, AccessoryType.RING, AccessoryType.RING, AccessoryType.GLOVE, AccessoryType.MISC};

    public InventoryAccessories(EntityPlayer thePlayer)
    {
        this.player = thePlayer;
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
		((PlayerAether) AetherAPI.getInstance().get(this.player)).updateAccessories();
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
		for (int i = 0; i < this.stacks.size(); ++i)
		{
			ItemStack stack = this.stacks.get(i);

			ByteBufUtils.writeItemStack(dataOutput, stack);
		}
	}

	public void readData(ByteBuf dataInput)
	{
		for (int i = 0; i < this.stacks.size(); ++i)
		{
			this.stacks.set(i, ByteBufUtils.readItemStack(dataInput));
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

	@Override
	public void dropAccessories()
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

	@Override
	public void damageWornStack(int damage, ItemStack stack)
	{
		ItemStack currentAccessory = this.getStackFromItem(stack.getItem());

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

	@Override
	public boolean setAccessorySlot(ItemStack stack)
	{
		if (!stack.isEmpty() && AetherAPI.getInstance().isAccessory(stack))
		{
			AetherAccessory accessory = AetherAPI.getInstance().getAccessory(stack);

			for (int i = 0; i < this.slotTypes.length; ++i)
			{
				AccessoryType type = this.slotTypes[i];

				if (accessory.getAccessoryType() == type && this.stacks.get(i).isEmpty())
				{
					this.stacks.set(i, stack);

					this.markDirty();

					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean wearingAccessory(ItemStack stack)
	{
		for (int index = 0; index < this.getSizeInventory(); index++)
		{
			if (this.getStackInSlot(index).getItem() == stack.getItem())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean wearingArmor(ItemStack stack)
	{
		for (int index = 0; index < 4; index++)
		{
			if (this.player != null && this.player.inventory.armorInventory.get(index).getItem() == stack.getItem())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isWearingZaniteSet()
	{
		return wearingArmor(new ItemStack(ItemsAether.zanite_helmet)) && wearingArmor(new ItemStack(ItemsAether.zanite_chestplate)) && wearingArmor(new ItemStack(ItemsAether.zanite_leggings)) && wearingArmor(new ItemStack(ItemsAether.zanite_boots)) && wearingAccessory(new ItemStack(ItemsAether.zanite_gloves));
	}

	@Override
	public boolean isWearingGravititeSet()
	{
		return wearingArmor(new ItemStack(ItemsAether.gravitite_helmet)) && wearingArmor(new ItemStack(ItemsAether.gravitite_chestplate)) && wearingArmor(new ItemStack(ItemsAether.gravitite_leggings)) && wearingArmor(new ItemStack(ItemsAether.gravitite_boots)) && wearingAccessory(new ItemStack(ItemsAether.gravitite_gloves));
	}

	@Override
	public boolean isWearingNeptuneSet()
	{
		return wearingArmor(new ItemStack(ItemsAether.neptune_helmet)) && wearingArmor(new ItemStack(ItemsAether.neptune_chestplate)) && wearingArmor(new ItemStack(ItemsAether.neptune_leggings)) && wearingArmor(new ItemStack(ItemsAether.neptune_boots)) && wearingAccessory(new ItemStack(ItemsAether.neptune_gloves));
	}

	@Override
	public boolean isWearingPhoenixSet()
	{
		return wearingArmor(new ItemStack(ItemsAether.phoenix_helmet)) && wearingArmor(new ItemStack(ItemsAether.phoenix_chestplate)) && wearingArmor(new ItemStack(ItemsAether.phoenix_leggings)) && wearingArmor(new ItemStack(ItemsAether.phoenix_boots)) && wearingAccessory(new ItemStack(ItemsAether.phoenix_gloves));
	}

	@Override
	public boolean isWearingValkyrieSet()
	{
		return wearingArmor(new ItemStack(ItemsAether.valkyrie_helmet)) && wearingArmor(new ItemStack(ItemsAether.valkyrie_chestplate)) && wearingArmor(new ItemStack(ItemsAether.valkyrie_leggings)) && wearingArmor(new ItemStack(ItemsAether.valkyrie_boots)) && wearingAccessory(new ItemStack(ItemsAether.valkyrie_gloves));
	}

	@Override
	public boolean isWearingObsidianSet()
	{
		return wearingArmor(new ItemStack(ItemsAether.obsidian_helmet)) && wearingArmor(new ItemStack(ItemsAether.obsidian_chestplate)) && wearingArmor(new ItemStack(ItemsAether.obsidian_leggings)) && wearingArmor(new ItemStack(ItemsAether.obsidian_boots)) && wearingAccessory(new ItemStack(ItemsAether.obsidian_gloves));
	}

	@Override
	public NonNullList<ItemStack> getAccessories()
	{
		return this.stacks;
	}

	@Override
	public int getAccessoryCount(ItemStack stack)
	{
		int count = 0;

		for (int index = 0; index < this.getSizeInventory(); index++)
		{
			if (this.getStackInSlot(index).getItem() == stack.getItem())
			{
				count++;
			}
		}

		return count;
	}

}