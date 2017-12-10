package com.legacy.aether.tile_entities;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.events.AetherHooks;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.tile_entities.util.AetherTileEntity;

public class TileEntityEnchanter extends AetherTileEntity
{

    private NonNullList<ItemStack> enchantedItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);

	public int enchantmentProgress, enchantmentTime, enchantmentTimeRemaining;

	private AetherEnchantment currentEnchantment;

	public TileEntityEnchanter() 
	{
		super("enchanter");

		this.enchantmentProgress = 0;
		this.enchantmentTimeRemaining = 0;
		this.enchantmentTime = 0;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : this.enchantedItemStacks)
		{
			if (!stack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public void update()
	{
		if (this.enchantmentTimeRemaining > 0)
		{
			this.enchantmentTimeRemaining--;

			if (this.currentEnchantment != null)
			{
				if (this.world.getBlockState(this.getPos().down()).getBlock() == BlocksAether.enchanted_gravitite)
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
				if (!this.world.isRemote)
				{
					ItemStack result = this.currentEnchantment.getOutput().copy();

					EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(this.getStackInSlot(0)), result);

					if (this.getStackInSlot(2) != null)
					{
						result.setCount(this.getStackInSlot(2).getCount() + 1);
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

				if (!this.world.isRemote)
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
		return this.enchantedItemStacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return this.enchantedItemStacks.get(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (this.enchantedItemStacks.get(i) != ItemStack.EMPTY)
		{
			if (this.enchantedItemStacks.get(i).getCount() <= j)
			{
				ItemStack itemstack = this.enchantedItemStacks.get(i);

				this.enchantedItemStacks.set(i, ItemStack.EMPTY);

				return itemstack;
			}
			else
			{
				ItemStack itemstack1 = this.enchantedItemStacks.get(i).splitStack(j);

				if (this.enchantedItemStacks.get(i).getCount() == 0)
				{
					this.enchantedItemStacks.set(i, ItemStack.EMPTY);
				}

				return itemstack1;
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.enchantedItemStacks.get(par1) != ItemStack.EMPTY)
		{
			ItemStack itemstack = this.enchantedItemStacks.get(par1);

			this.enchantedItemStacks.set(par1, ItemStack.EMPTY);

			return itemstack;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.enchantedItemStacks.set(i, itemstack);

		if (itemstack != ItemStack.EMPTY && itemstack.getCount() > this.getInventoryStackLimit())
		{
			itemstack.setCount(this.getInventoryStackLimit());
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

		this.enchantmentProgress = nbttagcompound.getShort("EnchantedTime");
		this.enchantmentTime = nbttagcompound.getShort("EnchantTime");

		ItemStackHelper.loadAllItems(nbttagcompound, this.enchantedItemStacks);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setShort("EnchantedTime", (short) this.enchantmentProgress);
		nbttagcompound.setShort("EnchantTime", (short) this.enchantmentTime);

		ItemStackHelper.saveAllItems(nbttagcompound, this.enchantedItemStacks);

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