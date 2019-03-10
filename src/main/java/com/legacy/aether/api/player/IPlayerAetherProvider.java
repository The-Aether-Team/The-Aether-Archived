package com.legacy.aether.api.player;

import com.legacy.aether.api.AetherAPI;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class IPlayerAetherProvider implements ICapabilitySerializable<NBTTagCompound>
{

	private final LazyOptional<IPlayerAether> playerAetherHandler;

	public IPlayerAetherProvider(IPlayerAether playerAether)
	{
		this.playerAetherHandler = LazyOptional.of(() -> {
			return playerAether;
		});
	}

	public <T> LazyOptional<T> getCapability(Capability<T> cap, EnumFacing side)
	{
		return cap == AetherAPI.AETHER_PLAYER ? this.playerAetherHandler.cast() : LazyOptional.empty();
	}

	public void deserializeNBT(NBTTagCompound compound)
	{
		this.playerAetherHandler.orElse(null).readAdditional(compound);
	}

	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();

		this.playerAetherHandler.orElse(null).writeAdditional(compound);

		return compound;
	}

}