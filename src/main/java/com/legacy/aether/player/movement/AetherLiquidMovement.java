package com.legacy.aether.player.movement;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import com.legacy.aether.api.player.IPlayerAether;

public class AetherLiquidMovement {

	private IPlayerAether player;

	public AetherLiquidMovement(IPlayerAether player) {
		this.player = player;
	}

	public void onUpdate() {
		Entity entity = this.player.getEntity();

		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLiving = (EntityLivingBase) entity;

			float movementLR = this.negativeDifference(entityLiving, entityLiving.moveStrafing);
			float movementFB = this.negativeDifference(entityLiving, entityLiving.moveForward);

			if (entityLiving.isInWater()) {
				entityLiving.moveFlying(movementLR, movementFB, 0.03F);
			}

			if (entityLiving.handleLavaMovement()) {
				entityLiving.moveFlying(movementLR, movementFB, 0.06F);
			}
		}
	}

	public float negativeDifference(EntityLivingBase entity, float number) {
		if (number < 0.0F) {
			return number + 0.1F;
		} else if (number > 0.0F) {
			return number - 0.1F;
		} else {
			return 0.0F;
		}
	}

}