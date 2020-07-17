package com.legacy.aether.player.movement;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;

import com.legacy.aether.player.PlayerAether;

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
			float f2 = 0.03F;
			float f3 = (float) EnchantmentHelper.getDepthStriderModifier(player);

			if (f3 > 3.0F)
			{
				f3 = 3.0F;
			}

			if (f3 > 0.0F)
			{
				f2 += (f3 / 100.0F) * 0.5F;
			}

			player.moveRelative(movementLR, 0.0F, movementFB, f2);
		}

		if (player.isInLava())
		{
			float f2 = 0.06F;
			float f3 = (float) EnchantmentHelper.getDepthStriderModifier(player);

			if (f3 > 3.0F)
			{
				f3 = 3.0F;
			}

			if (f3 > 0.0F)
			{
				f2 += (f3 / 100.0F) * 0.5F;
			}

			player.moveRelative(movementLR, 0.0F, movementFB, f2);
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