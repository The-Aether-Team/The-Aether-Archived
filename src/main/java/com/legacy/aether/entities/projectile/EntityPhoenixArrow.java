package com.legacy.aether.entities.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IThrowableEntity;

public class EntityPhoenixArrow extends EntityArrow implements IThrowableEntity {

	private int timeInGround;

	private boolean hitGround;

	public EntityPhoenixArrow(World worldIn) {
		super(worldIn);
	}

	public EntityPhoenixArrow(World worldIn, EntityLivingBase shooter, float distance) {
		super(worldIn, shooter, distance);
	}

	@Override
	public void onUpdate() {
		if (this.arrowShake == 7) {
			this.hitGround = true;
		}

		if (this.hitGround) {
			++this.timeInGround;

			if (this.timeInGround % 5 == 0) {
				this.worldObj.spawnParticle("flame", this.posX + (this.rand.nextGaussian() / 5D), this.posY + (this.rand.nextGaussian() / 5D), this.posZ + (this.rand.nextGaussian() / 3D), 0.0D, 0.0D, 0.0D);
			}
		} else {
			for (int j = 0; j < 2; ++j) {
				this.worldObj.spawnParticle("flame", this.posX + (this.rand.nextGaussian() / 5D), this.posY + (this.rand.nextGaussian() / 5D), this.posZ + (this.rand.nextGaussian() / 3D), 0.0D, 0.0D, 0.0D);
			}
		}

		super.onUpdate();
	}

	@Override
	public void setThrower(Entity entity) {
		this.shootingEntity = entity;
	}

	@Override
	public Entity getThrower() {
		return this.shootingEntity;
	}

}