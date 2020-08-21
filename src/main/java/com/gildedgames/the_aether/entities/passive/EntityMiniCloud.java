package com.gildedgames.the_aether.entities.passive;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.gildedgames.the_aether.entities.projectile.crystals.EntityCrystal;
import com.gildedgames.the_aether.entities.projectile.crystals.EnumCrystalType;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityMiniCloud extends EntityFlying implements IEntityAdditionalSpawnData {

	public EntityPlayer owner;

	public int shotTimer, lifeSpan;

	public boolean direction;

	public double targetX, targetY, targetZ;

	public EntityMiniCloud(World worldObj) {
		super(worldObj);

		this.noClip = true;
		this.lifeSpan = 3600;
		this.entityCollisionReduction = 1.75F;

		this.setSize(0.5F, 0.45F);
	}

	public EntityMiniCloud(World worldObj, EntityPlayer owner, int direction) {
		this(worldObj);

		this.owner = owner;
		this.direction = direction == 0;
		this.rotationYaw = this.owner.rotationYaw;
		this.rotationPitch = this.owner.rotationPitch;

		this.getTargetPos();
		this.setPosition(this.targetX, this.targetY, this.targetZ);
	}

	public boolean isInRangeToRenderDist(double var1) {
		return true;
	}

	public void getTargetPos() {
		if (this.getDistanceToEntity(this.owner) > 2.0F) {
			this.targetX = this.owner.posX;
			this.targetY = this.owner.posY + 1.10000000149011612D;
			this.targetZ = this.owner.posZ;
		} else {
			double var1 = (double) this.owner.rotationYaw;

			if (this.direction) {
				var1 -= 90.0D;
			} else {
				var1 += 90.0D;
			}

			var1 /= -(180D / Math.PI);
			this.targetX = this.owner.posX + Math.sin(var1) * 1.05D;
			this.targetY = this.owner.posY + 1.10000000149011612D;
			this.targetZ = this.owner.posZ + Math.cos(var1) * 1.05D;
		}
	}

	public boolean atShoulder() {
		double var1 = this.posX - this.targetX;
		double var3 = this.posY - this.targetY;
		double var5 = this.posZ - this.targetZ;
		return Math.sqrt(var1 * var1 + var3 * var3 + var5 * var5) < 0.3D;
	}

	public void approachTarget() {
		double var1 = this.targetX - this.posX;
		double var3 = this.targetY - this.posY;
		double var5 = this.targetZ - this.posZ;
		double var7 = Math.sqrt(var1 * var1 + var3 * var3 + var5 * var5) * 3.25D;
		this.motionX = (this.motionX + var1 / var7) / 2.0D;
		this.motionY = (this.motionY + var3 / var7) / 2.0D;
		this.motionZ = (this.motionZ + var5 / var7) / 2.0D;
		Math.atan2(var1, var5);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);

		var1.setShort("LifeSpan", (short) this.lifeSpan);
		var1.setShort("ShotTimer", (short) this.shotTimer);
		var1.setBoolean("direction", this.direction);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);

		this.lifeSpan = var1.getShort("LifeSpan");
		this.shotTimer = var1.getShort("ShotTimer");
		this.direction = var1.getBoolean("direction");
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();

		--this.lifeSpan;

		if (this.lifeSpan <= 0) {
			this.spawnExplosionParticle();
			this.isDead = true;
		} else {
			if (this.owner == null && !this.worldObj.isRemote) {
				this.setDead();
				return;
			}

			if (this.shotTimer > 0) {
				--this.shotTimer;
			}

			if (!this.owner.isDead) {
				if (this.worldObj.isRemote) {
					return;
				}

				this.getTargetPos();

				if (this.atShoulder()) {
					this.motionX *= 0.65D;
					this.motionY *= 0.65D;
					this.motionZ *= 0.65D;

					this.rotationYaw = this.owner.rotationYaw + (this.direction ? 1.0F : -1F);
					this.rotationPitch = this.owner.rotationPitch;
					this.rotationYawHead = this.owner.rotationYawHead;

					if (this.shotTimer <= 0 && this.owner.swingProgress > 0.0F) {
						float var7 = this.rotationYaw - (this.direction ? 1.0F : -1.0F);
						double var1 = this.posX + Math.sin((double) var7 / -(180D / Math.PI)) * 1.6D;
						double var3 = this.posY - -1.0D;
						double var5 = this.posZ + Math.cos((double) var7 / -(180D / Math.PI)) * 1.6D;
						EntityCrystal crystal = new EntityCrystal(this.worldObj, var1, var3, var5, EnumCrystalType.CLOUD);
						Vec3 var9 = this.getLookVec();

						crystal.shootingEntity = this.owner;

						if (var9 != null) {
							crystal.smotionX = var9.xCoord * 1.5D;
							crystal.smotionY = var9.yCoord * 1.5D;
							crystal.smotionZ = var9.zCoord * 1.5D;
						}

						crystal.wasHit = true;

						if (!this.worldObj.isRemote) {
							this.worldObj.spawnEntityInWorld(crystal);
						}

						this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "aether_legacy:aemob.zephyr.shoot", 0.75F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

						this.shotTimer = 40;
					}
				} else {
					this.approachTarget();
				}
			} else {
				this.spawnExplosionParticle();
				this.isDead = true;
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource var1, float var2) {
		if (var1.getEntity() == this.owner || var1.getDamageType() == "inWall") {
			return false;
		}

		return super.attackEntityFrom(var1, var2);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeBoolean(this.direction);
		buffer.writeInt(this.owner.getEntityId());
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		this.direction = buffer.readBoolean();
		this.owner = (EntityPlayer) this.worldObj.getEntityByID(buffer.readInt());
	}

}