package com.legacy.aether;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.legacy.aether.advancements.AetherAdvancements;
import com.legacy.aether.entities.AetherEntities;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.player.capability.PlayerAetherManager;
import com.legacy.aether.registry.AetherRegistries;
import com.legacy.aether.registry.AetherRegistryEvent;
import com.legacy.aether.registry.sounds.SoundsAether;
import com.legacy.aether.tile_entities.AetherTileEntities;
import com.legacy.aether.world.AetherWorld;

@Mod(name = "Aether Legacy", modid = Aether.modid, version = Aether.version, acceptedMinecraftVersions = "1.12.2")
public class Aether 
{

	public static final String modid = "aether_legacy";

	public static final String version = "v1.0";

	@Instance(Aether.modid)
	public static Aether instance;

	@SidedProxy(modId = Aether.modid, clientSide = "com.legacy.aether.client.ClientProxy", serverSide = "com.legacy.aether.ServerProxy")
	public static ServerProxy proxy;

	@EventHandler
	public void preInitialization(FMLPreInitializationEvent event)
	{
		ServerProxy.registerEvent(new AetherRegistryEvent());

		AetherConfig.init(event.getModConfigurationDirectory());
		AetherConfig.autoDeveloperMode(version);

		SoundsAether.initialization();
		AetherAdvancements.initialization();
		AetherNetworkingManager.preInitialization();

		proxy.preInitialization();
	}

	@EventHandler
	public void initialization(FMLInitializationEvent event)
	{
		PlayerAetherManager.initialization();
		AetherEntities.initialization();
		AetherRegistries.initialization();
		AetherTileEntities.initialization();
		AetherWorld.initialization();

		ServerProxy.registerEvent(new AetherEventHandler());

		proxy.initialization();
	}

	@EventHandler
	public void postInitialization(FMLPostInitializationEvent event)
	{
		proxy.postInitialization();
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