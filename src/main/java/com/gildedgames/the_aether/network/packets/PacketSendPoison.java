package com.gildedgames.the_aether.network.packets;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSendPoison extends AetherPacket<PacketSendPoison> {

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
		if (player != null && player.worldObj != null)
		{
			EntityPlayer parent = (EntityPlayer) player.worldObj.getEntityByID(message.entityID);

			if (parent != null)
			{
				((PlayerAether) AetherAPI.get(parent)).setPoisoned();
			}
		}
	}

	@Override
	public void handleServer(PacketSendPoison message, EntityPlayer player)
	{

	}

}