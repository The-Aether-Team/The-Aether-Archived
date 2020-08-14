package com.gildedgames.the_aether.api.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class IPlayerAetherStorage implements IStorage<IPlayerAether>
{

	@Override
	public NBTBase writeNBT(Capability<IPlayerAether> capability, IPlayerAether instance, EnumFacing side)
	{
		NBTTagCompound compound = new NBTTagCompound();

		instance.saveNBTData(compound);

		return compound;
	}

	@Override
	public void readNBT(Capability<IPlayerAether> capability, IPlayerAether instance, EnumFacing side, NBTBase nbt)
	{
		if (nbt instanceof NBTTagCompound)
		{
			instance.loadNBTData((NBTTagCompound) nbt);
		}
	}

}