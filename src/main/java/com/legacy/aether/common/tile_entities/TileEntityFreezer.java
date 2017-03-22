package com.legacy.aether.common.tile_entities;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.registry.AetherRegistry;
import com.legacy.aether.common.registry.objects.AetherFreezable;
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

		if (this.currentFreezable != null && (this.frozenItemStacks[0] == null || this.frozenItemStacks[0].getItem() != this.currentFreezable.getFreezableInput().getItem()))
		{
			this.currentFreezable = null;
			this.freezeProgress = 0;
		}

		if (this.currentFreezable != null && this.freezeProgress >= this.currentFreezable.getTimeRequired())
		{
			if (!this.worldObj.isRemote)
			{
				if (this.frozenItemStacks[2] == null)
				{
					this.setInventorySlotContents(2, new ItemStack(this.currentFreezable.getFrozenResult().getItem(), 1, this.currentFreezable.getFrozenResult().getItemDamage()));
				}
				else
				{
					this.setInventorySlotContents(2, new ItemStack(this.currentFreezable.getFrozenResult().getItem(), this.getStackInSlot(2).stackSize + 1, this.currentFreezable.getFrozenResult().getItemDamage()));
				}

				if (this.frozenItemStacks[0].getItem() == ItemsAether.skyroot_bucket && this.frozenItemStacks[0].getItemDamage() == 1)
				{
					this.setInventorySlotContents(0, new ItemStack(ItemsAether.skyroot_bucket));
				}
				else if (this.frozenItemStacks[0].getItem() == Items.WATER_BUCKET || this.frozenItemStacks[0].getItem() == Items.LAVA_BUCKET)
				{
					this.setInventorySlotContents(0, new ItemStack(Items.BUCKET));
				}
				else
				{
					this.decrStackSize(0, 1);
				}
			}

			this.freezeProgress = 0;
		}

		if (this.frozenTimeRemaining <= 0 && this.currentFreezable != null && this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() == Item.getItemFromBlock(BlocksAether.icestone))
		{
			this.frozenTimeRemaining += 500;

			if (!this.worldObj.isRemote)
			{
				this.decrStackSize(1, 1);
			}
		}

		if (this.currentFreezable == null)
		{
			ItemStack itemstack = this.getStackInSlot(0);

			for (int i = 0; i < AetherRegistry.getFreezableSize(); i++)
			{
				AetherFreezable freezable = AetherRegistry.getFreezable(i);

				if (itemstack != null && freezable != null && itemstack.getItem() == freezable.getFreezableInput().getItem() && itemstack.getItemDamage() == freezable.getFreezableInput().getItemDamage())
				{
					if (this.frozenItemStacks[2] == null)
					{
						this.currentFreezable = freezable;
						this.freezeTime = this.currentFreezable.getTimeRequired();
					}
					else if (this.frozenItemStacks[2].getItem() == freezable.getFrozenResult().getItem() && freezable.getFreezableInput().getMaxStackSize() > this.frozenItemStacks[2].stackSize)
					{
						this.currentFreezable = freezable;
						this.freezeTime = this.currentFreezable.getTimeRequired();
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

	public static boolean isItemFuel(ItemStack fuel)
	{
		return fuel.getItem() == Item.getItemFromBlock(BlocksAether.icestone);
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
			if (getFreezableResult(stackInSlot) != null)
			{
				return true;
			}
			else if (slot == 1 && isItemFuel(stackInSlot))
			{
				return true;
			}
		}

		return false;
	}

	public static ItemStack getFreezableResult(ItemStack stack) 
	{
		for (AetherFreezable enchants : AetherRegistry.getFreezables())
		{
			if (enchants.getFreezableInput().getItem() == stack.getItem())
			{
				return enchants.getFrozenResult().copy();
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