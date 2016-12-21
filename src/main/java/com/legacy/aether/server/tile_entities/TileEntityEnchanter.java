package com.legacy.aether.server.tile_entities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.items.ItemsAether;
import com.legacy.aether.server.registry.AetherRegistry;
import com.legacy.aether.server.registry.objects.AetherEnchantment;
import com.legacy.aether.server.tile_entities.util.AetherTileEntity;

public class TileEntityEnchanter extends AetherTileEntity
{

	public int enchantmentProgress, enchantmentTime, enchantmentTimeRemaining;

	private ItemStack enchantedItemStacks[];

	private AetherEnchantment currentEnchantment;

	public TileEntityEnchanter() 
	{
		super("enchanter");

		this.enchantedItemStacks = new ItemStack[3];
		this.enchantmentProgress = 0;
		this.enchantmentTimeRemaining = 0;
		this.enchantmentTime = 0;
	}

	@Override
	public void update()
	{
		if (this.enchantmentTimeRemaining > 0)
		{
			this.enchantmentTimeRemaining--;

			if (this.currentEnchantment != null)
			{
				if (this.worldObj.getBlockState(this.getPos().down()).getBlock() == BlocksAether.enchanted_gravitite)
				{
					this.enchantmentProgress += 2;
				}
				else
				{
					this.enchantmentProgress++;
				}
			}
		}

		if (this.currentEnchantment != null && (this.enchantedItemStacks[0] == null || this.enchantedItemStacks[0].getItem() != this.currentEnchantment.getEnchantmentInput().getItem()))
		{
			this.currentEnchantment = null;
			this.enchantmentProgress = 0;
		}

		if (this.currentEnchantment != null && this.enchantmentProgress >= this.currentEnchantment.getTimeRequired())
		{
			if (!this.worldObj.isRemote)
			{
				if (this.enchantedItemStacks[2] == null)
				{
					this.setInventorySlotContents(2, new ItemStack(this.currentEnchantment.getEnchantedResult().getItem(), 1, this.currentEnchantment.getEnchantedResult().getItemDamage()));
				}
				else
				{
					this.setInventorySlotContents(2, new ItemStack(this.currentEnchantment.getEnchantedResult().getItem(), this.getStackInSlot(2).stackSize + 1, this.currentEnchantment.getEnchantedResult().getItemDamage()));
				}

				this.decrStackSize(0, 1);
			}

			this.enchantmentProgress = 0;
		}

		if (this.enchantmentTimeRemaining <= 0 && this.currentEnchantment != null && this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() == ItemsAether.ambrosium_shard)
		{
			this.enchantmentTimeRemaining += 500;

			if (!this.worldObj.isRemote)
			{
				this.decrStackSize(1, 1);
			}
		}

		if (this.currentEnchantment == null)
		{
			ItemStack itemstack = this.getStackInSlot(0);

			for (int i = 0; i < AetherRegistry.getEnchantmentSize(); i++)
			{
				AetherEnchantment enchantment = AetherRegistry.getEnchantment(i);

				if (itemstack != null && enchantment != null && itemstack.getItem() == enchantment.getEnchantmentInput().getItem() && (itemstack.getItemDamage() == enchantment.getEnchantmentInput().getItemDamage() || itemstack.isItemStackDamageable()))
				{
					if (this.enchantedItemStacks[2] == null)
					{
						this.currentEnchantment = enchantment;
						this.enchantmentTime = this.currentEnchantment.getTimeRequired();
					}
					else if (this.enchantedItemStacks[2].getItem() == enchantment.getEnchantedResult().getItem() && enchantment.getEnchantmentInput().getMaxStackSize() > this.enchantedItemStacks[2].stackSize)
					{
						this.currentEnchantment = enchantment;
						this.enchantmentTime = this.currentEnchantment.getTimeRequired();
					}
				}
			}
		}	
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@SideOnly(Side.CLIENT)
	public int getEnchantmentProgressScaled(int i)
	{
		if (this.enchantmentTime == 0)
		{
			return 0;
		}
		return (this.enchantmentProgress * i) / this.enchantmentTime;
	}

	@SideOnly(Side.CLIENT)
	public int getEnchantmentTimeRemaining(int i)
	{
		return (this.enchantmentTimeRemaining * i) / 500;
	}

	public boolean isBurning()
	{
		return this.enchantmentTimeRemaining > 0;
	}

	@Override
	public int getSizeInventory()
	{
		return this.enchantedItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return this.enchantedItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (this.enchantedItemStacks[i] != null)
		{
			if (this.enchantedItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = this.enchantedItemStacks[i];
				this.enchantedItemStacks[i] = null;
				return itemstack;
			}
			else
			{
				ItemStack itemstack1 = this.enchantedItemStacks[i].splitStack(j);
				if (this.enchantedItemStacks[i].stackSize == 0)
				{
					this.enchantedItemStacks[i] = null;
				}
				return itemstack1;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.enchantedItemStacks[par1] != null)
		{
			ItemStack var2 = this.enchantedItemStacks[par1];
			this.enchantedItemStacks[par1] = null;
			return var2;
		}
		else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.enchantedItemStacks[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	public static boolean isItemFuel(ItemStack fuel)
	{
		return fuel.getItem() == ItemsAether.ambrosium_shard;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		this.writeToNBT(var1);
		return new SPacketUpdateTileEntity(this.pos, 1, var1);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		this.enchantedItemStacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < this.enchantedItemStacks.length)
			{
				this.enchantedItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.enchantmentProgress = nbttagcompound.getShort("EnchantedTime");
		this.enchantmentTime = nbttagcompound.getShort("EnchantTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setShort("EnchantedTime", (short) this.enchantmentProgress);
		nbttagcompound.setShort("EnchantTime", (short) this.enchantmentTime);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.enchantedItemStacks.length; i++)
		{
			if (this.enchantedItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.enchantedItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
		return super.writeToNBT(nbttagcompound);
	}

	@Override
	public boolean isValidSlotItem(int slot, ItemStack stackInSlot)
	{
		if (slot == 2)
		{
			return false;
		}
		else if (slot == 1 && isItemFuel(stackInSlot))
		{
			return true;
		}
		else if (slot == 0 && getEnchantmentResult(stackInSlot) != null)
		{
			return true;
		}

		return false;
	}

	public static ItemStack getEnchantmentResult(ItemStack stack) 
	{
		for (AetherEnchantment enchants : AetherRegistry.getEnchantables())
		{
			if (enchants.getEnchantmentInput().getItem() == stack.getItem())
			{
				return enchants.getEnchantedResult().copy();
			}
		}

		return null;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? new int[] {2} : new int[] {0, 1};
	}

}