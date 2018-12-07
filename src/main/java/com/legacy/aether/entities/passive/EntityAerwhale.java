package com.legacy.aether.entities.passive;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.ai.EntityAIUpdateState;

public class EntityAerwhale extends EntityFlying implements IMob {

	private double motionYaw;

	private double motionPitch;

	public double aerwhaleRotationYaw;

	public double aerwhaleRotationPitch;

	public EntityAerwhale(World world) {
		super(world);

		this.setSize(4F, 4F);
		this.isImmuneToFire = true;
		this.ignoreFrustumCheck = true;
		this.rotationYaw = 360F * this.getRNG().nextFloat();
		this.rotationPitch = 90F * this.getRNG().nextFloat() - 45F;
		this.tasks.addTask(0, new EntityAIUpdateState(this));
		this.tasks.addTask(6, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.boundingBox.minY);
		int k = MathHelper.floor_double(this.posZ);

		return this.worldObj.getBlock(i, j - 1, k) == BlocksAether.aether_grass && this.rand.nextInt(65) == 0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY), MathHelper.floor_double(this.posZ)) > 8 && super.getCanSpawnHere();
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		this.extinguish();
		this.updateEntityActionState();
	}

	@Override
	public void updateEntityActionState() {
		if (this.riddenByEntity != null) {
			return;
		}

		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.boundingBox.minY);
		int z = MathHelper.floor_double(this.posZ);

		double[] distances = new double[5];

		distances[0] = this.checkForTravelableCourse(0F, 0F);
		distances[1] = this.checkForTravelableCourse(45F, 0F);
		distances[2] = this.checkForTravelableCourse(0F, 45F);
		distances[3] = this.checkForTravelableCourse(-45F, 0F);
		distances[4] = this.checkForTravelableCourse(0, -45F);

		int course = this.getCorrectCourse(distances);

		if (course == 0) {
			if (distances[0] == 50D) {
				this.motionYaw *= 0.9F;
				this.motionPitch *= 0.9F;

				if (this.posY > 100) {
					this.motionPitch -= 2F;
				}
				if (this.posY < 20) {
					this.motionPitch += 2F;
				}
			} else {
				this.rotationPitch = -this.rotationPitch;
				this.rotationYaw = -this.rotationYaw;
			}
		} else if (course == 1) {
			this.motionYaw += 5F;
		} else if (course == 2) {
			this.motionPitch -= 5F;
		} else if (course == 3) {
			this.motionYaw -= 5F;
		} else {
			this.motionPitch += 5F;
		}

		if (this.posY < -64.0D) {
			this.setDead();
		}

		this.motionYaw += 2F * this.getRNG().nextFloat() - 1F;
		this.motionPitch += 2F * this.getRNG().nextFloat() - 1F;

		this.rotationPitch += 0.1D * this.motionPitch;
		this.rotationYaw += 0.1D * this.motionYaw;

		this.aerwhaleRotationPitch += 0.1D * this.motionPitch;
		this.aerwhaleRotationYaw += 0.1D * this.motionYaw;

		if (this.rotationPitch < -60F) {
			this.rotationPitch = -60F;
		}

		if (this.aerwhaleRotationPitch < -60D) {
			this.aerwhaleRotationPitch = -60D;
		}

		if (this.rotationPitch > 60F) {
			this.rotationPitch = 60F;
		}

		if (this.aerwhaleRotationPitch > 60D) {
			this.aerwhaleRotationPitch = 60D;
		}

		this.rotationPitch *= 0.99D;
		this.aerwhaleRotationPitch *= 0.99D;

		if (!this.worldObj.isRemote) {
			this.motionX += 0.01D * Math.cos((this.aerwhaleRotationYaw / 180D) * 3.1415926535897931D) * Math.cos((this.aerwhaleRotationPitch / 180D) * 3.1415926535897931D);

			this.motionY += 0.01D * Math.sin((this.aerwhaleRotationPitch / 180D) * Math.PI);

			this.motionZ += 0.01D * Math.sin((this.aerwhaleRotationYaw / 180D) * 3.1415926535897931D) * Math.cos((this.aerwhaleRotationPitch / 180D) * 3.1415926535897931D);

			this.motionX *= 0.98D;
			this.motionY *= 0.98D;
			this.motionZ *= 0.98D;
		}

		if (this.motionX > 0D && this.worldObj.getBlock(x + 1, y, z) != Blocks.air) {
			if (!this.worldObj.isRemote) {
				this.motionX = -this.motionX;
			}

			this.motionYaw -= 10F;
		} else if (this.motionX < 0D && this.worldObj.getBlock(x - 1, y, z) != Blocks.air) {
			if (!this.worldObj.isRemote) {
				this.motionX = -this.motionX;
			}

			this.motionYaw += 10F;
		}

		if (this.motionY > 0D && this.worldObj.getBlock(x, y + 1, z) != Blocks.air) {
			if (!this.worldObj.isRemote) {
				this.motionY = -this.motionY;
			}

			this.motionPitch -= 20F;
		} else if (this.motionY < 0D && this.worldObj.getBlock(x, y - 1, z) != Blocks.air) {
			if (!this.worldObj.isRemote) {
				this.motionY = -this.motionY;
			}

			this.motionPitch += 20F;
		}

		if (this.motionZ > 0D && this.worldObj.getBlock(x, y, z + 1) != Blocks.air) {
			if (!this.worldObj.isRemote) {
				this.motionZ = -this.motionZ;
			}

			this.motionYaw -= 10F;
		} else if (this.motionZ < 0D && this.worldObj.getBlock(x, y, z - 1) != Blocks.air) {
			if (!this.worldObj.isRemote) {
				this.motionZ = -this.motionZ;
			}

			this.motionYaw += 10F;
		}

		if (!this.worldObj.isRemote) {
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
		}
	}

	private int getCorrectCourse(double[] distances) {
		int correctCourse = 0;

		for (int i = 1; i < 5; i++) {
			if (distances[i] > distances[correctCourse]) {
				correctCourse = i;
			}
		}

		return correctCourse;
	}

	private double checkForTravelableCourse(float rotationYawOffset, float rotationPitchOffset) {
		double standard = 50D;

		float yaw = this.rotationYaw + rotationYawOffset;
		float pitch = this.rotationYaw + rotationYawOffset;

		float f3 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
		float f4 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
		float f5 = MathHelper.cos(-pitch * 0.01745329F);
		float f6 = MathHelper.sin(-pitch * 0.01745329F);

		float f7 = f4 * f5;
		float f8 = f6;
		float f9 = f3 * f5;

		Vec3 vec3d = Vec3.createVectorHelper(this.posX, this.boundingBox.minY, this.posZ);
		Vec3 vec3d1 = vec3d.addVector((double) f7 * standard, (double) f8 * standard, (double) f9 * standard);

		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3d, vec3d1, true);

		if (movingobjectposition == null) {
			return standard;
		}

		if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
			double i = movingobjectposition.blockX - this.posX;
			double j = movingobjectposition.blockY - this.boundingBox.minY;
			double k = movingobjectposition.blockZ - this.posZ;

			return Math.sqrt(i * i + j * j + k * k);
		}

		return standard;
	}

	@Override
	public String getLivingSound() {
		return "aether_legacy:aemob.aerwhale.call";
	}

	@Override
	protected String getHurtSound() {
		return "aether_legacy:aemob.aerwhale.death";
	}

	@Override
	protected String getDeathSound() {
		return "aether_legacy:aemob.aerwhale.death";
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

}