package com.legacy.aether.server.player.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import com.legacy.aether.server.player.PlayerAether;

public class PlayerAetherProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound>
{

	private final PlayerAether playerAether;

	public PlayerAetherProvider(PlayerAether playerAether)
	{
		this.playerAether = playerAether;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == PlayerAetherManager.AETHER_PLAYER;
	}

	@Override
	@SuppressWarnings("unchecked") // Yay...
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if (capability == PlayerAetherManager.AETHER_PLAYER)
		{
			return (T) this.playerAether;
		}

		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound output = new NBTTagCompound();

		if (this.playerAether != null)
		{
			this.playerAether.saveNBTData(output);
		}

		return output;
	}

	@Override
	public void deserializeNBT(NBTTagCompound input)
	{
		if (this.playerAether != null)
		{
			this.playerAether.loadNBTData(input);
		}
	}

}