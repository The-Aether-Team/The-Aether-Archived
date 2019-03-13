package com.legacy.aether.api;

import com.legacy.aether.api.player.IPlayerAether;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class AetherAPI
{

	@CapabilityInject(IPlayerAether.class)
	public static Capability<IPlayerAether> AETHER_PLAYER = null;

	private static final AetherAPI instance = new AetherAPI();

	public AetherAPI()
	{
	}

	public IPlayerAether get(EntityPlayer player)
	{
		return player.getCapability(AETHER_PLAYER).orElse(null);
	}

	public static AetherAPI getInstance()
	{
		return instance;
	}

}