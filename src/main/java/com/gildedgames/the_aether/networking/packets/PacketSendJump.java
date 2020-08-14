package com.gildedgames.the_aether.networking.packets;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.gildedgames.the_aether.api.AetherAPI;

public class PacketSendJump extends AetherPacket<PacketSendJump>
{

	private UUID uuid;

	private boolean isJumping;

	public PacketSendJump()
	{

	}

	public PacketSendJump(UUID uuid, boolean isJumping)
	{
		this.uuid = uuid;
		this.isJumping = isJumping;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.uuid = new UUID(buf.readLong(), buf.readLong());
		this.isJumping = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeLong(this.uuid.getMostSignificantBits());
		buf.writeLong(this.uuid.getLeastSignificantBits());
		buf.writeBoolean(this.isJumping);
	}

	@Override
	public void handleClient(PacketSendJump message, EntityPlayer player)
	{

	}

	@Override
	public void handleServer(PacketSendJump message, EntityPlayer player)
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		if (server != null)
		{
			EntityPlayer user = server.getPlayerList().getPlayerByUUID(message.uuid);

			if (user != null)
			{
				AetherAPI.getInstance().get(user).setJumping(message.isJumping);
			}
		}
	}

}