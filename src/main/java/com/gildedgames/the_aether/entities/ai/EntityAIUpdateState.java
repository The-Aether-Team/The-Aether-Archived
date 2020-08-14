package com.gildedgames.the_aether.entities.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIUpdateState extends EntityAIBase {

	private EntityLivingBase entity;

	private boolean isStuckWarning = false;

	private long checkTime = 0L;

	private double checkX;

	private double checkY;

	private double checkZ;

	public EntityAIUpdateState(EntityLivingBase entity) {
		this.entity = entity;
		this.setMutexBits(1);
	}

	@Override
	public boolean isInterruptible() {
		return false;
	}

	@Override
	public void updateTask() {
		long curtime = System.currentTimeMillis();

		if (curtime > this.checkTime + 3000L) {
			double diffx = this.entity.posX - this.checkX;
			double diffy = this.entity.posY - this.checkY;
			double diffz = this.entity.posZ - this.checkZ;

			double distanceTravelled = Math.sqrt((diffx * diffx) + (diffy * diffy) + (diffz * diffz));

			if (distanceTravelled < 3D) {
				if (!this.isStuckWarning) {
					this.isStuckWarning = true;
				} else {
					this.entity.setDead();
				}
			}

			this.checkX = this.entity.posX;
			this.checkY = this.entity.posY;
			this.checkZ = this.entity.posZ;
			this.checkTime = curtime;
		}
	}

	@Override
	public boolean shouldExecute() {
		return !this.entity.isDead;
	}

}