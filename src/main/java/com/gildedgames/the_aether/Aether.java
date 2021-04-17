package com.gildedgames.the_aether;

import com.gildedgames.the_aether.advancements.AetherAdvancements;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.IPlayerAetherStorage;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.entities.AetherEntities;
import com.gildedgames.the_aether.events.AetherEntityEvents;
import com.gildedgames.the_aether.networking.AetherNetworkingManager;
import com.gildedgames.the_aether.player.capability.PlayerAetherManager;
import com.gildedgames.the_aether.registry.AetherRegistryEvent;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import com.gildedgames.the_aether.tile_entities.AetherTileEntities;
import com.gildedgames.the_aether.universal.crafttweaker.AetherCraftTweakerPlugin;
import com.gildedgames.the_aether.universal.reskillable.ReskillableTickHandler;
import com.gildedgames.the_aether.world.AetherWorld;
import com.gildedgames.the_aether.world.biome.BiomeStorage;
import com.gildedgames.the_aether.world.storage.loot.conditions.LootConditionsAether;
import com.gildedgames.the_aether.world.storage.loot.functions.LootFunctionsAether;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = "Aether", modid = Aether.modid, version = Aether.version, acceptedMinecraftVersions = "1.12.2", updateJSON = "https://raw.githubusercontent.com/Modding-Legacy/Aether-Legacy/master/aether-legacy-changelog.json")
public class Aether 
{

	public static final String modid = "aether_legacy";

	public static final String version = "1.5.3.2";

	@Instance(Aether.modid)
	public static Aether instance;

	@SidedProxy(modId = Aether.modid, clientSide = "com.gildedgames.the_aether.client.ClientProxy", serverSide = "com.gildedgames.the_aether.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInitialization(FMLPreInitializationEvent event)
	{
		CapabilityManager.INSTANCE.register(IPlayerAether.class, new IPlayerAetherStorage(), () -> null);

		BlocksAether.initialization();
		BlocksAether.initializeHarvestLevels();
		SoundsAether.initialization();
		LootConditionsAether.initialization();
		LootFunctionsAether.initialization();
		AetherAdvancements.initialization();
		AetherNetworkingManager.preInitialization();

		if(Loader.isModLoaded("crafttweaker"))
		{
			AetherCraftTweakerPlugin.preInitialization();
		}

		CommonProxy.registerEvent(new AetherRegistryEvent());

		proxy.preInitialization();
	}

	@EventHandler
	public void initialization(FMLInitializationEvent event)
	{
		PlayerAetherManager.initialization();
		AetherEntities.initialization();
		AetherTileEntities.initialization();
		BiomeStorage.handleBiomeConfig();
		AetherWorld.initialization();

		CommonProxy.registerEvent(new AetherEventHandler());
		CommonProxy.registerEvent(new AetherEntityEvents());

		if(Loader.isModLoaded("reskillable"))
		{
			CommonProxy.registerEvent(new ReskillableTickHandler());
		}

		proxy.initialization();
	}

	@EventHandler
	public void postInitialization(FMLPostInitializationEvent event)
	{
		proxy.postInitialization();

		FurnaceRecipes.instance().addSmeltingRecipeForBlock(BlocksAether.aether_log, new ItemStack(Items.COAL, 1, 1), 0.15F);
	}

	public static ResourceLocation locate(String location)
	{
		return new ResourceLocation(modid, location);
	}

	public static String modAddress()
	{
		return modid + ":";
	}

	public static String doubleDropNotifier()
	{
		return modid + "_double_drops";
	}

}