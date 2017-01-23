package com.legacy.aether.server;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.entities.AetherEntities;
import com.legacy.aether.server.items.ItemsAether;
import com.legacy.aether.server.networking.AetherNetworkingManager;
import com.legacy.aether.server.player.capability.PlayerAetherManager;
import com.legacy.aether.server.registry.achievements.AchievementsAether;
import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.server.registry.recipes.AetherRecipes;
import com.legacy.aether.server.registry.sounds.SoundsAether;
import com.legacy.aether.server.tile_entities.AetherTileEntities;
import com.legacy.aether.server.world.AetherWorld;

@Mod(name = "Aether Legacy", modid = Aether.modid, version = "v1.3.1-1.10.2", acceptedMinecraftVersions = "1.10.2")
public class Aether 
{

	public static final String modid = "aether_legacy";

	@Instance(Aether.modid)
	public static Aether instance;

	@SidedProxy(modId = Aether.modid, clientSide = "com.legacy.aether.client.ClientProxy", serverSide = "com.legacy.aether.server.ServerProxy")
	public static ServerProxy proxy;

	@EventHandler
	public void preInitialization(FMLPreInitializationEvent event)
	{
		AetherConfig.init(event.getModConfigurationDirectory());

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
		AetherRecipes.initialization();
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