package com.legacy.aether.network.message;

import java.util.function.Supplier;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.player.IPlayerAether;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class UpdatePlayerMessage
{

	private NBTTagCompound compound;

	public UpdatePlayerMessage()
	{

	}

	public UpdatePlayerMessage(IPlayerAether playerAether)
	{
		this.compound = new NBTTagCompound();

		playerAether.writeAdditional(this.compound);
	}

	public static void encode(UpdatePlayerMessage message, PacketBuffer buffer)
	{
		buffer.writeCompoundTag(message.compound);
	}

	public static UpdatePlayerMessage decode(PacketBuffer buffer)
	{
		UpdatePlayerMessage message = new UpdatePlayerMessage();

		message.compound = buffer.readCompoundTag();

		return message;
	}

	public static void onPacketRecieved(UpdatePlayerMessage message, Supplier<Context> contextSupplier)
	{
		AetherAPI.getInstance().get(net.minecraft.client.Minecraft.getInstance().player).readAdditional(message.compound);
	}

}