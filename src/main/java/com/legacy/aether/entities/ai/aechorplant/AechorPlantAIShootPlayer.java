package com.legacy.aether.entities.ai.aechorplant;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.EnumDifficulty;

import com.legacy.aether.entities.hostile.EntityAechorPlant;
import com.legacy.aether.entities.projectile.EntityPoisonNeedle;

public class AechorPlantAIShootPlayer extends EntityAIBase
{

	private EntityAechorPlant shooter;

	private int reloadTime;

	public AechorPlantAIShootPlayer(EntityAechorPlant aechorplant)
	{
		this.shooter = aechorplant;
		this.setMutexBits(4);
	}

	@Override
	public boolean shouldExecute() 
	{
		return !this.shooter.isDead && this.shooter.getAttackTarget() != null;
	}

	@Override
	public void updateTask()
	{
		double distanceToPlayer = this.shooter.getAttackTarget().getDistance(this.shooter);
		double lookDistance = 5.5D + ((double)this.shooter.size / 2D);

		if(this.shooter.getAttackTarget().isDead || distanceToPlayer > lookDistance) 
		{
			this.shooter.setAttackTarget(null);
			this.reloadTime = 0;
		}

		if(this.reloadTime == 20 &&this.shooter.canEntityBeSeen(this.shooter.getAttackTarget())) 
		{
			this.shootAtPlayer();
			this.reloadTime = -10;
		}

		if(this.reloadTime != 20) 
		{
			++this.reloadTime;
		}
	}

	public void shootAtPlayer()
	{
		if(this.shooter.world.getDifficulty().equals(EnumDifficulty.PEACEFUL)) 
		{
			return;
		}

		double x = this.shooter.getAttackTarget().posX - this.shooter.posX;
		double z = this.shooter.getAttackTarget().posZ - this.shooter.posZ;
		double y = 0.1D + (Math.sqrt((x * x) + (z * z) + 0.1D) * 0.5D) + ((this.shooter.posY - this.shooter.getAttackTarget().posY) * 0.25D);

		double distance = 1.5D / Math.sqrt((x * x) + (z * z) + 0.1D);

		x = x * distance;
		z = z * distance;

		EntityPoisonNeedle poisonNeedle = new EntityPoisonNeedle(this.shooter.world, this.shooter);

		poisonNeedle.shoot(this.shooter, this.shooter.rotationPitch, this.shooter.rotationYaw, 0.0F, 0.5F, 1.0F);
		poisonNeedle.posY = this.shooter.posY + 1D;

        this.shooter.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.2F / (this.shooter.getRNG().nextFloat() * 0.2F + 0.9F));
		this.shooter.world.spawnEntity(poisonNeedle);

		poisonNeedle.shoot(x, y, z, 0.285F + ((float)y * 0.05F), 1.0F);
	}

}