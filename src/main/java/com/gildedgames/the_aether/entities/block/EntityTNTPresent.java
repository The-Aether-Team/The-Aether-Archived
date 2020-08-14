package com.gildedgames.the_aether.entities.block;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTNTPresent extends Entity {

	public int fuse;

	public EntityTNTPresent(World world) {
		super(world);
		this.fuse = 10;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
	}

	public EntityTNTPresent(World world, double d, double d1, double d2) {
		this(world);
		this.setPosition(d + 0.5D, d1, d2 + 0.5D);
		this.motionY = 0.20000000298023224D;
		this.fuse = 10;
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionY *= 0.9800000190734863D;

		if (this.onGround) {
			this.motionY *= -0.5D;
		}

		if (this.fuse-- <= 0) {
			if (!this.worldObj.isRemote) {
				this.setDead();
				this.explode();
			}
		} else {
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	private void explode() {
		float f = 0.4F;
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("fuse", this.fuse);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.fuse = compound.getInteger("fuse");
	}

	@Override
	public float getShadowSize() {
		return 0.0F;
	}

}