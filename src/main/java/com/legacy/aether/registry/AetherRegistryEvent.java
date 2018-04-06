package com.legacy.aether.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.legacy.aether.Aether;
import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.enchantments.AetherEnchantmentFuel;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.api.freezables.AetherFreezableFuel;
import com.legacy.aether.api.moa.AetherMoaType;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.dictionary.AetherDictionary;
import com.legacy.aether.entities.util.AetherMoaTypes;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.registry.sounds.SoundsAether;
import com.legacy.aether.world.AetherWorld;

public class AetherRegistryEvent 
{

	@SubscribeEvent
	public void onRegisterBlockEvent(RegistryEvent.Register<Block> event)
	{
		BlocksAether.registerBlocks(event.getRegistry());
	}

	@SubscribeEvent
	public void onRegisterItemEvent(RegistryEvent.Register<Item> event)
	{
		BlocksAether.registerItems(event.getRegistry());

		ItemsAether.itemRegistry = event.getRegistry();

		ItemsAether.initialization();
		AetherCreativeTabs.initialization();
	}

	@SubscribeEvent
	public void onRegisterBiomeEvent(RegistryEvent.Register<Biome> event)
	{
		event.getRegistry().register(AetherWorld.aether_biome.setRegistryName(Aether.locate("aether_highlands")));
	}

	@SubscribeEvent
	public void onRegisterSoundEvent(RegistryEvent.Register<SoundEvent> event)
	{
		SoundsAether.soundRegistry = event.getRegistry();

		SoundsAether.initialization();
	}

	@SubscribeEvent
	public void onRegisterCraftingEvent(RegistryEvent.Register<IRecipe> event)
	{
		AetherRegistries.craftingRegistry = event.getRegistry();

		AetherDictionary.initialization();
		AetherRegistries.registerRecipes();
	}

	@SubscribeEvent
	public void onRegisterMoaTypeEvent(RegistryEvent.Register<AetherMoaType> event)
	{
		AetherMoaTypes.moaRegistry = event.getRegistry();

		AetherMoaTypes.initialization();
	}

	@SubscribeEvent
	public void onRegisterAccessoryEvent(RegistryEvent.Register<AetherAccessory> event)
	{
		AetherRegistries.initializeAccessories(event.getRegistry());
	}


	@SubscribeEvent
	public void onRegisterEnchantmentEvent(RegistryEvent.Register<AetherEnchantment> event)
	{
		AetherRegistries.initializeEnchantments(event.getRegistry());
	}

	@SubscribeEvent
	public void onRegisterEnchantmentFuelEvent(RegistryEvent.Register<AetherEnchantmentFuel> event)
	{
		AetherRegistries.initializeEnchantmentFuel(event.getRegistry());
	}

	@SubscribeEvent
	public void onRegisterFreezableEvent(RegistryEvent.Register<AetherFreezable> event)
	{
		AetherRegistries.initializeFreezables(event.getRegistry());
	}

	@SubscribeEvent
	public void onRegisterFreezableFuelEvent(RegistryEvent.Register<AetherFreezableFuel> event)
	{
		AetherRegistries.initializeFreezableFuel(event.getRegistry());
	}

}