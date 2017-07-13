package com.legacy.aether.common.tile_entities;

import net.minecraft.inventory.ItemStackHelper;
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
import com.legacy.aether.common.registry.objects.AetherEnchantment;
import com.legacy.aether.common.tile_entities.util.AetherTileEntity;

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

		if (this.currentEnchantment != null && (this.enchantedItemStacks.get(0) == ItemStack.EMPTY || this.enchantedItemStacks.get(0).getItem() != this.currentEnchantment.getEnchantmentInput().getItem()))
		{
			this.currentEnchantment = null;
			this.enchantmentProgress = 0;
		}

		if (this.currentEnchantment != null && this.enchantmentProgress >= this.currentEnchantment.getTimeRequired())
		{
			if (!this.world.isRemote)
			{
				if (this.enchantedItemStacks.get(2) == ItemStack.EMPTY)
				{
					this.setInventorySlotContents(2, new ItemStack(this.currentEnchantment.getEnchantedResult().getItem(), 1, this.currentEnchantment.getEnchantedResult().getItemDamage()));
				}
				else
				{
					this.setInventorySlotContents(2, new ItemStack(this.currentEnchantment.getEnchantedResult().getItem(), this.getStackInSlot(2).getCount() + 1, this.currentEnchantment.getEnchantedResult().getItemDamage()));
				}

				this.decrStackSize(0, 1);
			}

			this.enchantmentProgress = 0;
		}

		if (this.enchantmentTimeRemaining <= 0 && this.currentEnchantment != null && this.getStackInSlot(1).getItem() == ItemsAether.ambrosium_shard)
		{
			this.enchantmentTimeRemaining += 500;

			if (!this.world.isRemote)
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

				if (enchantment != null && itemstack.getItem() == enchantment.getEnchantmentInput().getItem() && (itemstack.getItemDamage() == enchantment.getEnchantmentInput().getItemDamage() || itemstack.isItemStackDamageable()))
				{
					if (this.enchantedItemStacks.get(2) == ItemStack.EMPTY)
					{
						this.currentEnchantment = enchantment;
						this.enchantmentTime = this.currentEnchantment.getTimeRequired();
					}
					else if (this.enchantedItemStacks.get(2).getItem() == enchantment.getEnchantedResult().getItem() && enchantment.getEnchantmentInput().getMaxStackSize() > this.enchantedItemStacks.get(2).getCount())
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
		else if (slot == 1 && isItemFuel(stackInSlot))
		{
			return true;
		}
		else if (slot == 0 && getEnchantmentResult(stackInSlot) != ItemStack.EMPTY)
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

		return ItemStack.EMPTY;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? new int[] {2} : new int[] {0, 1};
	}

}