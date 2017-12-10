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
import com.legacy.aether.api.events.AetherHooks;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.tile_entities.util.AetherTileEntity;

public class TileEntityFreezer extends AetherTileEntity
{

    private NonNullList<ItemStack> frozenItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);

	public int freezeProgress, freezeTime, frozenTimeRemaining;

	private AetherFreezable currentFreezable;

	public TileEntityFreezer() 
	{
		super("freezer");

		this.freezeProgress = 0;
		this.frozenTimeRemaining = 0;
		this.freezeTime = 0;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : this.frozenItemStacks)
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
		if (this.frozenTimeRemaining > 0)
		{
			this.frozenTimeRemaining--;

			if (this.currentFreezable != null)
			{
				if (this.world.getBlockState(this.getPos().down()).getBlock() == BlocksAether.icestone)
				{
					this.freezeProgress += 2;
				}
				else
				{
					this.freezeProgress++;
				}
			}
		}

		if (this.currentFreezable != null)
		{
			if (this.getStackInSlot(0) == null || AetherAPI.getInstance().hasFreezable(this.getStackInSlot(0)) && AetherAPI.getInstance().getFreezable(this.getStackInSlot(0)).equals(this.currentFreezable))
			{
				this.currentFreezable = null;
				this.freezeProgress = 0;
			}

			if (this.freezeProgress >= this.currentFreezable.getTimeRequired())
			{
				if (!this.world.isRemote)
				{
					ItemStack result = this.currentFreezable.getOutput().copy();

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

				this.freezeProgress = 0;
				AetherHooks.onItemFreeze(this, this.currentFreezable);
			}

			if (this.frozenTimeRemaining <= 0 && AetherAPI.getInstance().isFreezableFuel(this.getStackInSlot(1)))
			{
				this.frozenTimeRemaining += AetherAPI.getInstance().getFreezableFuel(this.getStackInSlot(1)).getTimeGiven();

				if (!this.world.isRemote)
				{
					this.decrStackSize(1, 1);
				}
			}
		}
		else if (this.getStackInSlot(0) != null)
		{
			ItemStack itemstack = this.getStackInSlot(0);
			AetherFreezable freezable = AetherAPI.getInstance().getFreezable(itemstack);

			if (this.getStackInSlot(2) == null || freezable.getOutput().getItem() == this.getStackInSlot(2).getItem() && freezable.getOutput().getMetadata() == this.getStackInSlot(2).getMetadata())
			{
				this.currentFreezable = freezable;
				this.freezeTime = this.currentFreezable.getTimeRequired();
				this.addEnchantmentWeight(itemstack);
				this.freezeTime = AetherHooks.onSetFreezableTime(this, this.currentFreezable, this.freezeTime);
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
				this.freezeTime += (levels * 1250);
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
		if (this.freezeTime == 0)
		{
			return 0;
		}
		return (this.freezeProgress * i) / this.freezeTime;
	}

	@SideOnly(Side.CLIENT)
	public int getEnchantmentTimeRemaining(int i)
	{
		return (this.frozenTimeRemaining * i) / 500;
	}

	public boolean isBurning()
	{
		return this.frozenTimeRemaining > 0;
	}

	@Override
	public int getSizeInventory()
	{
		return this.frozenItemStacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return this.frozenItemStacks.get(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (this.frozenItemStacks.get(i) != ItemStack.EMPTY)
		{
			if (this.frozenItemStacks.get(i).getCount() <= j)
			{
				ItemStack itemstack = this.frozenItemStacks.get(i);

				this.frozenItemStacks.set(i, ItemStack.EMPTY);

				return itemstack;
			}
			else
			{
				ItemStack itemstack = this.frozenItemStacks.get(i).splitStack(j);

				if (this.frozenItemStacks.get(i).getCount() == 0)
				{
					this.frozenItemStacks.set(i, ItemStack.EMPTY);
				}

				return itemstack;
			}
		}

		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int par1)
	{
		if (this.frozenItemStacks.get(par1) != ItemStack.EMPTY)
		{
			ItemStack itemstack = this.frozenItemStacks.get(par1);

			this.frozenItemStacks.set(par1, ItemStack.EMPTY);

			return itemstack;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.frozenItemStacks.set(i, itemstack);

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

		this.freezeProgress = nbttagcompound.getShort("FreezeTime");
		this.freezeTime = nbttagcompound.getShort("FrozenTime");

		ItemStackHelper.loadAllItems(nbttagcompound, this.frozenItemStacks);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setShort("FreezeTime", (short) this.freezeProgress);
		nbttagcompound.setShort("FrozenTime", (short) this.freezeTime);

		ItemStackHelper.saveAllItems(nbttagcompound, this.frozenItemStacks);

		return super.writeToNBT(nbttagcompound);
	}

	@Override
	public boolean isValidSlotItem(int slot, ItemStack stackInSlot)
	{
		if (stackInSlot != null)
		{
			if (AetherAPI.getInstance().hasFreezable(stackInSlot))
			{
				return true;
			}
			else if (slot == 1 && AetherAPI.getInstance().isFreezableFuel(stackInSlot))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? new int[] {2} : new int[] {0, 1};
	}

}