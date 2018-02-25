package com.legacy.aether.api;

import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.enchantments.AetherEnchantmentFuel;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.api.freezables.AetherFreezableFuel;
import com.legacy.aether.api.moa.AetherMoaType;

@Mod.EventBusSubscriber(modid = "aether_legacy")
public class AetherAPI
{

    private static IForgeRegistry<AetherAccessory> iAccessoryRegistry;
    private static IForgeRegistry<AetherEnchantment> iEnchantmentRegistry;
    private static IForgeRegistry<AetherEnchantmentFuel> iEnchantmentFuelRegistry;
    private static IForgeRegistry<AetherFreezable> iFreezableRegistry;
    private static IForgeRegistry<AetherFreezableFuel> iFreezableFuelRegistry;
    private static IForgeRegistry<AetherMoaType> iMoaTypeRegistry;

    private static final int MAX_REGISTRY_ID = Short.MAX_VALUE - 1;
   
    private static final AetherAPI instance = new AetherAPI();

	public AetherAPI()
    {

    }

	@SubscribeEvent
	public static void onMakeRegistries(RegistryEvent.NewRegistry event)
	{
    	iAccessoryRegistry = makeRegistry(new ResourceLocation("aetherAPI:accessories"), AetherAccessory.class, 0, MAX_REGISTRY_ID).create();
    	iEnchantmentRegistry = makeRegistry(new ResourceLocation("aetherAPI:enchantments"), AetherEnchantment.class, 0, MAX_REGISTRY_ID).create();
    	iEnchantmentFuelRegistry = makeRegistry(new ResourceLocation("aetherAPI:enchantment_fuels"), AetherEnchantmentFuel.class, 0, MAX_REGISTRY_ID).create();
    	iFreezableRegistry = makeRegistry(new ResourceLocation("aetherAPI:freezables"), AetherFreezable.class, 0, MAX_REGISTRY_ID).create();
    	iFreezableFuelRegistry = makeRegistry(new ResourceLocation("aetherAPI:freezable_fuels"), AetherFreezableFuel.class, 0, MAX_REGISTRY_ID).create();
    	iMoaTypeRegistry = makeRegistry(new ResourceLocation("aetherAPI:moa_types"), AetherMoaType.class, 0, MAX_REGISTRY_ID).create();
	}

    private static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> makeRegistry(ResourceLocation name, Class<T> type, int min, int max)
    {
        return new RegistryBuilder<T>().setName(name).setType(type).setIDRange(min, max).addCallback(new NamespacedWrapper.Factory<T>());
    }

    public boolean isAccessory(ItemStack stack)
    {
    	return iAccessoryRegistry.containsKey(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
    }

	public AetherAccessory getAccessory(ItemStack stack) 
	{
		return iAccessoryRegistry.getValue(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
	}

	public boolean hasEnchantment(ItemStack stack) 
	{
		return iEnchantmentRegistry.containsKey(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
	}

	public AetherEnchantment getEnchantment(ItemStack stack) 
	{
		return iEnchantmentRegistry.getValue(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
	}

	public boolean isEnchantmentFuel(ItemStack stack) 
	{
		return iEnchantmentFuelRegistry.containsKey(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
	}

	public AetherEnchantmentFuel getEnchantmentFuel(ItemStack stack) 
	{
		return iEnchantmentFuelRegistry.getValue(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
	}

	public boolean hasFreezable(ItemStack stack) 
	{
		return iFreezableRegistry.containsKey(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
	}

	public AetherFreezable getFreezable(ItemStack stack) 
	{
		return iFreezableRegistry.getValue(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
	}

	public boolean isFreezableFuel(ItemStack stack) 
	{
		return iFreezableFuelRegistry.containsKey(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
	}

	public AetherFreezableFuel getFreezableFuel(ItemStack stack) 
	{
		return iFreezableFuelRegistry.getValue(new ResourceLocation(stack.getItem().getRegistryName().toString() + "_meta_" + (stack.isItemStackDamageable() ? 0 : stack.getMetadata())));
	}

    public List<AetherEnchantment> getEnchantmentValues()
    {
    	return iEnchantmentRegistry.getValues();
    }

    public List<AetherFreezable> getFreezableValues()
    {
    	return iFreezableRegistry.getValues();
    }

    public int getMoaTypeId(AetherMoaType type)
    {
    	return iMoaTypeRegistry.getValues().indexOf(type);
    }

    public AetherMoaType getMoaType(int id)
    {
    	return iMoaTypeRegistry.getValues().get(id);
    }

    public AetherMoaType getRandomMoaType()
    {
    	return iMoaTypeRegistry.getValues().get(new Random().nextInt(this.getMoaTypeSize()));
    }

    public int getMoaTypeSize()
    {
    	return iMoaTypeRegistry.getEntries().size();
    }

    public static AetherAPI getInstance()
    {
    	return instance;
    }

}