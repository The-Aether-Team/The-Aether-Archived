package com.gildedgames.the_aether.network.packets;

import com.gildedgames.the_aether.Aether;
import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public abstract class AetherPacket<Packet extends IMessage> implements IMessage, IMessageHandler<Packet, Packet> {

	@Override
	public Packet onMessage(Packet message, MessageContext context) {
		if (context.side == Side.CLIENT) {
			this.handleClient(message, Aether.proxy.getPlayer());
		} else if (context.side == Side.SERVER) {
			this.handleServer(message, context.getServerHandler().playerEntity);
		}

		return null;
	}

	public abstract void handleClient(Packet message, EntityPlayer player);

	public abstract void handleServer(Packet message, EntityPlayer player);

}