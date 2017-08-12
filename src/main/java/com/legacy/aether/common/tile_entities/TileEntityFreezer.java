package com.legacy.aether.common.tile_entities;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.compatibility.AetherCompatibility;
import com.legacy.aether.common.events.AetherFreezerEvent;
import com.legacy.aether.common.freezables.AetherFreezable;
import com.legacy.aether.common.tile_entities.util.AetherTileEntity;

public class TileEntityFreezer extends AetherTileEntity
{

	public int freezeProgress, freezeTime, frozenTimeRemaining;

	private ItemStack frozenItemStacks[];

	private AetherFreezable currentFreezable;

	public TileEntityFreezer() 
	{
		super("freezer");

		this.frozenItemStacks = new ItemStack[3];
		this.freezeProgress = 0;
		this.frozenTimeRemaining = 0;
		this.freezeTime = 0;
	}

	@Override
	public void update()
	{
		if (this.frozenTimeRemaining > 0)
		{
			this.frozenTimeRemaining--;

			if (this.currentFreezable != null)
			{
				if (this.worldObj.getBlockState(this.getPos().down()).getBlock() == BlocksAether.icestone)
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
			if (this.getStackInSlot(0) == null || AetherCompatibility.getAetherRegistry().areFreezablesEqual(this.getStackInSlot(0), this.currentFreezable))
			{
				this.currentFreezable = null;
				this.freezeProgress = 0;
			}

			if (this.freezeProgress >= this.currentFreezable.getTimeRequired())
			{
				if (!this.worldObj.isRemote)
				{
					ItemStack result = this.currentFreezable.getResult().copy();

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

				this.freezeProgress = 0;
				MinecraftForge.EVENT_BUS.post(new AetherFreezerEvent.Freeze(this, this.currentFreezable));
			}

			if (this.frozenTimeRemaining <= 0 && AetherCompatibility.getAetherRegistry().isFreezerFuel(this.getStackInSlot(1)))
			{
				this.frozenTimeRemaining += AetherCompatibility.getAetherRegistry().getFreezerFuel(this.getStackInSlot(1));

				if (!this.worldObj.isRemote)
				{
					this.decrStackSize(1, 1);
				}
			}
		}
		else
		{
			ItemStack itemstack = this.getStackInSlot(0);
			AetherFreezable freezable = AetherCompatibility.getAetherRegistry().getFreezable(itemstack);

			if (this.getStackInSlot(2) == null || AetherCompatibility.getAetherRegistry().isFreezableResult(this.getStackInSlot(2), freezable))
			{
				this.currentFreezable = freezable;
				this.freezeTime = this.currentFreezable.getTimeRequired();
				this.addEnchantmentWeight(itemstack);

				AetherFreezerEvent.SetTime event = new AetherFreezerEvent.SetTime(this, this.freezeTime);

				if (!MinecraftForge.EVENT_BUS.post(event))
				{
					this.freezeTime = event.getNewTime();
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
		return this.frozenItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return this.frozenItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (this.frozenItemStacks[i] != null)
		{
			if (this.frozenItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = this.frozenItemStacks[i];
				this.frozenItemStacks[i] = null;
				return itemstack;
			}
			else
			{
				ItemStack itemstack1 = this.frozenItemStacks[i].splitStack(j);
				if (this.frozenItemStacks[i].stackSize == 0)
				{
					this.frozenItemStacks[i] = null;
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
		if (this.frozenItemStacks[par1] != null)
		{
			ItemStack var2 = this.frozenItemStacks[par1];
			this.frozenItemStacks[par1] = null;
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
		this.frozenItemStacks[i] = itemstack;

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
		this.frozenItemStacks = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < this.frozenItemStacks.length)
			{
				this.frozenItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.freezeProgress = nbttagcompound.getShort("FreezeTime");
		this.freezeTime = nbttagcompound.getShort("FrozenTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setShort("FreezeTime", (short) this.freezeProgress);
		nbttagcompound.setShort("FrozenTime", (short) this.freezeTime);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.frozenItemStacks.length; i++)
		{
			if (this.frozenItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.frozenItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);

		return super.writeToNBT(nbttagcompound);
	}

	@Override
	public boolean isValidSlotItem(int slot, ItemStack stackInSlot)
	{
		if (stackInSlot != null)
		{
			if (AetherCompatibility.getAetherRegistry().isFreezable(stackInSlot))
			{
				return true;
			}
			else if (slot == 1 && AetherCompatibility.getAetherRegistry().isFreezerFuel(stackInSlot))
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