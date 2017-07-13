package com.legacy.aether.common.player.movement;

import net.minecraft.entity.player.EntityPlayer;

import com.legacy.aether.common.player.PlayerAether;

public class AetherLiquidMovement
{

	private PlayerAether playerAether;

	public AetherLiquidMovement(PlayerAether player)
	{
		this.playerAether = player;
	}

	public void onUpdate()
	{
		EntityPlayer player = this.playerAether.thePlayer;

		float movementLR = negativeDifference(player, player.moveStrafing);
		float movementFB = negativeDifference(player, player.moveForward);

		if (player.isInWater())
		{
			player.moveRelative(movementLR, movementFB, 0.03F);
		}

		if (player.isInLava())
		{
			player.moveRelative(movementLR, movementFB, 0.06F);
		}
	}

	public float negativeDifference(EntityPlayer player, float number)
	{
		if (number < 0.0F)
		{
			return number + 0.1F;
		}
		else if (number > 0.0F)
		{
			return number - 0.1F;
		}
		else
		{
			return 0.0F;
		}
	}

}