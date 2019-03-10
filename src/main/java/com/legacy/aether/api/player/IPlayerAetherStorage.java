package com.legacy.aether.api.player;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class IPlayerAetherStorage implements IStorage<IPlayerAether>
{

	public IPlayerAetherStorage()
	{
	}

	public INBTBase writeNBT(Capability<IPlayerAether> capability, IPlayerAether instance, EnumFacing side)
	{
		NBTTagCompound compound = new NBTTagCompound();
		instance.writeAdditional(compound);
		return compound;
	}

	public void readNBT(Capability<IPlayerAether> capability, IPlayerAether instance, EnumFacing side, INBTBase nbt)
	{
		if (nbt instanceof NBTTagCompound)
		{
			instance.readAdditional((NBTTagCompound) nbt);
		}
	}

}