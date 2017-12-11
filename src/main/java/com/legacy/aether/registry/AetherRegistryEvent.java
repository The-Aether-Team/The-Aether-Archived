package com.legacy.aether.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.legacy.aether.Aether;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.registry.sounds.SoundsAether;
import com.legacy.aether.world.AetherWorld;

public class AetherRegistryEvent 
{

	@SubscribeEvent
	public void onRegisterBlockEvent(RegistryEvent.Register<Block> event)
	{
		BlocksAether.blockRegistry = event.getRegistry();
		
		if (BlocksAether.canInitialize())
		{
			BlocksAether.initialization();
		}
	}

	@SubscribeEvent
	public void onRegisterItemEvent(RegistryEvent.Register<Item> event)
	{
		BlocksAether.itemRegistry = event.getRegistry();
		ItemsAether.itemRegistry = event.getRegistry();

		if (BlocksAether.canInitialize())
		{
			BlocksAether.initialization();
		}

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

		AetherRegistries.registerRecipes();
	}

}