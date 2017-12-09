package com.legacy.aether.networking.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import com.legacy.aether.Aether;

public abstract class AetherPacket<Packet extends IMessage> implements IMessage, IMessageHandler<Packet, Packet>
{

	@Override
	public Packet onMessage(Packet message, MessageContext context)
	{
		if (context.side == Side.CLIENT)
		{
			this.handleClient(message, Aether.proxy.getThePlayer());
		}
		else if (context.side == Side.SERVER)
		{
			this.handleServer(message, context.getServerHandler().playerEntity);
		}

		return null;
	}

	public abstract void handleClient(Packet message, EntityPlayer player);

	public abstract void handleServer(Packet message, EntityPlayer player);

}