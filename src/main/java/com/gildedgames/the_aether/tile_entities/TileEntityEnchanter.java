package com.gildedgames.the_aether.tile_entities;

import java.util.Map;

import com.gildedgames.the_aether.tile_entities.util.AetherTileEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.enchantments.AetherEnchantment;
import com.gildedgames.the_aether.api.events.AetherHooks;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.container.BlockAetherContainer;

public class TileEntityEnchanter extends AetherTileEntity
{

	public int progress, ticksRequired, powerRemaining;

	private NonNullList<ItemStack> enchantedItemStacks = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);

	private AetherEnchantment currentEnchantment;

	public TileEntityEnchanter() 
	{
		super("enchanter");
	}

	@Override
	public NonNullList<ItemStack> getTileInventory() 
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
				if (this.world.getBlockState(this.getPos().down()).getBlock() == BlocksAether.enchanted_gravitite)
				{
					this.progress += 2.5F;
				}
				else
				{
					this.progress++;
				}
			}
		}

		if (this.currentEnchantment != null)
		{
			if (this.progress >= this.ticksRequired)
			{
				if (!this.world.isRemote)
				{
					ItemStack result = this.currentEnchantment.getOutput().copy();

					EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(this.getStackInSlot(0)), result);

					if (this.getStackInSlot(0).hasTagCompound())
					{
						result.setTagCompound(this.getStackInSlot(0).getTagCompound());
					}

					if (!this.getStackInSlot(2).isEmpty() && this.getStackInSlot(2).isStackable())
					{
						result.setCount(this.getStackInSlot(2).getCount() + 1);

						this.setInventorySlotContents(2, result);
					}
					else
					{
						this.setInventorySlotContents(2, result);
					}

					if (this.getStackInSlot(0).getItem().hasContainerItem(this.getStackInSlot(0)))
					{
						this.setInventorySlotContents(0, this.getStackInSlot(0).getItem().getContainerItem(this.getStackInSlot(0)));
					}
					else
					{
						this.decrStackSize(0, 1);
					}
				}

				this.progress = 0;
				AetherHooks.onItemEnchant(this, this.currentEnchantment);
			}

			if (this.getStackInSlot(0).isEmpty() || (!this.getStackInSlot(0).isEmpty() && AetherAPI.getInstance().getEnchantment(this.getStackInSlot(0)) != this.currentEnchantment))
			{
				this.currentEnchantment = null;
				this.progress = 0;
			}

			if (this.powerRemaining <= 0)
			{
				if (!this.getStackInSlot(1).isEmpty() && AetherAPI.getInstance().isEnchantmentFuel(this.getStackInSlot(1)))
				{
					this.powerRemaining += AetherAPI.getInstance().getEnchantmentFuel(this.getStackInSlot(1)).getTimeGiven();

					if (!this.world.isRemote)
					{
						this.decrStackSize(1, 1);
					}
				}
				else
				{
					this.currentEnchantment = null;
					this.progress = 0;
				}
			}
		}
		else if (!this.getStackInSlot(0).isEmpty())
		{
			ItemStack itemstack = this.getStackInSlot(0);
			AetherEnchantment enchantment = AetherAPI.getInstance().getEnchantment(itemstack);

			if (enchantment != null)
			{
				if (this.getStackInSlot(2).isEmpty() || (enchantment.getOutput().getItem() == this.getStackInSlot(2).getItem() && enchantment.getOutput().getMetadata() == this.getStackInSlot(2).getMetadata() && this.getStackInSlot(2).isStackable()))
				{
					this.currentEnchantment = enchantment;
					this.ticksRequired = this.currentEnchantment.getTimeRequired();
					this.addEnchantmentWeight(itemstack);
					this.ticksRequired = AetherHooks.onSetEnchantmentTime(this, this.currentEnchantment, this.ticksRequired);
				}
			}
		}

		if (flag != this.isEnchanting())
		{
			this.markDirty();
			BlockAetherContainer.setState(this.world, this.pos, this.isEnchanting());
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
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		this.progress = compound.getInteger("progress");
		this.powerRemaining = compound.getInteger("powerRemaining");
		this.ticksRequired = compound.getInteger("ticksRequired");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setInteger("progress", this.progress);
		compound.setInteger("powerRemaining", this.powerRemaining);
		compound.setInteger("ticksRequired", this.ticksRequired);

		return super.writeToNBT(compound);
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