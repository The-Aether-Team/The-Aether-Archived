package com.gildedgames.the_aether.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIAttackContinuously extends EntityAIBase {

	private EntityCreature attacker;

	private int attackTick;

	double speedTowardsTarget;

	private double targetX;
	private double targetY;
	private double targetZ;

	public EntityAIAttackContinuously(EntityCreature creature, double speedIn) {
		this.attacker = creature;
		this.speedTowardsTarget = speedIn;

		this.setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = (EntityLivingBase) this.attacker.getEntityToAttack();

		if (entitylivingbase == null) {
			return false;
		} else if (!entitylivingbase.isEntityAlive()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean continueExecuting() {
		EntityLivingBase entitylivingbase = (EntityLivingBase) this.attacker.getEntityToAttack();
		return entitylivingbase != null;
	}

	@Override
	public void startExecuting() {

	}

	@Override
	public void resetTask() {
		EntityLivingBase entitylivingbase = (EntityLivingBase) this.attacker.getEntityToAttack();

		if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).capabilities.isCreativeMode) {
			this.attacker.setAttackTarget((EntityLivingBase) null);
		}
	}

	@Override
	public void updateTask() {
		EntityLivingBase entitylivingbase = (EntityLivingBase) this.attacker.getEntityToAttack();

		this.attacker.getNavigator().setPath(this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase), this.speedTowardsTarget);
		this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 360.0F, 360.0F);

		double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);

		if (this.attacker.getEntitySenses().canSee(entitylivingbase) && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
			this.targetX = entitylivingbase.posX;
			this.targetY = entitylivingbase.boundingBox.minY;
			this.targetZ = entitylivingbase.posZ;
		}

		this.attackTick = Math.max(this.attackTick - 1, 0);
		this.checkAndPerformAttack(entitylivingbase, d0);
	}

	protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
		double d0 = this.getAttackReachSqr(p_190102_1_);

		if (p_190102_2_ <= d0 && this.attackTick <= 0) {
			this.attackTick = 20;
			this.attacker.swingItem();
			this.attacker.attackEntityAsMob(p_190102_1_);
		}
	}

	protected double getAttackReachSqr(EntityLivingBase attackTarget) {
		return (double) (this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width);
	}

}