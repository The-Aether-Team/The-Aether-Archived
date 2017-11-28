package com.legacy.aether.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.GameType;

public class PacketGameType extends AetherPacket<PacketGameType>
{

	public GameType gameType;

	public PacketGameType()
	{
		
	}

	public PacketGameType(GameType gameType)
	{
		this.gameType = gameType;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.gameType = GameType.getByID(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(this.gameType.getID());
	}

	@Override
	public void handleClient(PacketGameType message, EntityPlayer player)
	{

	}

	@Override
	public void handleServer(PacketGameType message, EntityPlayer player)
	{
		try
		{
			player.setGameType(message.gameType);
		}
		catch(Exception e)
		{
			
		}
	}

}
