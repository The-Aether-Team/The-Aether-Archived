package com.legacy.aether.common.entities.projectile;

import java.util.ArrayList;
import java.util.List;

import com.legacy.aether.common.entities.util.EntitySaddleMount;
import com.legacy.aether.common.items.ItemsAether;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityHammerProjectile extends Entity implements IProjectile
{

	private BlockPos posTile;

	public float entityYaw;

	public float entityPitch;

	public EntityPlayer thrower;

	private int tickInAir;

	public ArrayList<Block> harvestBlockBans = new ArrayList<Block>();

	public EntityHammerProjectile(World world)
	{
		super(world);
		this.posTile = new BlockPos(-1, -1, -1);
		this.tickInAir = 0;
		this.setSize(0.25F, 0.25F);
	}

	@Override
	protected void entityInit()
	{

	}

    public double getYOffset()
    {
        return 0.0D;
    }

	public EntityLivingBase getThrower()
	{
		return thrower;
	}

	@Override
	public boolean isInRangeToRenderDist(double d)
	{
		double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4D;
		d1 *= 64D;
		return d < d1 * d1;
	}

	public EntityHammerProjectile(World world, EntityPlayer entityliving)
	{
		this(world);
		this.thrower = entityliving;
		this.setLocationAndAngles(entityliving.posX, entityliving.posY + (double) entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
		this.posX -= MathHelper.cos((this.rotationYaw / 180F) * 3.141593F) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin((this.rotationYaw / 180F) * 3.141593F) * 0.16F;
		this.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		this.motionX = -MathHelper.sin((this.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((this.rotationPitch / 180F) * 3.141593F) * 0.4F;
		this.motionZ = MathHelper.cos((this.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((this.rotationPitch / 180F) * 3.141593F) * 0.4F;
		this.motionY = -MathHelper.sin((this.rotationPitch / 180F) * 3.141593F) * 0.4F;
		this.setSnowballHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
	}

	public EntityHammerProjectile(World world, double d, double d1, double d2)
	{
		this(world);
		this.setPositionAndRotation(d, d1, d2, this.rotationYaw, this.rotationPitch);
	}

	public void setSnowballHeading(double d, double d1, double d2, float f, float f1)
	{
		float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
		d /= f2;
		d1 /= f2;
		d2 /= f2;
		d += this.rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d1 += this.rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d2 += this.rand.nextGaussian() * 0.0074999998323619366D * (double) f1;
		d *= f;
		d1 *= f;
		d2 *= f;
		this.motionX = d;
		this.motionY = d1;
		this.motionZ = d2;
		float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
		this.prevRotationYaw = this.rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
		this.prevRotationPitch = this.rotationPitch = (float) ((Math.atan2(d1, f3) * 180D) / 3.1415927410125732D);
	}

	@Override
	public void setVelocity(double d, double d1, double d2)
	{
		this.motionX = d;
		this.motionY = d1;
		this.motionZ = d2;
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(d * d + d2 * d2);
			this.prevRotationYaw = this.rotationYaw = (float) ((Math.atan2(d, d2) * 180D) / 3.1415927410125732D);
			this.prevRotationPitch = this.rotationPitch = (float) ((Math.atan2(d1, f) * 180D) / 3.1415927410125732D);
		}
	}

	@Override
	public void onUpdate()
	{
		this.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY + 0.2f, this.posZ, 1.0d, 1.0d, 1.0d);

		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;

		super.onUpdate();

		if (this.tickInAir > 100)
		{
			this.setDead();
		}
		else
		{
			this.tickInAir++;
		}

		Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult movingobjectposition = this.worldObj.rayTraceBlocks(vec3d, vec3d1, false, true, false);
		RayTraceResult rayTraceResult = this.worldObj.rayTraceBlocks(vec3d, vec3d1, false, true, false);
		vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (movingobjectposition != null)
		{
			vec3d1 = new Vec3d(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
		}

		if (!this.worldObj.isRemote && this.thrower != null)
		{
			Entity entity = null;
			Entity extraEntity = null;
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(8.0D));
			double d = 0.0D;
			for (int l = 0; l < list.size(); l++)
			{
				Entity entity1 = (Entity) list.get(l);
				if (entity1 != this.thrower)
				{
					AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.3F);
					AxisAlignedBB extraAxisalignedbb = entity1.getEntityBoundingBox().expandXyz(3F);

					RayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
					RayTraceResult extraRayTraceResult = extraAxisalignedbb.calculateIntercept(vec3d, vec3d1);
	
					if (extraRayTraceResult != null)
					{
						double d1 = vec3d.distanceTo(extraRayTraceResult.hitVec);

						if (d1 < d || d == 0.0D)
						{
							extraEntity = entity1;
							d = d1;
						}
					}

					if (movingobjectposition1 != null)
					{
						double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d || d == 0.0D)
						{
							entity = entity1;
							d = d1;
						}
					}
				}
			}

			for (int l = (int) (this.posX - 3); l <= this.posX + 3; l++)
			{
				for (int i1 = (int) (this.posY - 3); i1 <= this.posY + 3; i1++)
				{
					for (int j1 = (int) (this.posZ - 3); j1 <= this.posZ + 3; j1++)
					{
						BlockPos prevBlockPos = new BlockPos(l, i1, j1);
						if (this.worldObj.getBlockState(prevBlockPos).getBlock() instanceof BlockFlower && this.thrower != null && this.thrower instanceof EntityPlayer)
						{
							IBlockState prevBlockState = this.worldObj.getBlockState(prevBlockPos);
							Block prevBlock = prevBlockState.getBlock();

							if (!this.harvestBlockBans.contains(prevBlock))
							{
								prevBlock.harvestBlock(this.thrower.worldObj, (EntityPlayer) this.thrower, prevBlockPos, prevBlockState, this.worldObj.getTileEntity(prevBlockPos), new ItemStack(ItemsAether.notch_hammer));

								prevBlock.removedByPlayer(prevBlockState, this.thrower.worldObj, prevBlockPos, (EntityPlayer) this.thrower, false);
							}

							continue;
						}
					}
				}
			}

			if (entity != null)
			{
				movingobjectposition = new RayTraceResult(entity);
			}
			
			if (extraEntity != null)
			{
				rayTraceResult = new RayTraceResult(extraEntity);
			}
		}

		if (rayTraceResult != null && rayTraceResult.typeOfHit == Type.ENTITY)
		{
			if (rayTraceResult.entityHit instanceof EntitySaddleMount && ((EntitySaddleMount)rayTraceResult.entityHit).getSaddled())
			{
				
			}
			else if (rayTraceResult != null && rayTraceResult.entityHit != this.thrower)
			{
				rayTraceResult.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.thrower), 5);
				rayTraceResult.entityHit.addVelocity(this.motionX, 0.6D, this.motionZ);
			}
		}

		if (movingobjectposition != null)
		{
			if (movingobjectposition.typeOfHit == Type.ENTITY)
			{
				if (movingobjectposition.entityHit instanceof EntitySaddleMount && ((EntitySaddleMount)movingobjectposition.entityHit).getSaddled())
				{
					
				}
				else if (movingobjectposition.entityHit != this.thrower)
				{
					movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.thrower), 5);
					movingobjectposition.entityHit.addVelocity(this.motionX, 0.6D, this.motionZ);
				}
			}

			for (int l = (int) (this.posX - 3); l <= this.posX + 3; l++)
			{
				for (int i1 = (int) (this.posY - 3); i1 <= this.posY + 3; i1++)
				{
					for (int j1 = (int) (this.posZ - 3); j1 <= this.posZ + 3; j1++)
					{
						BlockPos prevBlockPos = new BlockPos(l, i1, j1);
						if (this.worldObj.getBlockState(prevBlockPos).getBlock() instanceof BlockFlower && this.thrower != null && this.thrower instanceof EntityPlayer)
						{
							IBlockState prevBlockState = this.worldObj.getBlockState(prevBlockPos);
							Block prevBlock = prevBlockState.getBlock();

							if (!this.harvestBlockBans.contains(prevBlock))
							{
								prevBlock.harvestBlock(this.thrower.worldObj, (EntityPlayer) this.thrower, prevBlockPos, prevBlockState, this.worldObj.getTileEntity(prevBlockPos), null);

								prevBlock.removedByPlayer(prevBlockState, this.thrower.worldObj, prevBlockPos, (EntityPlayer) this.thrower, false);
							}

							continue;
						}
					}
				}
			}

			for (int j = 0; j < 8; j++)
			{
				this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
				this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
				this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}

			this.setDead();
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) ((Math.atan2(this.motionX, this.motionZ) * 180D) / 3.1415927410125732D);

		for (this.rotationPitch = (float) ((Math.atan2(this.motionY, f) * 180D) / 3.1415927410125732D); this.rotationPitch - this.prevRotationPitch < -180F; this.prevRotationPitch -= 360F) { }

		for (; this.rotationPitch - this.prevRotationPitch >= 180F; this.prevRotationPitch += 360F) { }

		for (; this.rotationYaw - this.prevRotationYaw < -180F; this.prevRotationYaw -= 360F) { }

		for (; this.rotationYaw - this.prevRotationYaw >= 180F; this.prevRotationYaw += 360F) { }

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

		float f1 = 0.99F;

		if (this.isInWater())
		{
			for (int k = 0; k < 4; k++)
			{
				this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
			}

			f1 = 0.8F;
		}

		this.motionX *= f1;
		this.motionY *= f1;
		this.motionZ *= f1;

		this.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		nbttagcompound.setInteger("xTile", (short) this.posTile.getX());
		nbttagcompound.setInteger("yTile", (short) this.posTile.getY());
		nbttagcompound.setInteger("zTile", (short) this.posTile.getZ());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		int xTile = nbttagcompound.getInteger("xTile");
		int yTile = nbttagcompound.getInteger("yTile");
		int zTile = nbttagcompound.getInteger("zTile");
		this.posTile = new BlockPos(xTile, yTile, zTile);
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityplayer)
	{

	}

	@Override
	public void setThrowableHeading(double var1, double var3, double var5, float var7, float var8) { }

}