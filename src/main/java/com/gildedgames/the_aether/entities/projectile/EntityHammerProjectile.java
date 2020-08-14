package com.gildedgames.the_aether.entities.projectile;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.gildedgames.the_aether.api.player.util.IAetherBoss;
import com.gildedgames.the_aether.entities.util.EntitySaddleMount;

public class EntityHammerProjectile extends EntityProjectileBase {

	public ArrayList<Block> harvestBlockBans = new ArrayList<Block>();

	public EntityHammerProjectile(World worldIn) {
		super(worldIn);
	}

	public EntityHammerProjectile(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		this.worldObj.spawnParticle("reddust", this.posX, this.posY + 0.2F, this.posZ, 1.0D, 1.0D, 1.0D);

		if (this.ticksInAir > 100) {
			this.setDead();
		} else {
			this.ticksInAir++;
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void onImpact(MovingObjectPosition object) {
		if (object.typeOfHit == MovingObjectType.ENTITY) {
			if (object.entityHit instanceof EntitySaddleMount && ((EntitySaddleMount) object.entityHit).isSaddled()) {

			} else if (object.entityHit != this.getThrower() && !(object.entityHit instanceof IAetherBoss)) {
				object.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), 5);
				object.entityHit.addVelocity(this.motionX, 0.6D, this.motionZ);
			}
		}

		for (int j = 0; j < 8; j++) {
			this.worldObj.spawnParticle("explode", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("explode", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("largesmoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle("flame", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected float getBoundingBoxExpansion() {
		return 2.5F;
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

}