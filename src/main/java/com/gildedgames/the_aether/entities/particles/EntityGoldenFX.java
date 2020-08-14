package com.gildedgames.the_aether.entities.particles;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

public class EntityGoldenFX extends EntityPortalFX {

	public EntityGoldenFX(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed) {
		super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);

		this.particleBlue = 0.0F;
		this.particleRed = 0.976F;
		this.particleGreen = 0.7450980392156863F;
	}

}