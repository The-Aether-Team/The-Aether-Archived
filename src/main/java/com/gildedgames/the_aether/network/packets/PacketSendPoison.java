package com.gildedgames.the_aether.network.packets;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.player.PlayerAether;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
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
			Entity entity = player.worldObj.getEntityByID(message.entityID);

			if (entity instanceof EntityPlayer)
			{
				EntityPlayer parent = (EntityPlayer) entity;

				IPlayerAether iPlayerAether = AetherAPI.get(parent);

				if (iPlayerAether != null)
				{
					PlayerAether playerAether = (PlayerAether) iPlayerAether;

					playerAether.setPoisoned();
				}
			}
		}
	}

	@Override
	public void handleServer(PacketSendPoison message, EntityPlayer player)
	{

	}

}