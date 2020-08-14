package com.gildedgames.the_aether.api.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import com.gildedgames.the_aether.api.AetherAPI;

public class IPlayerAetherProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound>
{

	private final IPlayerAether playerAether;

	public IPlayerAetherProvider(IPlayerAether playerAether)
	{
		this.playerAether = playerAether;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == AetherAPI.AETHER_PLAYER;
	}

	@Override
	@SuppressWarnings("unchecked") // Yay...
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if (capability == AetherAPI.AETHER_PLAYER)
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