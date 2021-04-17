package com.gildedgames.the_aether;

import com.gildedgames.the_aether.events.AetherEntityEvents;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.entities.EntitiesAether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.player.PlayerAetherEvents;
import com.gildedgames.the_aether.player.perks.AetherRankings;
import com.gildedgames.the_aether.registry.AetherRegistries;
import com.gildedgames.the_aether.registry.achievements.AchievementsAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import com.gildedgames.the_aether.tileentity.AetherTileEntities;
import com.gildedgames.the_aether.world.AetherWorld;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Aether.MOD_ID, version = "v1.1.2.2")
public class Aether {

	public static final String MOD_ID = "aether_legacy";

	@Instance(Aether.MOD_ID)
	public static Aether instance;

	@SidedProxy(clientSide = "com.gildedgames.the_aether.client.ClientProxy", serverSide = "com.gildedgames.the_aether.CommonProxy")
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
		BlocksAether.initializeHarvestLevels();
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
		CommonProxy.registerEvent(new AetherEntityEvents());
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