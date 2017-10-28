package com.legacy.aether.common.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.api.moa.AetherMoaType;
import com.legacy.aether.common.events.accessories.ValidAccessoryEvent;
import com.legacy.aether.common.events.enchantments.AetherEnchantmentEvent;
import com.legacy.aether.common.events.freezables.AetherFreezableEvent;
import com.legacy.aether.common.events.moas.MoaHatchEvent;
import com.legacy.aether.common.tile_entities.TileEntityEnchanter;
import com.legacy.aether.common.tile_entities.TileEntityFreezer;
import com.legacy.aether.common.tile_entities.TileEntityIncubator;

public class AetherHooks
{

	public static boolean isValidAccessory(EntityPlayer player, AetherAccessory accessory)
	{
		ValidAccessoryEvent event = new ValidAccessoryEvent(player, accessory);

        if (MinecraftForge.EVENT_BUS.post(event)) return false;

		return !event.isCanceled();
	}

	public static void onMoaHatched(AetherMoaType type, TileEntityIncubator incubator)
	{
		MoaHatchEvent event = new MoaHatchEvent(type, incubator);

		MinecraftForge.EVENT_BUS.post(event);
	}

	public static void onItemEnchant(TileEntityEnchanter enchanter, AetherEnchantment enchantment)
	{
		AetherEnchantmentEvent.EnchantEvent event = new AetherEnchantmentEvent.EnchantEvent(enchanter, enchantment);

		MinecraftForge.EVENT_BUS.post(event);
	}

	public static void onItemFreeze(TileEntityFreezer freezer, AetherFreezable freezable)
	{
		AetherFreezableEvent.FreezeEvent event = new AetherFreezableEvent.FreezeEvent(freezer, freezable);

		MinecraftForge.EVENT_BUS.post(event);
	}

	public static int onSetEnchantmentTime(TileEntityEnchanter enchanter, AetherEnchantment enchantment, int original)
	{
		AetherEnchantmentEvent.SetTimeEvent event = new AetherEnchantmentEvent.SetTimeEvent(enchanter, enchantment, original);

		if (MinecraftForge.EVENT_BUS.post(event)) return original;

		return event.getNewTime();
	}

	public static int onSetFreezableTime(TileEntityFreezer freezer, AetherFreezable freezable, int original)
	{
		AetherFreezableEvent.SetTimeEvent event = new AetherFreezableEvent.SetTimeEvent(freezer, freezable, original);

		if (MinecraftForge.EVENT_BUS.post(event)) return original;

		return event.getNewTime();
	}
}