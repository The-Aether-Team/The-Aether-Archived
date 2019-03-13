package com.legacy.aether.player;

import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.api.player.util.IAetherBoss;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerAether implements IPlayerAether
{

	private IAetherBoss focusedBoss;

	private EntityPlayer player;

	public PlayerAether()
	{

	}

	public PlayerAether(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public void onUpdate()
	{
		//this.player.getMaxInPortalTime()
	}

	@Override
	public void writeAdditional(NBTTagCompound compound)
	{

	}

	@Override
	public void readAdditional(NBTTagCompound compound)
	{

	}

	@Override
	public EntityPlayer getEntity()
	{
		return this.player;
	}

	@Override
	public void setFocusedBoss(IAetherBoss boss)
	{
		this.focusedBoss = boss;
	}

	@Override
	public IAetherBoss getFocusedBoss()
	{
		return this.focusedBoss;
	}

	@Override
	public void inflictPoison(int ticks)
	{

	}

	@Override
	public boolean isPoisoned()
	{
		return false;
	}

	@Override
	public void inflictCure(int ticks)
	{

	}

	@Override
	public boolean isCured()
	{
		return false;
	}

}