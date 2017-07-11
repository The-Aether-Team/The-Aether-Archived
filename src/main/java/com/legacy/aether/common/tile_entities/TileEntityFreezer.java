package com.legacy.aether.common.tile_entities;

import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.registry.AetherRegistry;
import com.legacy.aether.common.registry.objects.AetherFreezable;
import com.legacy.aether.common.tile_entities.util.AetherTileEntity;

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

		if (this.currentFreezable != null && (this.frozenItemStacks.get(0) == ItemStack.EMPTY || this.frozenItemStacks.get(0).getItem() != this.currentFreezable.getFreezableInput().getItem()))
		{
			this.currentFreezable = null;
			this.freezeProgress = 0;
		}

		if (this.currentFreezable != null && this.freezeProgress >= this.currentFreezable.getTimeRequired())
		{
			if (!this.world.isRemote)
			{
				if (this.frozenItemStacks.get(2) == ItemStack.EMPTY)
				{
					this.setInventorySlotContents(2, new ItemStack(this.currentFreezable.getFrozenResult().getItem(), 1, this.currentFreezable.getFrozenResult().getItemDamage()));
				}
				else
				{
					this.setInventorySlotContents(2, new ItemStack(this.currentFreezable.getFrozenResult().getItem(), this.getStackInSlot(2).getCount() + 1, this.currentFreezable.getFrozenResult().getItemDamage()));
				}

				if (this.frozenItemStacks.get(0).getItem() == ItemsAether.skyroot_bucket && this.frozenItemStacks.get(0).getItemDamage() == 1)
				{
					this.setInventorySlotContents(0, new ItemStack(ItemsAether.skyroot_bucket));
				}
				else if (this.frozenItemStacks.get(0).getItem() == Items.WATER_BUCKET || this.frozenItemStacks.get(0).getItem() == Items.LAVA_BUCKET)
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

		if (this.frozenTimeRemaining <= 0 && this.currentFreezable != null && this.getStackInSlot(1).getItem() == Item.getItemFromBlock(BlocksAether.icestone))
		{
			this.frozenTimeRemaining += 500;

			if (!this.world.isRemote)
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

				if (freezable != null && itemstack.getItem() == freezable.getFreezableInput().getItem() && itemstack.getItemDamage() == freezable.getFreezableInput().getItemDamage())
				{
					if (this.frozenItemStacks.get(2) == ItemStack.EMPTY)
					{
						this.currentFreezable = freezable;
						this.freezeTime = this.currentFreezable.getTimeRequired();
					}
					else if (this.frozenItemStacks.get(2).getItem() == freezable.getFrozenResult().getItem() && freezable.getFreezableInput().getMaxStackSize() > this.frozenItemStacks.get(2).getCount())
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
		if (getFreezableResult(stackInSlot) != ItemStack.EMPTY)
		{
			return true;
		}
		else if (slot == 1 && isItemFuel(stackInSlot))
		{
			return true;
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

		return ItemStack.EMPTY;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? new int[] {2} : new int[] {0, 1};
	}

}