package com.legacy.aether.entities.hostile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;

public class EntitySentry extends EntityLiving implements IMob {

	private int jumpCount;

	public int searchTicks;

	public int lostTicks;

	public EntitySentry(World world) {
		super(world);

		this.yOffset = 0.0F;
		this.jumpCount = this.rand.nextInt(20) + 10;
	}

	public EntitySentry(World world, double x, double y, double z) {
		this(world);

		this.rotationYaw = (float) this.rand.nextInt(4) * 1.570796F;

		this.setPosition(x, y, z);
		this.setSize(0.85F, 0.85F);
	}

	@Override
	public void entityInit() {
		super.entityInit();

		this.dataWatcher.addObject(20, new Byte((byte) 0));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		compound.setBoolean("awake", this.isAwake());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		this.setAwake(compound.getBoolean("awake"));
	}

	@Override
	public void onUpdate() {
		boolean flag = this.onGround;

		super.onUpdate();

		if (this.onGround && !flag) {
			this.worldObj.playSoundAtEntity(this, "mob.slime", getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
		} else if (!this.onGround && flag && this.getAttackTarget() != null) {
			this.motionX *= 3D;
			this.motionZ *= 3D;
		}
		if (this.getAttackTarget() != null && this.getAttackTarget().isDead) {
			this.setAttackTarget(null);
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		boolean flag = super.attackEntityFrom(source, damage);

		if (flag && (source.getEntity() instanceof EntityLiving)) {
			this.lostTicks = 0;
			this.setAwake(true);
			this.setAttackTarget((EntityLivingBase) source.getEntity());
		}
		return flag;
	}

	@Override
	public void applyEntityCollision(Entity entity) {
		if (!this.isDead && this.getAttackTarget() != null && this.getAttackTarget() == entity) {
			this.worldObj.createExplosion(entity, this.posX, this.posY, this.posZ, 0.1F, false);

			entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);

			if (entity instanceof EntityLivingBase) {
				EntityLivingBase entityliving = (EntityLivingBase) entity;

				double d = entityliving.posX - this.posX;
				double d2;

				for (d2 = entityliving.posZ - this.posZ; d * d + d2 * d2 < 0.0001D; d2 = (Math.random() - Math.random()) * 0.01D) {
					d = (Math.random() - Math.random()) * 0.01D;
				}

				entityliving.knockBack(this, 5, -d, -d2);
				entityliving.motionX *= 4D;
				entityliving.motionY *= 4D;
				entityliving.motionZ *= 4D;
			}

			float f = 0.01745329F;

			for (int i = 0; i < 40; i++) {
				double d1 = (float) this.posX + this.rand.nextFloat() * 0.25F;
				double d3 = (float) this.posY + 0.5F;
				double d4 = (float) this.posZ + this.rand.nextFloat() * 0.25F;
				float f1 = this.rand.nextFloat() * 360F;
				this.worldObj.spawnParticle("explode", d1, d3, d4, -Math.sin(f * f1) * 0.75D, 0.125D, Math.cos(f * f1) * 0.75D);
			}

			this.setDead();
		}
	}

	@Override
	protected void updateEntityActionState() {
		EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 8.0D);

		if (!this.isAwake() && this.searchTicks >= 8) {
			if (player != null && this.canEntityBeSeen(player)) {
				this.lostTicks = 0;
				this.setAwake(true);
				this.setAttackTarget(player);
				this.faceEntity(player, 10.0F, 10.0F);
			}

			this.searchTicks = 0;
		} else if (this.isAwake() && this.searchTicks >= 8) {
			if (this.getAttackTarget() == null) {
				if (player != null && this.canEntityBeSeen(player)) {
					this.lostTicks = 0;
					this.setAwake(true);
					this.setAttackTarget(player);
				} else {
					++this.lostTicks;

					if (this.lostTicks >= 4) {
						this.setSentryLost();
					}
				}
			} else if (this.getAttackTarget().isDead || !this.canEntityBeSeen(this.getAttackTarget()) || this.getDistanceToEntity(this.getAttackTarget()) >= 16.0F) {
				++this.lostTicks;

				if (this.lostTicks >= 4) {
					this.setSentryLost();
				}
			} else {
				this.lostTicks = 0;
			}

			this.searchTicks = 0;
		} else {
			++this.searchTicks;
		}

		if (!this.isAwake()) {
			return;
		}

		if (this.getAttackTarget() != null) {
			this.faceEntity(this.getAttackTarget(), 10.0F, 10.0F);
		}

		if (this.onGround && this.jumpCount-- <= 0) {
			this.isJumping = true;
			this.moveForward = 1.0F;
			this.jumpCount = this.rand.nextInt(20) + 10;
			this.moveStrafing = 0.5F - this.rand.nextFloat();

			if (this.getAttackTarget() != null) {
				this.jumpCount /= 2;
				this.moveForward = 1.0F;
			}

			this.playSound("mob.slime", getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
		} else {
			this.isJumping = false;

			if (this.onGround) {
				this.moveStrafing = this.moveForward = 0.0F;
			}
		}
	}

	private void setSentryLost() {
		this.lostTicks = 0;
		this.searchTicks = -64;
		this.setAttackTarget(null);
	}

	public void setAwake(boolean awake) {
		this.dataWatcher.updateObject(20, (byte) (awake ? 1 : 0));
	}

	public boolean isAwake() {
		return this.dataWatcher.getWatchableObjectByte(20) == (byte) 1;
	}

	@Override
	protected String getHurtSound() {
		return "mob.slime";
	}

	@Override
	protected String getDeathSound() {
		return "mob.slime";
	}

	@Override
	protected float getSoundVolume() {
		return 0.6F;
	}

	@Override
	protected Item getDropItem() {
		return this.rand.nextInt(5) == 0 ? Item.getItemFromBlock(BlocksAether.sentry_stone) : Item.getItemFromBlock(BlocksAether.carved_stone);
	}

}