package com.legacy.aether.world;

import net.minecraft.network.PacketBuffer;

public class AetherDaylightManager
{

	private boolean sunSpiritDefeated;

	public void write(PacketBuffer buffer)
	{
		buffer.writeBoolean(this.sunSpiritDefeated);
	}

	public void read(PacketBuffer buffer)
	{
		this.sunSpiritDefeated = buffer.readBoolean();
	}

	public void setSunSpiritDefeated(boolean defeated)
	{
		this.sunSpiritDefeated = defeated;
	}

	public boolean isSunSpiritDefeated()
	{
		return this.sunSpiritDefeated;
	}

}