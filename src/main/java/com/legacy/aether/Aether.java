package com.legacy.aether;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.AetherEntities;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.player.capability.PlayerAetherManager;
import com.legacy.aether.registry.AetherRegistries;
import com.legacy.aether.registry.achievements.AchievementsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.registry.sounds.SoundsAether;
import com.legacy.aether.tile_entities.AetherTileEntities;
import com.legacy.aether.world.AetherWorld;

@Mod(name = "Aether Legacy", modid = Aether.modid, version = Aether.version, acceptedMinecraftVersions = "1.11.2")
public class Aether 
{

	public static final String modid = "aether_legacy";

	//If version number has "dev" in it, developer mode will automatically be enabled.
	//This saves time and makes things much easier.
	public static final String version = "v1.0.1-dev";

	@Instance(Aether.modid)
	public static Aether instance;

	@SidedProxy(modId = Aether.modid, clientSide = "com.legacy.aether.client.ClientProxy", serverSide = "com.legacy.aether.ServerProxy")
	public static ServerProxy proxy;

	@EventHandler
	public void preInitialization(FMLPreInitializationEvent event)
	{
		AetherConfig.init(event.getModConfigurationDirectory());
		AetherConfig.autoDeveloperMode(version);

		AetherNetworkingManager.preInitialization();

		proxy.preInitialization();
	}

	@EventHandler
	public void initialization(FMLInitializationEvent event)
	{
		PlayerAetherManager.initialization();
		SoundsAether.initialization();
		AetherEntities.initialization();
		BlocksAether.initialization();
		ItemsAether.initialization();
		AetherRegistries.initialization();
		AchievementsAether.initialization();
		AetherTileEntities.initialization();
		AetherCreativeTabs.initialization();
		AetherWorld.initialization();

		proxy.initialization();

		ServerProxy.registerEvent(new AetherEventHandler());
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