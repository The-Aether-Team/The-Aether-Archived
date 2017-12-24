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

@Mod(name = "Aether Legacy", modid = Aether.modid, version = "v1.5", acceptedMinecraftVersions = "1.10.2")
public class Aether 
{

	public static final String modid = "aether_legacy";

	@Instance(Aether.modid)
	public static Aether instance;

	@SidedProxy(modId = Aether.modid, clientSide = "com.legacy.aether.client.ClientProxy", serverSide = "com.legacy.aether.CommonProxy")
	public static CommonProxy proxy;

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
		AetherRegistries.initialization();
		AchievementsAether.initialization();
		AetherTileEntities.initialization();
		AetherCreativeTabs.initialization();
		AetherWorld.initialization();

		proxy.initialization();

		CommonProxy.registerEvent(new AetherEventHandler());
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
		return "double_drops";
	}

}