package com.legacy.aether.entities.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.entities.passive.EntityAetherAnimal;
import com.legacy.aether.player.PlayerAether;

public abstract class EntityMountable extends EntityAetherAnimal
{

	public static final DataParameter<Boolean> RIDER_SNEAKING = EntityDataManager.<Boolean>createKey(EntityMountable.class, DataSerializers.BOOLEAN);

	protected float jumpPower;

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

		if (this.isBeingRidden() && this.isRiding())
		{
			for (Entity entity : this.getPassengers())
			{
				entity.dismountRidingEntity();
			}
		}
	}

	public void updateRider()
	{
		if (this.world.isRemote)
		{
			return;
		}

		if (this.isBeingRidden())
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
    public void travel(float strafe, float vertical, float forward)
	{
		Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			this.prevRotationYaw = this.rotationYaw = player.rotationYaw;
			this.prevRotationPitch = this.rotationPitch = player.rotationPitch;

			this.rotationYawHead = player.rotationYawHead;

			strafe = player.moveStrafing;
			vertical = player.moveVertical;
			forward = player.moveForward;

			if (forward <= 0.0F)
			{
				forward *= 0.25F;
			}

	        double d01 = player.posX - this.posX;
	        double d2 = player.posZ - this.posZ;

	        float f = (float)(MathHelper.atan2(d2, d01) * (180D / Math.PI)) - 90.0F;

			if (player.moveStrafing != 0.0F && player.world.isRemote)
			{
		        this.rotationYaw = this.updateRotation(this.rotationYaw, f, 40.0F);
			}

			if (AetherAPI.getInstance().get(player).isJumping())
			{
				onMountedJump(strafe, forward);
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

				if (!this.world.isRemote)
				{
					this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
				}
			}

			this.motionX *= 0.35F;
			this.motionZ *= 0.35F;

			this.stepHeight = 1.0F;

			if (!this.world.isRemote)
			{
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.6F;
				super.travel(strafe, vertical, forward);
			}

			if (this.onGround)
			{
				this.jumpPower = 0.0F;
				this.setMountJumping(false);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d0 = this.posX - this.prevPosX;
			double d1 = this.posZ - this.prevPosZ;
			float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;

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
			super.travel(strafe, vertical, forward);
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