package com.legacy.aether;

import com.legacy.aether.advancements.AetherAdvancements;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.AetherEntities;
import com.legacy.aether.events.AetherEntityEvents;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.player.capability.PlayerAetherManager;
import com.legacy.aether.registry.AetherRegistryEvent;
import com.legacy.aether.registry.sounds.SoundsAether;
import com.legacy.aether.tile_entities.AetherTileEntities;
import com.legacy.aether.universal.crafttweaker.AetherCraftTweakerPlugin;
import com.legacy.aether.world.AetherWorld;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = "Aether Legacy", modid = Aether.modid, version = Aether.version, acceptedMinecraftVersions = "1.12.2")
public class Aether 
{

	public static final String modid = "aether_legacy";

	public static final String version = "1.12.2-v1.4.0";

	@Instance(Aether.modid)
	public static Aether instance;

	@SidedProxy(modId = Aether.modid, clientSide = "com.legacy.aether.client.ClientProxy", serverSide = "com.legacy.aether.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInitialization(FMLPreInitializationEvent event)
	{
		BlocksAether.initialization();
		SoundsAether.initialization();
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
		AetherWorld.initialization();

		CommonProxy.registerEvent(new AetherEventHandler());
		CommonProxy.registerEvent(new AetherEntityEvents());

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