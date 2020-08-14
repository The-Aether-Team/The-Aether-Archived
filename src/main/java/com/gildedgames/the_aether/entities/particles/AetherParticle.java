package com.gildedgames.the_aether.entities.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class AetherParticle extends EntityFX {

	public AetherParticle(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
	}

	public AetherParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
	}

	public void setMotionX(double motionX) {
		this.motionX = motionX;
	}

	public void setMotionY(double motionY) {
		this.motionY = motionY;
	}

	public void setMotionZ(double motionZ) {
		this.motionZ = motionZ;
	}

	public double getX() {
		return this.posX;
	}

	public double getY() {
		return this.posY;
	}

	public double getZ() {
		return this.posZ;
	}

	public double getMotionX() {
		return this.motionX;
	}

	public double getMotionY() {
		return this.motionY;
	}

	public double getMotionZ() {
		return this.motionZ;
	}

}