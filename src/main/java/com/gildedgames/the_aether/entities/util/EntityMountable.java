package com.gildedgames.the_aether.entities.util;

import com.gildedgames.the_aether.entities.passive.EntityAetherAnimal;
import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityMountable extends EntityAetherAnimal {

	protected float jumpPower;

	protected int field_110285_bP;

	protected boolean mountJumping;

	protected boolean playStepSound = false;

	protected boolean canJumpMidAir = false;

	public EntityMountable(World world) {
		super(world);
	}

	@Override
	public void entityInit() {
		super.entityInit();

		this.dataWatcher.addObject(17, new Byte((byte) 0)); //onGroundClient
		this.dataWatcher.addObject(18, new Byte((byte) 0));
	}

	@Override
	public boolean canRiderInteract() {
		return true;
	}

	@Override
	public boolean shouldDismountInWater(Entity rider) {
		return false;
	}

	public boolean isOnGround() {
		return this.dataWatcher.getWatchableObjectByte(17) == (byte) 1;
	}

	private void setOnGround(boolean onGround) {
		this.dataWatcher.updateObject(17, (byte) (onGround ? 1 : 0));
	}

	public boolean isRiderSneaking() {
		return this.dataWatcher.getWatchableObjectByte(18) == (byte) 1;
	}

	public void setRiderSneaking(boolean riderSneaking) {
		this.dataWatcher.updateObject(18, (byte) (riderSneaking ? 1 : 0));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!this.worldObj.isRemote) {
			if (this.onGround != this.isOnGround()) {
				this.setOnGround(this.onGround);
			}

			this.updateRider();
		}
	}

	public void updateRider() {
		if (this.canDismount() && this.riddenByEntity instanceof EntityPlayer) {
			PlayerAether playerAether = PlayerAether.get((EntityPlayer) this.riddenByEntity);

			if (playerAether.isMountSneaking()) {
				if (this.onGround) {
					this.riddenByEntity.mountEntity(null);
					playerAether.setMountSneaking(false);
				}

				this.setRiderSneaking(true);
			} else {
				this.setRiderSneaking(false);
			}
		}
	}

	private float updateRotation(float angle, float targetAngle, float maxIncrease) {
		float f = MathHelper.wrapAngleTo180_float(targetAngle - angle);

		if (f > maxIncrease) {
			f = maxIncrease;
		}

		if (f < -maxIncrease) {
			f = -maxIncrease;
		}

		return angle + f;
	}

	@Override
	public void moveEntityWithHeading(float par1, float par2) {
		Entity entity = this.riddenByEntity;

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			this.prevRotationYaw = this.rotationYaw = player.rotationYaw;
			this.prevRotationPitch = this.rotationPitch = player.rotationPitch;

			this.rotationYawHead = player.rotationYawHead;

			par1 = player.moveStrafing;
			par2 = player.moveForward;

			if (par2 <= 0.0F) {
				par2 *= 0.25F;
				this.field_110285_bP = 0;
			}

			double d01 = player.posX - this.posX;
			double d2 = player.posZ - this.posZ;

			float f = (float) (Math.atan2(d2, d01) * (180D / Math.PI)) - 90.0F;

			if (player.moveStrafing != 0.0F && player.worldObj.isRemote) {
				this.rotationYaw = this.updateRotation(this.rotationYaw, f, 40.0F);
			}

			if (PlayerAether.get(player).isJumping()) {
				onMountedJump(par1, par2);
			}

			if (this.jumpPower > 0.0F && !this.isMountJumping() && (this.onGround || this.canJumpMidAir)) {
				this.motionY = this.getMountJumpStrength() * (double) this.jumpPower;

				if (this.isPotionActive(Potion.jump)) {
					this.motionY += (double) ((float) (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
				}

				this.setMountJumping(true);
				this.isAirBorne = true;

				this.jumpPower = 0.0F;

				if (!this.worldObj.isRemote) {
					this.moveEntity(this.motionX, this.motionY, this.motionZ);
				}
			}

			this.motionX *= 0.35F;
			this.motionZ *= 0.35F;

			this.stepHeight = 1.0F;

			if (!this.worldObj.isRemote) {
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.6F;
				super.moveEntityWithHeading(par1, par2);
			}

			if (this.onGround) {
				this.jumpPower = 0.0F;
				this.setMountJumping(false);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d0 = this.posX - this.prevPosX;
			double d1 = this.posZ - this.prevPosZ;
			float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

			if (f4 > 1.0F) {
				f4 = 1.0F;
			}

			this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		} else {
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(par1, par2);
		}
	}

	@Override
	public float getAIMoveSpeed() {
		return this.riddenByEntity != null ? this.getMountedMoveSpeed() : super.getAIMoveSpeed();
	}

	@Override
	protected void func_145780_a(int x, int y, int z, Block block) {

	}

	public float getMountedMoveSpeed() {
		return 0.15F;
	}

	protected double getMountJumpStrength() {
		return 1.0D;
	}

	protected void setMountJumping(boolean mountJumping) {
		this.mountJumping = mountJumping;
	}

	protected boolean isMountJumping() {
		return this.mountJumping;
	}

	public void onMountedJump(float par1, float par2) {
		this.jumpPower = 0.4F;
	}

	public boolean canDismount() {
		return true;
	}

}