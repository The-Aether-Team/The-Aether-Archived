package com.legacy.aether.network;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.legacy.aether.Aether;
import com.legacy.aether.network.message.PoisonPlayerMessage;
import com.legacy.aether.network.message.UpdatePlayerMessage;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class AetherNetwork
{

	private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(Aether.locate("aether_network"), () -> { return "0.0.1"; }, (version) -> { return version.equals("0.0.1"); }, (version) -> { return version.equals("0.0.1"); });

	private static int discriminator;

	public static void initialization()
	{
		register(UpdatePlayerMessage.class, UpdatePlayerMessage::encode, UpdatePlayerMessage::decode, UpdatePlayerMessage::onPacketRecieved);
		register(PoisonPlayerMessage.class, PoisonPlayerMessage::encode, PoisonPlayerMessage::decode, PoisonPlayerMessage::onPacketRecieved);
	}

	private static <MSG> void register(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<Context>> messageConsumer)
	{
		CHANNEL.registerMessage(discriminator++, messageType, encoder, decoder, messageConsumer);
	}

	@OnlyIn(Dist.CLIENT)
	public static <MSG> void sendToServer(MSG message)
	{
		CHANNEL.sendToServer(message);
	}

	public static <MSG> void sendToClient(MSG message, EntityPlayerMP player)
	{
		CHANNEL.sendTo(message, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
	}

}