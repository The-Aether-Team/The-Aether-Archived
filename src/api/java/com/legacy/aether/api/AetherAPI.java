package com.legacy.aether.api;

import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;

import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.enchantments.AetherEnchantmentFuel;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.api.freezables.AetherFreezableFuel;
import com.legacy.aether.api.moa.AetherMoaType;

public class AetherAPI
{

    private final FMLControlledNamespacedRegistry<AetherAccessory> iAccessoryRegistry;
    private final FMLControlledNamespacedRegistry<AetherEnchantment> iEnchantmentRegistry;
    private final FMLControlledNamespacedRegistry<AetherEnchantmentFuel> iEnchantmentFuelRegistry;
    private final FMLControlledNamespacedRegistry<AetherFreezable> iFreezableRegistry;
    private final FMLControlledNamespacedRegistry<AetherFreezableFuel> iFreezableFuelRegistry;
    private final FMLControlledNamespacedRegistry<AetherMoaType> iMoaTypeRegistry;

    private static final int MAX_REGISTRY_ID = Short.MAX_VALUE - 1;
   
    private static final AetherAPI instance = new AetherAPI();

    @SuppressWarnings("deprecation")
	public AetherAPI()
    {
    	this.iAccessoryRegistry = PersistentRegistryManager.createRegistry(new ResourceLocation("aetherAPI:accessories"), AetherAccessory.class, null, 0, MAX_REGISTRY_ID, false, null, null, null, null);
    	this.iEnchantmentRegistry = PersistentRegistryManager.createRegistry(new ResourceLocation("aetherAPI:enchantments"), AetherEnchantment.class, null, 0, MAX_REGISTRY_ID, false, null, null, null, null);
    	this.iEnchantmentFuelRegistry = PersistentRegistryManager.createRegistry(new ResourceLocation("aetherAPI:enchantment_fuels"), AetherEnchantmentFuel.class, null, 0, MAX_REGISTRY_ID, false, null, null, null, null);
    	this.iFreezableRegistry = PersistentRegistryManager.createRegistry(new ResourceLocation("aetherAPI:freezables"), AetherFreezable.class, null, 0, MAX_REGISTRY_ID, false, null, null, null, null);
    	this.iFreezableFuelRegistry = PersistentRegistryManager.createRegistry(new ResourceLocation("aetherAPI:freezable_fuels"), AetherFreezableFuel.class, null, 0, MAX_REGISTRY_ID, false, null, null, null, null);
    	this.iMoaTypeRegistry = PersistentRegistryManager.createRegistry(new ResourceLocation("aetherAPI:moa_types"), AetherMoaType.class, null, 0, MAX_REGISTRY_ID, false, null, null, null, null);
    }

    public AetherAccessory register(AetherAccessory type)
    {
    	ItemStack stack = type.getAccessoryStack();
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

    	this.iAccessoryRegistry.register(type.setRegistryName(registryName));

    	return type;
    }

    public AetherEnchantment register(AetherEnchantment type)
    {
    	ItemStack stack = type.getInput();
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

    	this.iEnchantmentRegistry.register(type.setRegistryName(registryName));

    	return type;
    }

    public AetherEnchantmentFuel register(AetherEnchantmentFuel type)
    {
    	ItemStack stack = type.getFuelStack();
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

    	this.iEnchantmentFuelRegistry.register(type.setRegistryName(registryName));

    	return type;
    }

    public AetherFreezable register(AetherFreezable type)
    {
    	ItemStack stack = type.getInput();
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

    	this.iFreezableRegistry.register(type.setRegistryName(registryName));

    	return type;
    }

    public AetherFreezableFuel register(AetherFreezableFuel type)
    {
    	ItemStack stack = type.getFuelStack();
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

    	this.iFreezableFuelRegistry.register(type.setRegistryName(registryName));

    	return type;
    }

    public AetherMoaType register(String modId, String name, AetherMoaType type)
    {
    	this.iMoaTypeRegistry.register(type.setRegistryName(modId, name));

    	return type;
    }

    public AetherMoaType register(ResourceLocation registryName, AetherMoaType type)
    {
    	this.iMoaTypeRegistry.register(type.setRegistryName(registryName));

    	return type;
    }

    public boolean isAccessory(ItemStack stack)
    {
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

    	return this.iAccessoryRegistry.containsKey(registryName);
    }

	public AetherAccessory getAccessory(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iAccessoryRegistry.getObject(registryName);
	}

	public boolean hasEnchantment(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iEnchantmentRegistry.containsKey(registryName);
	}

	public AetherEnchantment getEnchantment(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iEnchantmentRegistry.getObject(registryName);
	}

	public boolean isEnchantmentFuel(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iEnchantmentFuelRegistry.containsKey(registryName);
	}

	public AetherEnchantmentFuel getEnchantmentFuel(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iEnchantmentFuelRegistry.getObject(registryName);
	}

	public boolean hasFreezable(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iFreezableRegistry.containsKey(registryName);
	}

	public AetherFreezable getFreezable(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iFreezableRegistry.getObject(registryName);
	}

	public boolean isFreezableFuel(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iFreezableFuelRegistry.containsKey(registryName);
	}

	public AetherFreezableFuel getFreezableFuel(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iFreezableFuelRegistry.getObject(registryName);
	}

    public List<AetherEnchantment> getEnchantmentValues()
    {
    	return this.iEnchantmentRegistry.getValues();
    }

    public List<AetherFreezable> getFreezableValues()
    {
    	return this.iFreezableRegistry.getValues();
    }

    public int getMoaTypeId(AetherMoaType type)
    {
    	return this.iMoaTypeRegistry.getId(type);
    }

    public AetherMoaType getMoaType(int id)
    {
    	return this.iMoaTypeRegistry.getObjectById(id);
    }

    public AetherMoaType getRandomMoaType()
    {
    	return this.iMoaTypeRegistry.getRandomObject(new Random());
    }

    public int getMoaTypeSize()
    {
    	return this.iMoaTypeRegistry.getEntries().size();
    }

    public static AetherAPI getInstance()
    {
    	return instance;
    }

}