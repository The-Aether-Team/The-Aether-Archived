package com.legacy.aether.api.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

import com.legacy.aether.api.accessories.AetherAccessory;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.events.accessories.ValidAccessoryEvent;
import com.legacy.aether.api.events.enchantments.AetherEnchantmentEvent;
import com.legacy.aether.api.events.freezables.AetherFreezableEvent;
import com.legacy.aether.api.events.moas.MoaHatchEvent;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.api.moa.AetherMoaType;

public class AetherHooks
{

	public static boolean isValidAccessory(EntityPlayer player, AetherAccessory accessory)
	{
		ValidAccessoryEvent event = new ValidAccessoryEvent(player, accessory);

        if (MinecraftForge.EVENT_BUS.post(event)) return false;

		return !event.isCanceled();
	}

	public static void onMoaHatched(AetherMoaType type, TileEntity incubator)
	{
		MoaHatchEvent event = new MoaHatchEvent(type, incubator);

		MinecraftForge.EVENT_BUS.post(event);
	}

	public static void onItemEnchant(TileEntity enchanter, AetherEnchantment enchantment)
	{
		AetherEnchantmentEvent.EnchantEvent event = new AetherEnchantmentEvent.EnchantEvent(enchanter, enchantment);

		MinecraftForge.EVENT_BUS.post(event);
	}

	public static void onItemFreeze(TileEntity freezer, AetherFreezable freezable)
	{
		AetherFreezableEvent.FreezeEvent event = new AetherFreezableEvent.FreezeEvent(freezer, freezable);

		MinecraftForge.EVENT_BUS.post(event);
	}

	public static int onSetEnchantmentTime(TileEntity enchanter, AetherEnchantment enchantment, int original)
	{
		AetherEnchantmentEvent.SetTimeEvent event = new AetherEnchantmentEvent.SetTimeEvent(enchanter, enchantment, original);

		if (MinecraftForge.EVENT_BUS.post(event)) return original;

		return event.getNewTime();
	}

	public static int onSetFreezableTime(TileEntity freezer, AetherFreezable freezable, int original)
	{
		AetherFreezableEvent.SetTimeEvent event = new AetherFreezableEvent.SetTimeEvent(freezer, freezable, original);

		if (MinecraftForge.EVENT_BUS.post(event)) return original;

		return event.getNewTime();
	}
}