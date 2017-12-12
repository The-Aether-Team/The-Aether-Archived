package com.legacy.aether.api;

import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.enchantments.AetherEnchantmentFuel;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.api.freezables.AetherFreezableFuel;
import com.legacy.aether.api.moa.AetherMoaType;

public class AetherAPI
{

    private final IForgeRegistry<AetherAccessory> iAccessoryRegistry;
    private final IForgeRegistry<AetherEnchantment> iEnchantmentRegistry;
    private final IForgeRegistry<AetherEnchantmentFuel> iEnchantmentFuelRegistry;
    private final IForgeRegistry<AetherFreezable> iFreezableRegistry;
    private final IForgeRegistry<AetherFreezableFuel> iFreezableFuelRegistry;
    private final IForgeRegistry<AetherMoaType> iMoaTypeRegistry;

    private static final int MAX_REGISTRY_ID = Short.MAX_VALUE - 1;
   
    private static final AetherAPI instance = new AetherAPI();

	public AetherAPI()
    {
    	this.iAccessoryRegistry = makeRegistry(new ResourceLocation("aetherAPI:accessories"), AetherAccessory.class, 0, MAX_REGISTRY_ID).create();
    	this.iEnchantmentRegistry = makeRegistry(new ResourceLocation("aetherAPI:enchantments"), AetherEnchantment.class, 0, MAX_REGISTRY_ID).create();
    	this.iEnchantmentFuelRegistry = makeRegistry(new ResourceLocation("aetherAPI:enchantment_fuels"), AetherEnchantmentFuel.class, 0, MAX_REGISTRY_ID).create();
    	this.iFreezableRegistry = makeRegistry(new ResourceLocation("aetherAPI:freezables"), AetherFreezable.class, 0, MAX_REGISTRY_ID).create();
    	this.iFreezableFuelRegistry = makeRegistry(new ResourceLocation("aetherAPI:freezable_fuels"), AetherFreezableFuel.class, 0, MAX_REGISTRY_ID).create();
    	this.iMoaTypeRegistry = makeRegistry(new ResourceLocation("aetherAPI:moa_types"), AetherMoaType.class, 0, MAX_REGISTRY_ID).create();
    }

    private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(ResourceLocation name, Class<T> type, int min, int max)
    {
        return new RegistryBuilder<T>().setName(name).setType(type).setIDRange(min, max).addCallback(new NamespacedWrapper.Factory<T>());
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

		return this.iAccessoryRegistry.getValue(registryName);
	}

	public boolean hasEnchantment(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iEnchantmentRegistry.containsKey(registryName);
	}

	public AetherEnchantment getEnchantment(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iEnchantmentRegistry.getValue(registryName);
	}

	public boolean isEnchantmentFuel(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iEnchantmentFuelRegistry.containsKey(registryName);
	}

	public AetherEnchantmentFuel getEnchantmentFuel(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iEnchantmentFuelRegistry.getValue(registryName);
	}

	public boolean hasFreezable(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iFreezableRegistry.containsKey(registryName);
	}

	public AetherFreezable getFreezable(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iFreezableRegistry.getValue(registryName);
	}

	public boolean isFreezableFuel(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iFreezableFuelRegistry.containsKey(registryName);
	}

	public AetherFreezableFuel getFreezableFuel(ItemStack stack) 
	{
    	ResourceLocation registryName = new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + stack.getMetadata());

		return this.iFreezableFuelRegistry.getValue(registryName);
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
    	return this.iMoaTypeRegistry.getValues().indexOf(type);
    }

    public AetherMoaType getMoaType(int id)
    {
    	return this.iMoaTypeRegistry.getValues().get(id);
    }

    public AetherMoaType getRandomMoaType()
    {
    	return this.iMoaTypeRegistry.getValues().get(new Random().nextInt(this.getMoaTypeSize()));
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