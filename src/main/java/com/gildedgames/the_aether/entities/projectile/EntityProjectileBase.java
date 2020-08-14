package com.gildedgames.the_aether.entities.projectile;

import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityProjectileBase extends Entity implements IProjectile, IThrowableEntity {

	public int xTile = -1;
	public int yTile = -1;
	public int zTile = -1;
	public Block inTile;
	public boolean inGround;
	public int throwableShake;
	public int canBePickedUp;
	private EntityLivingBase thrower;
	private String throwerName;
	public int ticksInGround;
	public int ticksInAir;

	public EntityProjectileBase(World world) {
		super(world);

		this.yOffset = 0.0F;
		this.setSize(0.25F, 0.25F);
	}

	public EntityProjectileBase(World worldIn, double x, double y, double z) {
		this(worldIn);

		this.setPosition(x, y, z);
	}

	public EntityProjectileBase(World world, EntityLivingBase thrower) {
		this(world, thrower.posX, thrower.posY + (double) thrower.getEyeHeight() - 0.10000000149011612D, thrower.posZ);

		this.thrower = thrower;
	}

	@Override
	protected void entityInit() {

	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return distance < d1 * d1;
	}

	public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
		float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
		float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		this.setThrowableHeading((double) f, (double) f1, (double) f2, velocity, inaccuracy);
		this.motionX += entityThrower.motionX;
		this.motionZ += entityThrower.motionZ;

		if (!entityThrower.onGround) {
			this.motionY += entityThrower.motionY;
		}
	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
		float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
		x /= (double) f2;
		y /= (double) f2;
		z /= (double) f2;
		x += this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		y += this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		z += this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		x *= (double) velocity;
		y *= (double) velocity;
		z *= (double) velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f3 = MathHelper.sqrt_double(x * x + z * z);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) f3) * 180.0D / Math.PI);
		this.ticksInGround = 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(x * x + z * z);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(x, z) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(y, (double) f) * 180.0D / Math.PI);
		}
	}

	@Override
	public void onUpdate() {
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();

		if (this.throwableShake > 0) {
			--this.throwableShake;
		}

		if (this.inGround) {
			if (this.worldObj.getBlock(this.xTile, this.yTile, this.zTile) == this.inTile) {
				++this.ticksInGround;

				if (this.ticksInGround == 1200) {
					this.setDead();
				}

				return;
			}

			this.inGround = false;
			this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
			this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
			this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
			this.ticksInGround = 0;
			this.ticksInAir = 0;
		} else {
			++this.ticksInAir;
		}

		Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX + this.getBoundingBoxExpansion(), this.posY + this.motionY + this.getBoundingBoxExpansion(), this.posZ + this.motionZ + this.getBoundingBoxExpansion());
		MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
		vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (movingobjectposition != null) {
			vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}

		if (!this.worldObj.isRemote) {
			Entity entity = null;
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(this.getBoundingBoxExpansion(), this.getBoundingBoxExpansion(), this.getBoundingBoxExpansion()));
			double d0 = 0.0D;
			EntityLivingBase entitylivingbase = this.getThrower();

			for (int j = 0; j < list.size(); ++j) {
				Entity entity1 = (Entity) list.get(j);

				if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase || this.ticksInAir >= 5)) {
					float f = 0.3F + this.getBoundingBoxExpansion();
					AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double) f, (double) f, (double) f);
					MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

					if (movingobjectposition1 != null) {
						double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}
		}

		if (movingobjectposition != null) {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Blocks.portal) {
				this.setInPortal();
			} else {
				this.onImpact(movingobjectposition);
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float f2 = 0.99F;
		float f3 = this.getGravityVelocity();

		if (this.isInWater()) {
			for (int i = 0; i < 4; ++i) {
				float f4 = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) f4, this.posY - this.motionY * (double) f4, this.posZ - this.motionZ * (double) f4, this.motionX, this.motionY, this.motionZ);
			}

			f2 = 0.8F;
		}

		this.motionX *= (double) f2;
		this.motionY *= (double) f2;
		this.motionZ *= (double) f2;
		this.motionY -= (double) f3;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	protected float getBoundingBoxExpansion() {
		return 1.0F;
	}

	protected float getGravityVelocity() {
		return 0.03F;
	}

	protected abstract void onImpact(MovingObjectPosition object);

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setShort("xTile", (short) this.xTile);
		compound.setShort("yTile", (short) this.yTile);
		compound.setShort("zTile", (short) this.zTile);
		compound.setByte("inTile", (byte) Block.getIdFromBlock(this.inTile));
		compound.setByte("shake", (byte) this.throwableShake);
		compound.setByte("inGround", (byte) (this.inGround ? 1 : 0));

		if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null && this.thrower instanceof EntityPlayer) {
			this.throwerName = this.thrower.getCommandSenderName();
		}

		compound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.xTile = compound.getShort("xTile");
		this.yTile = compound.getShort("yTile");
		this.zTile = compound.getShort("zTile");
		this.inTile = Block.getBlockById(compound.getByte("inTile") & 255);
		this.throwableShake = compound.getByte("shake") & 255;
		this.inGround = compound.getByte("inGround") == 1;
		this.throwerName = compound.getString("ownerName");

		if (this.throwerName != null && this.throwerName.length() == 0) {
			this.throwerName = null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}

	@Override
	public void setThrower(Entity entity) {
		if (entity instanceof EntityLivingBase) {
			this.thrower = (EntityLivingBase) entity;
		}
	}

	@Override
	public EntityLivingBase getThrower() {
		if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
			this.thrower = this.worldObj.getPlayerEntityByName(this.throwerName);
		}

		return this.thrower;
	}
}