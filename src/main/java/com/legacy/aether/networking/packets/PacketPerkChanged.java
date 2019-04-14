package com.legacy.aether.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.player.perks.util.DonatorMoaSkin;
import com.legacy.aether.player.perks.util.EnumAetherPerkType;

public class PacketPerkChanged extends AetherPacket<PacketPerkChanged>
{

	public int entityID;

	public boolean renderHalo, renderGlow;

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
		
		if (type == EnumAetherPerkType.Glow) 
		{ 
			this.renderGlow = info;
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
		else if (this.perkType == EnumAetherPerkType.Moa)
		{
			this.moaSkin = DonatorMoaSkin.readMoaSkin(buf);
		}
		else if (this.perkType == EnumAetherPerkType.Glow)
		{
			this.renderGlow = buf.readBoolean();
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
		else if (this.perkType == EnumAetherPerkType.Moa)
		{
			this.moaSkin.writeMoaSkin(buf);
		}
		else if (this.perkType == EnumAetherPerkType.Glow)
		{
			buf.writeBoolean(this.renderGlow);
		}
	}

	@Override
	public void handleClient(PacketPerkChanged message, EntityPlayer player)
	{
		if (player != null && player.world != null)
		{
			EntityPlayer parent = (EntityPlayer) player.world.getEntityByID(message.entityID);

			if (parent != null)
			{
				IPlayerAether instance = AetherAPI.getInstance().get(parent);

				if (message.perkType == EnumAetherPerkType.Halo)
				{
					((PlayerAether) instance).shouldRenderHalo = message.renderHalo;
				}
				else if (message.perkType == EnumAetherPerkType.Moa)
				{
					((PlayerAether) instance).donatorMoaSkin = message.moaSkin;
				}
				else if (message.perkType == EnumAetherPerkType.Glow)
				{
					((PlayerAether) instance).shouldRenderGlow = message.renderGlow;
				}
			}
		}
	}

	@Override
	public void handleServer(PacketPerkChanged message, EntityPlayer player) 
	{
		if (player != null && player.world != null && !player.world.isRemote)
		{
			AetherNetworkingManager.sendToAll(message);
		}
	}

}