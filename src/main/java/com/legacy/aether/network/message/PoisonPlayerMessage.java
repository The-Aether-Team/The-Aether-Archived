package com.legacy.aether.network.message;

import java.util.function.Supplier;

import com.legacy.aether.api.AetherAPI;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PoisonPlayerMessage
{

	private int poisonTicks;

	public PoisonPlayerMessage()
	{

	}

	public PoisonPlayerMessage(int ticks)
	{
		this.poisonTicks = ticks;
	}

	public static void encode(PoisonPlayerMessage message, PacketBuffer buffer)
	{
		buffer.writeInt(message.poisonTicks);
	}

	public static PoisonPlayerMessage decode(PacketBuffer buffer)
	{
		PoisonPlayerMessage message = new PoisonPlayerMessage();

		message.poisonTicks = buffer.readInt();

		return message;
	}

	public static void onPacketRecieved(PoisonPlayerMessage message, Supplier<Context> contextSupplier)
	{
		AetherAPI.getInstance().get(net.minecraft.client.Minecraft.getInstance().player).inflictPoison(message.poisonTicks);
	}

}