package com.legacy.aether.tile_entities;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.events.AetherHooks;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.tile_entities.util.AetherTileEntity;

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

		if (this.currentEnchantment != null)
		{
			if (this.getStackInSlot(0) == null || AetherAPI.getInstance().hasEnchantment(this.getStackInSlot(0)) && AetherAPI.getInstance().getEnchantment(this.getStackInSlot(0)).equals(this.currentEnchantment))
			{
				this.currentEnchantment = null;
				this.enchantmentProgress = 0;
			}

			if (this.enchantmentProgress >= this.enchantmentTime)
			{
				if (!this.worldObj.isRemote)
				{
					ItemStack result = this.currentEnchantment.getOutput().copy();

					EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(this.getStackInSlot(0)), result);

					if (this.getStackInSlot(2) != null)
					{
						result.stackSize = this.getStackInSlot(2).stackSize + 1;
						this.setInventorySlotContents(2, result);
					}
					else
					{
						this.setInventorySlotContents(2, result);
					}

					if (this.getStackInSlot(0).getItem().getContainerItem() != null)
					{
						this.setInventorySlotContents(0, new ItemStack(this.getStackInSlot(0).getItem().getContainerItem()));
					}
					else
					{
						this.decrStackSize(0, 1);
					}
				}

				this.enchantmentProgress = 0;

				AetherHooks.onItemEnchant(this, this.currentEnchantment);
			}

			if (this.enchantmentTimeRemaining <= 0 && AetherAPI.getInstance().isEnchantmentFuel(this.getStackInSlot(1)))
			{
				this.enchantmentTimeRemaining += AetherAPI.getInstance().getEnchantmentFuel(this.getStackInSlot(1)).getTimeGiven();

				if (!this.worldObj.isRemote)
				{
					this.decrStackSize(1, 1);
				}
			}
		}
		else
		{
			if (this.getStackInSlot(0) != null)
			{
				ItemStack itemstack = this.getStackInSlot(0);
				AetherEnchantment enchantment = AetherAPI.getInstance().getEnchantment(itemstack);

				if (enchantment != null)
				{
					if (this.getStackInSlot(2) == null || enchantment.getOutput().getItem() == this.getStackInSlot(2).getItem() && enchantment.getOutput().getMetadata() == this.getStackInSlot(2).getMetadata())
					{
						this.currentEnchantment = enchantment;
						this.enchantmentTime = this.currentEnchantment.getTimeRequired();
						this.addEnchantmentWeight(itemstack);
						this.enchantmentTime = AetherHooks.onSetEnchantmentTime(this, this.currentEnchantment, this.enchantmentTime);
					}
				}
			}
		}
	}

	public void addEnchantmentWeight(ItemStack stack)
	{
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);

		if (!enchantments.isEmpty())
		{
			for (int levels : enchantments.values())
			{
				this.enchantmentTime += (levels * 1250);
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
		else if (slot == 1 && AetherAPI.getInstance().isEnchantmentFuel(stackInSlot))
		{
			return true;
		}
		else if (slot == 0 && AetherAPI.getInstance().hasEnchantment(stackInSlot))
		{
			return true;
		}

		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? new int[] {2} : new int[] {0, 1};
	}

}