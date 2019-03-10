package com.legacy.aether;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.api.player.IPlayerAetherStorage;
import com.legacy.aether.client.ClientAether;
import com.legacy.aether.network.AetherNetwork;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.player.PlayerAetherEvents;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("aether_legacy")
public class Aether
{

	private static final Logger LOGGER = LogManager.getLogger();

	public Aether()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::initialization);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientAether::initialization);
		MinecraftForge.EVENT_BUS.register(new PlayerAetherEvents());
	}

	public void initialization(FMLCommonSetupEvent event)
	{
		AetherNetwork.initialization();
		CapabilityManager.INSTANCE.register(IPlayerAether.class, new IPlayerAetherStorage(), PlayerAether::new);
	}

	public static void println(Object obj)
	{
		LOGGER.info(obj.toString());
	}

	public static ResourceLocation locate(String name)
	{
		return new ResourceLocation("aether_legacy", name);
	}

}