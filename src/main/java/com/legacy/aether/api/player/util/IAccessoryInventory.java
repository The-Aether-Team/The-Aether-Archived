package com.legacy.aether.api.player.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public interface IAccessoryInventory extends IInventory
{

	public void dropAccessories();

	public void damageWornStack(int damage, ItemStack stack);

	public boolean setAccessorySlot(ItemStack stack);

	public boolean wearingAccessory(ItemStack stack);

	public boolean wearingArmor(ItemStack stack);

	public void writeToNBT(NBTTagCompound compound);

	public void readFromNBT(NBTTagCompound list);

	public void writeData(ByteBuf buf);

	public void readData(ByteBuf buf);

	public boolean isWearingZaniteSet();

	public boolean isWearingGravititeSet();

	public boolean isWearingNeptuneSet();

	public boolean isWearingPhoenixSet();

	public boolean isWearingObsidianSet();

	public boolean isWearingValkyrieSet();

	public NonNullList<ItemStack> getAccessories();

	public int getAccessoryCount(ItemStack stack);

}
