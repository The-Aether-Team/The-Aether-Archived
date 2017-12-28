package com.legacy.aether.tile_entities;

import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.events.AetherHooks;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.container.BlockAetherContainer;
import com.legacy.aether.tile_entities.util.AetherTileEntity;

public class TileEntityEnchanter extends AetherTileEntity
{

	public int progress, ticksRequired, powerRemaining;

	private ItemStack[] enchantedItemStacks = new ItemStack[3];

	private AetherEnchantment currentEnchantment;

	public TileEntityEnchanter() 
	{
		super("altar");
	}

	@Override
	public ItemStack[] getTileInventory() 
	{
		return this.enchantedItemStacks;
	}

	@Override
	public void onSlotChanged(int index) 
	{

	}

	@Override
	public void update()
	{
        boolean flag = this.isEnchanting();

		if (this.powerRemaining > 0)
		{
			this.powerRemaining--;

			if (this.currentEnchantment != null)
			{
				if (this.worldObj.getBlockState(this.getPos().down()).getBlock() == BlocksAether.enchanted_gravitite)
				{
					this.progress += 2;
				}
				else
				{
					this.progress++;
				}
			}
		}

		if (this.currentEnchantment != null)
		{
			if (this.getStackInSlot(0) == null || this.getStackInSlot(0) != null && !AetherAPI.getInstance().getEnchantment(this.getStackInSlot(0)).equals(this.currentEnchantment))
			{
				this.currentEnchantment = null;
				this.progress = 0;

				return;
			}

			if (this.progress >= this.ticksRequired)
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

				this.progress = 0;

				AetherHooks.onItemEnchant(this, this.currentEnchantment);
			}

			if (this.powerRemaining <= 0)
			{
				if (this.getStackInSlot(1) != null && AetherAPI.getInstance().isEnchantmentFuel(this.getStackInSlot(1)))
				{
					this.powerRemaining += AetherAPI.getInstance().getEnchantmentFuel(this.getStackInSlot(1)).getTimeGiven();

					if (!this.worldObj.isRemote)
					{
						this.decrStackSize(1, 1);
					}
				}
				else
				{
					this.progress = 0;
					this.powerRemaining = 0;
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
						this.ticksRequired = this.currentEnchantment.getTimeRequired();
						this.addEnchantmentWeight(itemstack);
						this.ticksRequired = AetherHooks.onSetEnchantmentTime(this, this.currentEnchantment, this.ticksRequired);
					}
				}
			}
		}

		if (flag != this.isEnchanting())
		{
			this.markDirty();
			BlockAetherContainer.setState(this.worldObj, this.pos, this.isEnchanting());
		}
	}

	public void addEnchantmentWeight(ItemStack stack)
	{
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);

		if (!enchantments.isEmpty())
		{
			for (int levels : enchantments.values())
			{
				this.ticksRequired += (levels * 1250);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public int getEnchantmentProgressScaled(int i)
	{
		if (this.ticksRequired == 0)
		{
			return 0;
		}
		return (this.progress * i) / this.ticksRequired;
	}

	@SideOnly(Side.CLIENT)
	public int getEnchantmentTimeRemaining(int i)
	{
		return (this.powerRemaining * i) / 500;
	}

	public boolean isEnchanting()
	{
		return this.powerRemaining > 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		this.powerRemaining = nbttagcompound.getShort("EnchantmentPowerRemaining");
		this.ticksRequired = nbttagcompound.getShort("EnchantmentTimeRequired");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setShort("EnchantmentPowerRemaining", (short) this.powerRemaining);
		nbttagcompound.setShort("EnchantmentTimeRequired", (short) this.ticksRequired);

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

	@Override
	public int getField(int id)
	{
		if (id == 0)
		{
			return this.progress;
		}
		else if (id == 1)
		{
			return this.powerRemaining;
		}
		else if (id == 2)
		{
			return this.ticksRequired;
		}

		return 0; 
	}

	@Override
	public void setField(int id, int value) 
	{ 
		if (id == 0)
		{
			this.progress = value;
		}
		else if (id == 1)
		{
			this.powerRemaining = value;
		}
		else if (id == 2)
		{
			this.ticksRequired = value;
		}
	}

	@Override
	public int getFieldCount() 
	{
		return 3; 
	}

}