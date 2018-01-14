package com.legacy.aether.tile_entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;

import com.legacy.aether.api.events.AetherHooks;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.container.BlockAetherContainer;
import com.legacy.aether.entities.passive.mountable.EntityMoa;
import com.legacy.aether.items.ItemMoaEgg;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.tile_entities.util.AetherTileEntity;

public class TileEntityIncubator extends AetherTileEntity
{

	public EntityPlayer owner;

	public int progress;

	public int powerRemaining;

	public int ticksRequired = 5700;

	private NonNullList<ItemStack> incubatorItemStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);

	public TileEntityIncubator()
	{
		super("incubator");
	}

	@Override
	public NonNullList<ItemStack> getTileInventory() 
	{
		return this.incubatorItemStacks;
	}

	@Override
	public void onSlotChanged(int index) 
	{
		if (index == 1)
		{
			this.progress = 0;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);

		this.progress = nbttagcompound.getInteger("IncubationProgress");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setShort("IncubationProgress", (short) this.progress);

		return super.writeToNBT(nbttagcompound);
	}

	public int getProgressScaled(int i)
	{
		return (this.progress * i) / this.ticksRequired;
	}

	public int getPowerTimeRemainingScaled(int i)
	{
		return (this.powerRemaining * i) / 500;
	}

	public boolean isIncubating()
	{
		return this.getField(1) > 0;
	}

	@Override
	public void update()
	{
		boolean flag = this.isIncubating();

		if (this.powerRemaining > 0)
		{
			this.powerRemaining--;

			if (!this.getStackInSlot(1).isEmpty())
			{
				if (this.world.getBlockState(this.getPos().down()).getBlock() == Blocks.MAGMA)
				{
					this.progress += 2F;
				}
				else
				{
					this.progress++;
				}
			}
		}

		if (this.progress >= this.ticksRequired)
		{
			if (this.getStackInSlot(1).getItem() instanceof ItemMoaEgg)
			{
				ItemMoaEgg moaEgg = (ItemMoaEgg) this.getStackInSlot(1).getItem();

				if (!this.world.isRemote)
				{
					EntityMoa moa = new EntityMoa(this.world);

					moa.setPlayerGrown(true);
					moa.setGrowingAge(-24000);
					moa.setMoaType(moaEgg.getMoaTypeFromItemStack(this.getStackInSlot(1)));

					for (int safeY = 0; !this.world.isAirBlock(this.pos.up(safeY)); safeY++)
					{
						moa.setPositionAndUpdate(this.pos.getX() + 0.5D, this.pos.getY() + safeY + 1.5D, this.pos.getZ() + 0.5D);
					}

					this.world.spawnEntity(moa);
				}

				AetherHooks.onMoaHatched(moaEgg.getMoaTypeFromItemStack(this.getStackInSlot(1)), this);
			}

			if (!this.world.isRemote)
			{
				this.decrStackSize(1, 1);
			}

			this.progress = 0;
		}

		if (this.powerRemaining <= 0)
		{
			if (this.getStackInSlot(1).getItem() == ItemsAether.moa_egg && this.getStackInSlot(0).getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch))
			{
				this.powerRemaining += 1000;

				if (!this.world.isRemote)
				{
					this.decrStackSize(0, 1);
				}
			}
			else
			{
				this.powerRemaining = 0;
				this.progress = 0;
			}
		}

		if (flag != this.isIncubating())
		{
			this.markDirty();
			BlockAetherContainer.setState(this.world, this.pos, this.isIncubating());
		}
	}

	@Override
	public boolean isValidSlotItem(int index, ItemStack itemstack)
	{
		return (index == 0 && itemstack.getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch) ? true : (index == 1 && itemstack.getItem() == ItemsAether.moa_egg));
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? new int[] {} : new int[] {0, 1};
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
	}

	@Override
	public int getFieldCount() 
	{
		return 2; 
	}

}