package com.legacy.aether.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.player.util.IAccessoryInventory;
import com.legacy.aether.containers.inventory.InventoryAccessories;
import com.legacy.aether.player.PlayerAether;

public class PacketAccessory extends AetherPacket<PacketAccessory>
{

	private IAccessoryInventory accessories;

	private int entityID;

	public PacketAccessory()
	{
		
	}

	public PacketAccessory(PlayerAether thePlayer)
	{
		this.accessories = thePlayer.accessories;
		this.entityID = thePlayer.thePlayer.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		this.entityID = buf.readInt();

		this.accessories = new InventoryAccessories(null);
		this.accessories.readData(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(this.entityID);

		this.accessories.writeData(buf);
	}

	@Override
	public void handleClient(PacketAccessory message, EntityPlayer player) 
	{
		if (player != null && player.world != null)
		{
			EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

			if (parent != null)
			{
				((InventoryAccessories) message.accessories).player = parent;

				AetherAPI.getInstance().get(parent).setAccessoryInventory(message.accessories);
			}
		}
	}

	@Override
	public void handleServer(PacketAccessory message, EntityPlayer player)
	{

	}

}
