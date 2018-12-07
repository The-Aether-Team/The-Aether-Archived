package com.legacy.aether.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.enchantments.AetherEnchantmentFuel;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.api.freezables.AetherFreezableFuel;
import com.legacy.aether.api.moa.AetherMoaType;
import com.legacy.aether.api.player.IPlayerAether;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AetherAPI {

	private static final HashMap<ResourceLocation, AetherAccessory> iAccessoryRegistry = new HashMap<ResourceLocation, AetherAccessory>(4096);
	private static final HashMap<ResourceLocation, AetherEnchantment> iEnchantmentRegistry = new HashMap<ResourceLocation, AetherEnchantment>(4096);
	private static final HashMap<ResourceLocation, AetherEnchantmentFuel> iEnchantmentFuelRegistry = new HashMap<ResourceLocation, AetherEnchantmentFuel>(4096);
	private static final HashMap<ResourceLocation, AetherFreezable> iFreezableRegistry = new HashMap<ResourceLocation, AetherFreezable>(4096);
	private static final HashMap<ResourceLocation, AetherFreezableFuel> iFreezableFuelRegistry = new HashMap<ResourceLocation, AetherFreezableFuel>(4096);
	private static final HashMap<ResourceLocation, AetherMoaType> iMoaTypeRegistry = new HashMap<ResourceLocation, AetherMoaType>(4096);

	private static final AetherAPI instance = new AetherAPI();

	public static final IPlayerAether get(EntityPlayer playerIn) {
		return (IPlayerAether) playerIn.getExtendedProperties("aether_legacy:player_aether");
	}

	public AetherAccessory register(AetherAccessory type) {
		ItemStack stack = type.getAccessoryStack();
		ResourceLocation registryName = new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage()));

		iAccessoryRegistry.put(registryName, (AetherAccessory) type.setRegistryName(registryName));

		return type;
	}

	public AetherEnchantment register(AetherEnchantment type) {
		ItemStack stack = type.getInput();
		ResourceLocation registryName = new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage()));

		iEnchantmentRegistry.put(registryName, (AetherEnchantment) type.setRegistryName(registryName));

		return type;
	}

	public AetherEnchantmentFuel register(AetherEnchantmentFuel type) {
		ItemStack stack = type.getFuelStack();
		ResourceLocation registryName = new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage()));

		iEnchantmentFuelRegistry.put(registryName, (AetherEnchantmentFuel) type.setRegistryName(registryName));

		return type;
	}

	public AetherFreezable register(AetherFreezable type) {
		ItemStack stack = type.getInput();
		ResourceLocation registryName = new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage()));

		iFreezableRegistry.put(registryName, (AetherFreezable) type.setRegistryName(registryName));

		return type;
	}

	public AetherFreezableFuel register(AetherFreezableFuel type) {
		ItemStack stack = type.getFuelStack();
		ResourceLocation registryName = new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage()));

		iFreezableFuelRegistry.put(registryName, (AetherFreezableFuel) type.setRegistryName(registryName));

		return type;
	}

	public AetherMoaType register(String modId, String name, AetherMoaType type) {
		iMoaTypeRegistry.put(new ResourceLocation(modId, name), (AetherMoaType) type.setRegistryName(modId, name));

		return type;
	}

	public AetherMoaType register(ResourceLocation registryName, AetherMoaType type) {
		iMoaTypeRegistry.put(registryName, (AetherMoaType) type.setRegistryName(registryName));

		return type;
	}

	public boolean isAccessory(ItemStack stack) {
		return iAccessoryRegistry.containsKey(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public AetherAccessory getAccessory(ItemStack stack) {
		return iAccessoryRegistry.get(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public boolean hasEnchantment(ItemStack stack) {
		return iEnchantmentRegistry.containsKey(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public AetherEnchantment getEnchantment(ItemStack stack) {
		return iEnchantmentRegistry.get(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public boolean isEnchantmentFuel(ItemStack stack) {
		return iEnchantmentFuelRegistry.containsKey(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public AetherEnchantmentFuel getEnchantmentFuel(ItemStack stack) {
		return iEnchantmentFuelRegistry.get(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public boolean hasFreezable(ItemStack stack) {
		return iFreezableRegistry.containsKey(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public AetherFreezable getFreezable(ItemStack stack) {
		return iFreezableRegistry.get(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public boolean isFreezableFuel(ItemStack stack) {
		return iFreezableFuelRegistry.containsKey(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public AetherFreezableFuel getFreezableFuel(ItemStack stack) {
		return iFreezableFuelRegistry.get(new ResourceLocation(stack.getItem().getUnlocalizedName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getItemDamage())));
	}

	public List<AetherEnchantment> getEnchantmentValues() {
		return new ArrayList<AetherEnchantment>(iEnchantmentRegistry.values());
	}

	public List<AetherFreezable> getFreezableValues() {
		return new ArrayList<AetherFreezable>(iFreezableRegistry.values());
	}

	public List<AetherMoaType> getMoaTypeValues() {
		return new ArrayList<AetherMoaType>(iMoaTypeRegistry.values());
	}

	public int getMoaTypeId(AetherMoaType type) {
		return this.getMoaTypeValues().indexOf(type);
	}

	public AetherMoaType getMoaType(int id) {
		return this.getMoaTypeValues().get(id);
	}

	public AetherMoaType getRandomMoaType() {
		return this.getMoaTypeValues().get(new Random().nextInt(this.getMoaTypeSize()));
	}

	public int getMoaTypeSize() {
		return this.getMoaTypeValues().size();
	}

	public static AetherAPI instance() {
		return instance;
	}

}