package com.legacy.aether.server.entities.particles;

import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.world.World;

public class EntityPurpleFX extends ParticlePortal
{

	public EntityPurpleFX(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed)
	{
		super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);

		this.particleRed = 0.69F;
		this.particleGreen = 0.5255F;
		this.particleBlue = 0.918F;
	}

}