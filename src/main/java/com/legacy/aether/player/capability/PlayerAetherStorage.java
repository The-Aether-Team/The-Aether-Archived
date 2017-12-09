package com.legacy.aether.player.capability;

import com.legacy.aether.player.PlayerAether;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PlayerAetherStorage implements IStorage<PlayerAether>
{

	@Override
	public NBTBase writeNBT(Capability<PlayerAether> capability, PlayerAether instance, EnumFacing side) 
	{
		NBTTagCompound input = new NBTTagCompound();

		instance.saveNBTData(input);

		return input;
	}

	@Override
	public void readNBT(Capability<PlayerAether> capability, PlayerAether instance, EnumFacing side, NBTBase nbt) 
	{
		NBTTagCompound output = (NBTTagCompound) nbt;

		instance.loadNBTData(output);
	}

}