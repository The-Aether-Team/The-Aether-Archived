package com.gildedgames.the_aether.tileentity;

import java.util.List;

import com.gildedgames.the_aether.api.events.AetherHooks;
import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import com.gildedgames.the_aether.registry.achievements.AchievementsAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.container.BlockAetherContainer;
import com.gildedgames.the_aether.items.ItemMoaEgg;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.tileentity.util.AetherTileEntity;
import com.gildedgames.the_aether.util.FilledList;

public class TileEntityIncubator extends AetherTileEntity {

	public EntityPlayer owner;

	public int progress;

	public int powerRemaining;

	public int ticksRequired = 5700;

	private final FilledList<ItemStack> incubatorItemStacks = new FilledList<ItemStack>(3, null);

	public TileEntityIncubator() {
		super("Incubator");
	}

	@Override
	public List<ItemStack> getTileInventory() {
		return this.incubatorItemStacks;
	}

	@Override
	public void onSlotChanged(int index) {
		if (index == 1) {
			this.progress = 0;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		this.progress = compound.getInteger("progress");
		this.powerRemaining = compound.getInteger("powerRemaining");
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("progress", this.progress);
		compound.setInteger("powerRemaining", this.powerRemaining);
	}

	public int getProgressScaled(int i) {
		return (this.progress * i) / this.ticksRequired;
	}

	public int getPowerTimeRemainingScaled(int i) {
		return (this.powerRemaining * i) / 500;
	}

	public boolean isIncubating() {
		return this.powerRemaining > 0;
	}

	@Override
	public void updateEntity() {
		boolean flag = this.isIncubating();

		if (this.powerRemaining > 0) {
			this.powerRemaining--;

			if (this.getStackInSlot(1) != null) {
				this.progress++;
			}
		}

		if (this.progress >= this.ticksRequired) {
			if (this.getStackInSlot(1).getItem() instanceof ItemMoaEgg) {
				ItemMoaEgg moaEgg = (ItemMoaEgg) this.getStackInSlot(1).getItem();

				if (this.owner != null) {
					this.owner.triggerAchievement(AchievementsAether.incubator);
				}

				if (!this.worldObj.isRemote) {
					EntityMoa moa = new EntityMoa(this.worldObj);

					moa.setPlayerGrown(true);
					moa.setGrowingAge(-24000);
					moa.setMoaType(moaEgg.getMoaTypeFromItemStack(this.getStackInSlot(1)));

					for (int safeY = 0; !this.worldObj.isAirBlock(this.xCoord, this.yCoord + safeY, this.zCoord); safeY++) {
						moa.setPositionAndUpdate(this.xCoord + 0.5D, this.yCoord + safeY + 1.5D, this.zCoord + 0.5D);
					}

					this.worldObj.spawnEntityInWorld(moa);
				}

				AetherHooks.onMoaHatched(moaEgg.getMoaTypeFromItemStack(this.getStackInSlot(1)), this);
			}

			if (!this.worldObj.isRemote) {
				this.decrStackSize(1, 1);
			}

			this.progress = 0;
		}

		if (this.powerRemaining <= 0) {
			if (this.getStackInSlot(0) != null && this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() == ItemsAether.moa_egg && this.getStackInSlot(0).getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch)) {
				this.powerRemaining += 1000;

				if (!this.worldObj.isRemote) {
					this.decrStackSize(0, 1);
				}
			} else {
				this.powerRemaining = 0;
				this.progress = 0;
			}
		}

		if (flag != this.isIncubating()) {
			this.markDirty();
			BlockAetherContainer.setState(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.isIncubating());
		}
	}

	@Override
	public boolean isValidSlotItem(int index, ItemStack itemstack) {
		return (index == 0 && itemstack.getItem() == Item.getItemFromBlock(BlocksAether.ambrosium_torch) ? true : (index == 1 && itemstack.getItem() == ItemsAether.moa_egg));
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? new int[]{} : new int[]{0, 1};
	}

}