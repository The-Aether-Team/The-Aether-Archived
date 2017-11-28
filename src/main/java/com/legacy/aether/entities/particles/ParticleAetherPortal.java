package com.legacy.aether.entities.particles;

import net.minecraft.client.particle.ParticlePortal;
import net.minecraft.world.World;

public class ParticleAetherPortal extends ParticlePortal
{

    public ParticleAetherPortal(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed)
	{
		super(world, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);

        float f = this.rand.nextFloat() * 0.6F + 0.4F;

        this.particleRed = this.particleGreen = this.particleBlue = 1.0F * f;
        this.particleRed *= 0.2F;
        this.particleGreen *= 0.2F;
    }

}
