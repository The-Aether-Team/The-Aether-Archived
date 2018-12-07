package com.legacy.aether.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.legacy.aether.player.PlayerAether;

public class PacketSendPoison extends AetherPacket<PacketSendPoison> {

	public PacketSendPoison() {

	}

	public PacketSendPoison(EntityPlayer thePlayer) {

	}

	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}

	@Override
	public void handleClient(PacketSendPoison message, EntityPlayer player) {
		if (player != null && player.worldObj != null) {
			PlayerAether.get(player).inflictPoison(500);
		}
	}

	@Override
	public void handleServer(PacketSendPoison message, EntityPlayer player) {

	}

}