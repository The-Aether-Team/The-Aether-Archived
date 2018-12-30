package com.legacy.aether;

import net.minecraft.util.ResourceLocation;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.EntitiesAether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.network.AetherNetwork;
import com.legacy.aether.player.PlayerAetherEvents;
import com.legacy.aether.player.perks.AetherRankings;
import com.legacy.aether.registry.AetherRegistries;
import com.legacy.aether.registry.achievements.AchievementsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.tileentity.AetherTileEntities;
import com.legacy.aether.world.AetherWorld;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Aether.MOD_ID, version = "v1.0.1")
public class Aether {

	public static final String MOD_ID = "aether_legacy";

	@Instance(Aether.MOD_ID)
	public static Aether instance;

	@SidedProxy(clientSide = "com.legacy.aether.client.ClientProxy", serverSide = "com.legacy.aether.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		AetherRankings.initialization();
		AetherNetwork.preInitialization();
		AetherConfig.init(event.getModConfigurationDirectory());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		BlocksAether.initialization();
		ItemsAether.initialization();
		AetherRegistries.register();
		EntitiesAether.initialization();
		AetherCreativeTabs.initialization();
		AetherTileEntities.initialization();
		AetherWorld.initialization();
		AchievementsAether.initialization();

		proxy.init();

		CommonProxy.registerEvent(new PlayerAetherEvents());
		CommonProxy.registerEvent(new AetherEventHandler());
	}

	public static ResourceLocation locate(String location) {
		return new ResourceLocation(MOD_ID, location);
	}

	public static String find(String location) {
		return modAddress() + location;
	}

	public static String modAddress() {
		return MOD_ID + ":";
	}
}