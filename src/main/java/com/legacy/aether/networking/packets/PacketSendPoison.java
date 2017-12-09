package com.legacy.aether.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.legacy.aether.player.PlayerAether;

public class PacketSendPoison extends AetherPacket<PacketSendPoison>
{

	private int entityID;

	public PacketSendPoison()
	{
		
	}

	public PacketSendPoison(EntityPlayer thePlayer)
	{
		this.entityID = thePlayer.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.entityID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(this.entityID);
	}

	@Override
	public void handleClient(PacketSendPoison message, EntityPlayer player) 
	{
		if (player != null && player.world != null)
		{
			EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

			if (parent != null)
			{
				PlayerAether.get(parent).afflictPoison();
			}
		}
	}

	@Override
	public void handleServer(PacketSendPoison message, EntityPlayer player)
	{

	}

}