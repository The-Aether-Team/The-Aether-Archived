package com.legacy.aether.common.player.perks;

import io.netty.buffer.ByteBuf;

import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.legacy.aether.common.player.perks.util.DonatorMoaSkin;
import com.legacy.aether.common.player.perks.util.EnumAetherPerkType;

public class AetherPerks
{

	private EnumAetherPerkType perkType;

	private boolean shouldRenderHalo;

	private DonatorMoaSkin customMoaSkin;

	public AetherPerks(EnumAetherPerkType type)
	{
		this.perkType = type;
	}

	public AetherPerks(EnumAetherPerkType type, boolean renderHalo)
	{
		this(type);

		this.shouldRenderHalo = renderHalo;
	}

	public AetherPerks(EnumAetherPerkType type, DonatorMoaSkin moaSkin)
	{
		this(type);

		this.customMoaSkin = moaSkin;
	}

	public void readPerks(ByteBuf buf)
	{
		this.perkType = EnumAetherPerkType.getPerkByID(buf.readInt());

		if (this.perkType == EnumAetherPerkType.Halo)
		{
			this.shouldRenderHalo = buf.readBoolean();
		}
		else if (this.perkType == EnumAetherPerkType.Moa)
		{
			this.customMoaSkin = DonatorMoaSkin.readMoaSkin(buf);
		}
	}

	public void writePerks(ByteBuf buf)
	{
		buf.writeInt(this.perkType.getPerkID());

		if (this.perkType == EnumAetherPerkType.Halo)
		{
			buf.writeBoolean(this.shouldRenderHalo);
		}
		else if (this.perkType == EnumAetherPerkType.Moa)
		{
			this.customMoaSkin.writeMoaSkin(buf);
		}
	}

	public static boolean isDonator(UUID uuid)
	{
		try 
		{
			String urlReading = IOUtils.toString(new URL("http://www.gilded-games.com/aether/signatureUUID.php?name=" + uuid.toString().replace("-", "")));

			return urlReading.contains("true") ? true : false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return false;
	}

}