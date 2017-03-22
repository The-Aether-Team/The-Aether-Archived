package com.legacy.aether.common.entities.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.passive.EntityAetherAnimal;
import com.legacy.aether.common.entities.passive.mountable.EntitySwet;
import com.legacy.aether.common.player.PlayerAether;

public abstract class EntityMountable extends EntityAetherAnimal
{

	public static final DataParameter<Boolean> RIDER_SNEAKING = EntityDataManager.<Boolean>createKey(EntityMountable.class, DataSerializers.BOOLEAN);

	protected float jumpPower;

	protected int field_110285_bP;

	protected boolean mountJumping;

	protected boolean playStepSound = false;

	protected boolean canJumpMidAir = false;

	public EntityMountable(World world)
	{
		super(world);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(RIDER_SNEAKING, false);
	}

	@Override
    public boolean canRiderInteract()
    {
        return true;
    }

	@Override
    public boolean shouldDismountInWater(Entity rider)
	{
        return false;
    }

	public boolean isRiderSneaking()
	{
		return this.dataManager.get(RIDER_SNEAKING);
	}

	public void setRiderSneaking(boolean riderSneaking)
	{
		this.dataManager.set(RIDER_SNEAKING, riderSneaking);
	}

	@Override
	public void onUpdate()
	{
		this.updateRider();
		super.onUpdate();
	}

	public void updateRider()
	{
		if ((this instanceof EntitySwet && ((EntitySwet)this).isFriendly() && !this.getPassengers().isEmpty() || !this.getPassengers().isEmpty()) && !this.worldObj.isRemote)
		{
			Entity passenger = this.getPassengers().get(0);

			if (passenger.isSneaking())
			{
				if (this.onGround)
				{
					passenger.setSneaking(false);
					passenger.dismountRidingEntity();
					
					return;
				}
				
				this.setRiderSneaking(true);
			}
			else
			{
				this.setRiderSneaking(false);
			}

			passenger.setSneaking(false);
		}
	}

    private float updateRotation(float angle, float targetAngle, float maxIncrease)
    {
        float f = MathHelper.wrapDegrees(targetAngle - angle);

        if (f > maxIncrease)
        {
            f = maxIncrease;
        }

        if (f < -maxIncrease)
        {
            f = -maxIncrease;
        }

        return angle + f;
    }

	@Override
	public void moveEntityWithHeading(float par1, float par2)
	{
		Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			this.prevRotationYaw = this.rotationYaw = player.rotationYaw;
			this.prevRotationPitch = this.rotationPitch = player.rotationPitch;

			this.rotationYawHead = player.rotationYawHead;

			par1 = player.moveStrafing;
			par2 = player.moveForward;

			if (par2 <= 0.0F)
			{
				par2 *= 0.25F;
				this.field_110285_bP = 0;
			}

	        double d01 = player.posX - this.posX;
	        double d2 = player.posZ - this.posZ;

	        float f = (float)(MathHelper.atan2(d2, d01) * (180D / Math.PI)) - 90.0F;

			if (player.moveStrafing != 0.0F && player.worldObj.isRemote)
			{
		        this.rotationYaw = this.updateRotation(this.rotationYaw, f, 40.0F);
			}

			if (PlayerAether.get(player).isJumping())
			{
				onMountedJump(par1, par2);
			}

			if (this.jumpPower > 0.0F && !this.isMountJumping() && (this.onGround || this.canJumpMidAir))
			{
				this.motionY = this.getMountJumpStrength() * (double) this.jumpPower;

				if (this.isPotionActive(MobEffects.JUMP_BOOST))
				{
					this.motionY += (double) ((float) (this.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
				}

				this.setMountJumping(true);
				this.isAirBorne = true;

				this.jumpPower = 0.0F;

				if (!this.worldObj.isRemote)
				{
					this.moveEntity(this.motionX, this.motionY, this.motionZ);
				}
			}

			this.motionX *= 0.35F;
			this.motionZ *= 0.35F;

			this.stepHeight = 1.0F;

			if (!this.worldObj.isRemote)
			{
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.6F;
				super.moveEntityWithHeading(par1, par2);
			}

			if (this.onGround)
			{
				this.jumpPower = 0.0F;
				this.setMountJumping(false);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d0 = this.posX - this.prevPosX;
			double d1 = this.posZ - this.prevPosZ;
			float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

			if (f4 > 1.0F)
			{
				f4 = 1.0F;
			}

			this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(par1, par2);
		}
	}

	@Override
    public float getAIMoveSpeed()
    {
        return this.getMountedMoveSpeed();
    }

	public float getMountedMoveSpeed()
	{
		return 0.15F;
	}

	@Override
    protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
    {
    	
    }

	protected double getMountJumpStrength()
	{
		return 1.0D;
	}

	protected void setMountJumping(boolean mountJumping)
	{
		this.mountJumping = mountJumping;
	}

	protected boolean isMountJumping()
	{
		return this.mountJumping;
	}

	public void onMountedJump(float par1, float par2)
	{
		this.jumpPower = 0.4F;
	}

}