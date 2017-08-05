package com.legacy.aether.common.player.perks;

import net.minecraft.entity.player.EntityPlayerMP;

import com.legacy.aether.common.networking.AetherNetworkingManager;
import com.legacy.aether.common.networking.packets.PacketPerkChanged;
import com.legacy.aether.common.player.PlayerAether;
import com.legacy.aether.common.player.perks.util.EnumAetherPerkType;

public class AetherPerkThread implements Runnable
{

	private PlayerAether playerAether;

	public AetherPerkThread(PlayerAether playerAether) 
	{
		this.playerAether = playerAether;
	}

	@Override
	public void run()
	{
		boolean isDonator = AetherPerks.isDonator(this.playerAether.thePlayer.getUniqueID());

		this.playerAether.setDonator(isDonator);

		AetherNetworkingManager.sendTo(new PacketPerkChanged(this.playerAether.thePlayer.getEntityId(), EnumAetherPerkType.Information, isDonator), (EntityPlayerMP) this.playerAether.thePlayer);
	}

}