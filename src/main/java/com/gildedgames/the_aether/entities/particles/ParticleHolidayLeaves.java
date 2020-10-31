package com.gildedgames.the_aether.entities.particles;

import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.world.World;

public class ParticleHolidayLeaves extends ParticlePortal
{

	public ParticleHolidayLeaves(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed)
	{
		super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);

		this.particleRed = 1F;
		this.particleGreen = 1F;
		this.particleBlue = 1F;
	}

}