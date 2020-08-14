package com.gildedgames.the_aether.api.player.util;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.gildedgames.the_aether.api.accessories.AccessoryType;

public interface IAccessoryInventory extends IInventory {

	public void dropAccessories();

	public void damageAccessory(int damage, AccessoryType type);

	public void damageWornStack(int damage, ItemStack stack);

	public void setAccessorySlot(AccessoryType type, ItemStack stack);

	public ItemStack getStackInSlot(AccessoryType type);

	public ItemStack removeStackFromAccessorySlot(AccessoryType type);

	public boolean setAccessorySlot(ItemStack stack);

	public boolean wearingAccessory(ItemStack stack);

	public boolean wearingArmor(ItemStack stack);

	public NBTTagList writeToNBT(NBTTagCompound compound);

	public void readFromNBT(NBTTagList list);

	public void writeData(ByteBuf buf);

	public void readData(ByteBuf buf);

	public boolean isWearingZaniteSet();

	public boolean isWearingGravititeSet();

	public boolean isWearingNeptuneSet();

	public boolean isWearingPhoenixSet();

	public boolean isWearingObsidianSet();

	public boolean isWearingValkyrieSet();

	public List<ItemStack> getAccessories();

	public int getAccessoryCount(ItemStack stack);

}