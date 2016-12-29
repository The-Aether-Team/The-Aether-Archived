package com.legacy.aether.server.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.legacy.aether.server.networking.AetherNetworkingManager;
import com.legacy.aether.server.player.PlayerAether;
import com.legacy.aether.server.player.perks.util.DonatorMoaSkin;
import com.legacy.aether.server.player.perks.util.EnumAetherPerkType;

public class PacketPerkChanged extends AetherPacket<PacketPerkChanged>
{

	public int entityID;

	public boolean renderHalo, isDonator;

	public DonatorMoaSkin moaSkin;

	public EnumAetherPerkType perkType;

	public PacketPerkChanged()
	{

	}

	public PacketPerkChanged(int entityID, EnumAetherPerkType type, boolean info)
	{
		this.entityID = entityID;
		this.perkType = type;

		if (type == EnumAetherPerkType.Halo) 
		{ 
			this.renderHalo = info;
		}
		else
		{
			this.isDonator = info;
		}
	}

	public PacketPerkChanged(int entityID, EnumAetherPerkType type, DonatorMoaSkin moa)
	{
		this.entityID = entityID;
		this.moaSkin = moa;
		this.perkType = type;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.perkType = EnumAetherPerkType.getPerkByID(buf.readInt());
		this.entityID = buf.readInt();

		if (this.perkType == EnumAetherPerkType.Halo)
		{
			this.renderHalo = buf.readBoolean();
		}
		else if (this.perkType == EnumAetherPerkType.Information)
		{
			this.isDonator = buf.readBoolean();
		}
		else if (this.perkType == EnumAetherPerkType.Moa)
		{
			this.moaSkin = DonatorMoaSkin.readMoaSkin(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.perkType.getPerkID());
		buf.writeInt(this.entityID);

		if (this.perkType == EnumAetherPerkType.Halo)
		{
			buf.writeBoolean(this.renderHalo);
		}
		else if (this.perkType == EnumAetherPerkType.Information)
		{
			buf.writeBoolean(this.isDonator);
		}
		else if (this.perkType == EnumAetherPerkType.Moa)
		{
			this.moaSkin.writeMoaSkin(buf);
		}
	}

	@Override
	public void handleClient(PacketPerkChanged message, EntityPlayer player)
	{
		if (player != null && player.worldObj != null)
		{
			EntityPlayer parent = (EntityPlayer) player.worldObj.getEntityByID(message.entityID);

			if (parent != null)
			{
				PlayerAether instance = PlayerAether.get(parent);

				if (message.perkType == EnumAetherPerkType.Halo)
				{
					instance.shouldRenderHalo = message.renderHalo;
				}
				else if (message.perkType == EnumAetherPerkType.Information)
				{
					instance.setDonator(message.isDonator);
				}
				else if (message.perkType == EnumAetherPerkType.Moa)
				{
					instance.donatorMoaSkin = message.moaSkin;
				}
			}
		}
	}

	@Override
	public void handleServer(PacketPerkChanged message, EntityPlayer player) 
	{
		if (player != null && player.worldObj != null && !player.worldObj.isRemote)
		{
			AetherNetworkingManager.sendToAll(message);
		}
	}

}