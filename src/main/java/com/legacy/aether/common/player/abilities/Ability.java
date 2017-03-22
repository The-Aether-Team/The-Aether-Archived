package com.legacy.aether.common.player.abilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

import com.legacy.aether.common.player.PlayerAether;

public abstract class Ability 
{

	public PlayerAether playerAether;

	public EntityPlayer player;

	public Ability(PlayerAether player)
	{
		this.playerAether = player;
		this.player = player.thePlayer;
	}

	public boolean isEnabled()
	{
		return true;
	}

	public abstract void onUpdate();

	public boolean onPlayerAttacked(DamageSource source)
	{
		return true;
	}

}