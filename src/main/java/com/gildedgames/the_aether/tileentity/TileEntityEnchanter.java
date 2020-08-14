package com.gildedgames.the_aether.tileentity;

import java.util.List;
import java.util.Map;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.events.AetherHooks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.the_aether.api.enchantments.AetherEnchantment;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.container.BlockAetherContainer;
import com.gildedgames.the_aether.tileentity.util.AetherTileEntity;
import com.gildedgames.the_aether.util.FilledList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityEnchanter extends AetherTileEntity {

	public int progress, ticksRequired, powerRemaining;

	private final FilledList<ItemStack> enchantedItemStacks = new FilledList<ItemStack>(3, null);

	private AetherEnchantment currentEnchantment;

	public TileEntityEnchanter() {
		super("Altar");
	}

	@Override
	public List<ItemStack> getTileInventory() {
		return this.enchantedItemStacks;
	}

	@Override
	public void onSlotChanged(int index) {

	}

	@Override
	public void updateEntity() {
		boolean flag = this.isEnchanting();

		if (this.powerRemaining > 0) {
			this.powerRemaining--;

			if (this.currentEnchantment != null) {
				if (this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == BlocksAether.enchanted_gravitite) {
					this.progress += 2.5F;
				} else {
					this.progress++;
				}
			}
		}

		if (this.currentEnchantment != null) {
			if (this.progress >= this.ticksRequired) {
				if (!this.worldObj.isRemote) {
					ItemStack result = this.currentEnchantment.getOutput().copy();

					EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(this.getStackInSlot(0)), result);

					if (this.getStackInSlot(0).hasTagCompound())
					{
						result.setTagCompound(this.getStackInSlot(0).getTagCompound());
					}

					if (this.getStackInSlot(2) != null && this.getStackInSlot(2).isStackable()) {
						result.stackSize += (this.getStackInSlot(2).stackSize);

						this.setInventorySlotContents(2, result);
					} else {
						this.setInventorySlotContents(2, result);
					}

					if (this.getStackInSlot(0).getItem().hasContainerItem(this.getStackInSlot(0))) {
						this.setInventorySlotContents(0, this.getStackInSlot(0).getItem().getContainerItem(this.getStackInSlot(0)));
					} else {
						this.decrStackSize(0, 1);
					}
				}

				this.progress = 0;
				AetherHooks.onItemEnchant(this, this.currentEnchantment);
			}

			if (this.getStackInSlot(0) == null || (this.getStackInSlot(0) != null && AetherAPI.instance().getEnchantment(this.getStackInSlot(0)) != this.currentEnchantment)) {
				this.currentEnchantment = null;
				this.progress = 0;
			}

			if (this.powerRemaining <= 0) {
				if (this.getStackInSlot(1) != null && AetherAPI.instance().isEnchantmentFuel(this.getStackInSlot(1))) {
					this.powerRemaining += AetherAPI.instance().getEnchantmentFuel(this.getStackInSlot(1)).getTimeGiven();

					if (!this.worldObj.isRemote) {
						this.decrStackSize(1, 1);
					}
				} else {
					this.currentEnchantment = null;
					this.progress = 0;
				}
			}
		} else if (this.getStackInSlot(0) != null) {
			ItemStack itemstack = this.getStackInSlot(0);
			AetherEnchantment enchantment = AetherAPI.instance().getEnchantment(itemstack);

			if (enchantment != null) {
				if (this.getStackInSlot(2) == null || (enchantment.getOutput().getItem() == this.getStackInSlot(2).getItem() && enchantment.getOutput().getItemDamage() == this.getStackInSlot(2).getItemDamage() && this.getStackInSlot(2).isStackable())) {
					this.currentEnchantment = enchantment;
					this.ticksRequired = this.currentEnchantment.getTimeRequired();
					this.addEnchantmentWeight(itemstack);
					this.ticksRequired = AetherHooks.onSetEnchantmentTime(this, this.currentEnchantment, this.ticksRequired);
				}
			}
		}

		if (flag != this.isEnchanting()) {
			this.markDirty();
			BlockAetherContainer.setState(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.isEnchanting());
		}
	}

	@SuppressWarnings("unchecked")
	public void addEnchantmentWeight(ItemStack stack) {
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);

		if (!enchantments.isEmpty()) {
			for (int levels : enchantments.values()) {
				this.ticksRequired += (levels * 1250);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public int getEnchantmentProgressScaled(int i) {
		if (this.ticksRequired == 0) {
			return 0;
		}

		return (this.progress * i) / this.ticksRequired;
	}

	@SideOnly(Side.CLIENT)
	public int getEnchantmentTimeRemaining(int i) {
		return (this.powerRemaining * i) / 500;
	}

	public boolean isEnchanting() {
		return this.powerRemaining > 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		this.progress = compound.getInteger("progress");
		this.powerRemaining = compound.getInteger("powerRemaining");
		this.ticksRequired = compound.getInteger("ticksRequired");
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("progress", this.progress);
		compound.setInteger("powerRemaining", this.powerRemaining);
		compound.setInteger("ticksRequired", this.ticksRequired);
	}

	@Override
	public boolean isValidSlotItem(int slot, ItemStack stackInSlot) {
		if (slot == 2) {
			return false;
		} else if (slot == 1 && AetherAPI.instance().isEnchantmentFuel(stackInSlot)) {
			return true;
		} else if (slot == 0 && AetherAPI.instance().hasEnchantment(stackInSlot)) {
			return true;
		}

		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? new int[]{2} : new int[]{0, 1};
	}

}