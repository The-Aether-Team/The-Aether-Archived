package com.legacy.aether.entities.passive.mountable;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.util.EntityMountable;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;

public class EntitySwet extends EntityMountable
{

	public static final DataParameter<Boolean> FRIENDLY = EntityDataManager.<Boolean>createKey(EntitySwet.class, DataSerializers.BOOLEAN);

	public static final DataParameter<Integer> SWET_TYPE = EntityDataManager.<Integer>createKey(EntitySwet.class, DataSerializers.VARINT);

	public static final DataParameter<Integer> ATTACK_COOLDOWN = EntityDataManager.<Integer>createKey(EntitySwet.class, DataSerializers.VARINT);

	private int slimeJumpDelay = 0;

	public int ticker;

	public int flutter;

	public int hops;

	public boolean kickoff;

	public EntitySwet(World world)
	{
		super(world);

		this.hops = 0;
		this.flutter = 0;
		this.ticker = 0;
		this.slimeJumpDelay = this.rand.nextInt(20) + 10;

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		this.setHealth(25.0F);

		this.setType(this.rand.nextInt(2));
		this.setSize(0.8F, 0.8F);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.5D);
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		this.dataManager.register(FRIENDLY, false);
		this.dataManager.register(SWET_TYPE, 0);
		this.dataManager.register(ATTACK_COOLDOWN, 100);
	}

	@Override
	public void updateRidden()
	{
		super.updateRidden();

		if (this.hasPrey() && this.kickoff)
		{
			this.setAttackCooldown(50);
			this.getPassengers().get(0).dismountRidingEntity();
			this.kickoff = false;
		}
	}

	@Override
	public void onUpdate()
	{
		if (this.getAttackTarget() != null)
		{
			for (int i = 0; i < 3; i++)
			{
				double d = (float) this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
				double d1 = (float) this.posY + this.height;
				double d2 = (float) this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
				this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, d, d1 - 0.25D, d2, 0.0D, 0.0D, 0.0D);
			}
		}

		super.onUpdate();

		if (this.getAttackCooldown() != 0)
		{
			this.setAttackCooldown(this.getAttackCooldown() - 1);
		}

		if (this.getAttackCooldown() == 0 && !this.hasPrey() && !this.isFriendly())
		{
			List<?> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());

			if (0 < list.size())
			{
				Entity entity = (Entity) list.get(0);

				if (entity instanceof EntityLivingBase && !(entity instanceof EntitySwet))
				{
					this.capturePrey((EntityLivingBase) entity);
				}
			}
		}

		if (this.handleWaterMovement())
		{
			this.dissolve();
		}
	}

	@Override
	protected boolean canDespawn()
	{
		return !this.isFriendly();
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{
		if (this.isFriendly())
		{
			return;
		}

		super.fall(distance, damageMultiplier);

		if (this.hops >= 3 && this.getHealth() > 0)
		{
			this.dissolve();
		}
	}

	@Override
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio)
	{
		if (!this.hasPrey())
		{
			super.knockBack(entityIn, strength, xRatio, zRatio);
		}
	}

	@Override
	public void updateRider()
	{
		if (this.isFriendly() && this.isBeingRidden())
		{
			Entity passenger = this.getPassengers().get(0);

			if (passenger.isSneaking())
			{
				if (this.onGround)
				{
					this.setAttackCooldown(50);
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

	public void dissolve()
	{
		for (int i = 0; i < 50; i++)
		{
			float f = this.rand.nextFloat() * 3.141593F * 2.0F;
			float f1 = this.rand.nextFloat() * 0.5F + 0.25F;
			float f2 = MathHelper.sin(f) * f1;
			float f3 = MathHelper.cos(f) * f1;

			this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (double) f2, this.getEntityBoundingBox().minY + 1.25D, this.posZ + (double) f3, (double) f2 * 1.5D + this.motionX, 4D, (double) f3 * 1.5D + this.motionZ);
		}

		this.setDead();
	}

	public void capturePrey(EntityLivingBase entity)
	{
		this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.HOSTILE, 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);

		this.prevPosX = this.posX = entity.posX;
		this.prevPosY = this.posY = entity.posY + 0.0099999997764825821D;
		this.prevPosZ = this.posZ = entity.posZ;
		this.prevRotationYaw = this.rotationYaw = entity.rotationYaw;
		this.prevRotationPitch = this.rotationPitch = entity.rotationPitch;
		this.motionX = entity.motionX;
		this.motionY = entity.motionY;
		this.motionZ = entity.motionZ;

		this.setAttackCooldown(50);
		this.setSize(entity.width, entity.height);
		this.setPosition(this.posX, this.posY, this.posZ);

		entity.startRiding(this);

		this.rotationYaw = this.rand.nextFloat() * 360F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		Entity entity = source.getImmediateSource();

		if (this.hops == 3 && entity == null && this.getHealth() >= 1.0F)
		{
			this.setHealth(1);
		}

		boolean flag = super.attackEntityFrom(source, amount);

		if (flag && this.hasPrey())
		{
			EntityLivingBase rider = (EntityLivingBase) this.getPassengers().get(0);

			if (entity != null && this.isPrey(entity))
			{
				if (this.rand.nextInt(3) == 0)
				{
					this.kickoff = true;
				}
			}
			else
			{
				rider.attackEntityFrom(DamageSource.causeMobDamage(this), amount);

				if (this.getHealth() <= 0.0F)
				{
					this.kickoff = true;
				}
			}
		}

		if (flag && this.getHealth() <= 0.0F)
		{
			this.dissolve();
		}
		else if (flag && entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityliving = (EntityLivingBase) entity;

			if (entityliving.getHealth() >= 0.0F && this.isPrey(entityliving))
			{
				this.setAttackTarget((EntityLivingBase) entity);
				this.faceEntity(entity, 180F, 180F);
				this.kickoff = true;
			}
		}

		if (this.isFriendly())
		{
			this.setAttackTarget(null);
		}

		return flag;
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		this.addGrowth(1);

		if (this.isFriendly() && !this.hasPrey())
		{
			return;
		}

		if (this.getAttackTarget() == null && !this.hasPrey() && this.getHealth() >= 0.0F)
		{
			if (this.onGround && this.slimeJumpDelay-- <= 0)
			{
				this.slimeJumpDelay = this.rand.nextInt(20) + 10;

				this.isJumping = true;

				this.motionY = 0.34999999403953552D;

				this.playSound(SoundEvents.ENTITY_SLIME_JUMP, 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

				this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
				this.moveForward = 16.0F;
			}
			else
			{
				this.isJumping = false;

				if (this.onGround)
				{
					this.moveStrafing = this.moveForward = 0.0F;
				}
			}
		}

		if (!this.onGround && this.isJumping)
		{
			this.isJumping = false;
		}

		if (this.getAttackTarget() != null && !this.hasPrey() && this.getHealth() >= 0.0F)
		{
			if (!this.canEntityBeSeen(this.getAttackTarget()) || ((this.getAttackTarget() instanceof EntityPlayer) && ((EntityPlayer)this.getAttackTarget()).capabilities.isCreativeMode))
			{
				this.setAttackTarget(null);
				return;
			}

			this.faceEntity(this.getAttackTarget(), 10F, 10F);

			if (this.onGround && this.slimeJumpDelay-- <= 0)
			{
				this.slimeJumpDelay = this.rand.nextInt(20) + 10;

				this.isJumping = true;

				this.motionY = 0.34999999403953552D;

				this.playSound(SoundEvents.ENTITY_SLIME_JUMP, 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

				this.moveStrafing = 1.0F;
				this.moveForward = 16.0F;
			}
			else
			{
				this.isJumping = false;

				if (this.onGround)
				{
					this.moveStrafing = this.moveForward = 0.0F;
				}
			}
		}

		if (this.getAttackTarget() != null && this.getAttackTarget().isDead)
		{
			this.setAttackTarget(null);
		}

		if (!this.onGround && this.motionY < 0.05000000074505806D && this.flutter > 0)
		{
			this.motionY += 0.070000000298023224D;
			this.flutter--;
		}

		if (this.ticker < 4)
		{
			this.ticker++;
		}
		else
		{
			if (this.onGround && !this.hasPrey() && this.hops != 0 && this.hops != 3)
			{
				this.hops = 0;
			}

			if (this.getAttackTarget() == null && !this.hasPrey())
			{
				Entity entity = this.getPrey();
				if (entity != null)
				{
					this.setAttackTarget((EntityLivingBase) entity);
				}
			}
			else if (this.getAttackTarget() != null && !this.hasPrey())
			{
				if (this.getDistance(this.getAttackTarget()) <= 9F)
				{
					if (this.onGround && this.canEntityBeSeen(this.getAttackTarget()))
					{
						this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.HOSTILE, 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);

						this.flutter = 10;
						this.isJumping = true;
						this.moveForward = 1.0F;
						this.rotationYaw += 5F * (this.rand.nextFloat() - this.rand.nextFloat());
					}
				}
				else
				{
					this.setAttackTarget(null);
					this.isJumping = false;
					this.moveForward = 0.0F;
				}
			}
			else if (this.hasPrey() && this.onGround)
			{
				this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.HOSTILE, 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);

				++this.hops;

				this.flutter = 5;
				this.onGround = false;
				this.rotationYaw += 20F * (this.rand.nextFloat() - this.rand.nextFloat());

				if (this.hops == 0)
				{
					this.motionY = 0.34999999403953552D;
					this.moveForward = 0.8F;
				}
				else if (this.hops == 1)
				{
					this.motionY = 0.44999998807907104D;
					this.moveForward = 0.9F;
				}
				else if (this.hops == 2)
				{
					this.motionY = 1.25D;
					this.moveForward = 1.25F;
				}
			}

			this.ticker = 0;
		}

		if (this.onGround && this.hops >= 3)
		{
			this.dissolve();
		}
	}

	@Override
    public void travel(float strafe, float vertical, float forward)
	{
		if (!this.hasPrey())
		{
			super.travel(strafe, vertical, forward);

			return;
		}

		EntityLivingBase prey = this.getPrey();

		if (prey instanceof EntityPlayer)
		{
			EntityPlayer rider = (EntityPlayer) prey;
			PlayerAether aetherRider = PlayerAether.get(rider);

			if (aetherRider == null)
			{
				return;
			}

			this.setFriendly(aetherRider.wearingAccessory(ItemsAether.swet_cape) ? true : false);

			if (this.isFriendly())
			{
				this.prevRotationYaw = this.rotationYaw = rider.rotationYaw;
				this.rotationPitch = rider.rotationPitch * 0.5F;

				this.setRotation(this.rotationYaw, this.rotationPitch);

				this.rotationYawHead = this.renderYawOffset = this.rotationYaw;

				if (this.onGround && this.slimeJumpDelay-- <= 0)
				{
					strafe = rider.moveStrafing * 2.0F;
					vertical = rider.moveVertical * 2.0F;
					forward = rider.moveForward * 2.0F;

					if (strafe != 0.0f || forward != 0.0f)
					{
						this.jump();
						this.onGround = false;
						this.motionY = 0.7f;
						this.slimeJumpDelay = this.rand.nextInt(4);
					}

					if (aetherRider.isJumping())
					{
						this.jump();
						this.onGround = false;
						this.motionY = 1.0f;
						this.slimeJumpDelay = this.rand.nextInt(15);
					}

					int rotate = MathHelper.floor(rider.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

					double x = Math.cos(this.getLookVec().x);
					double z = Math.cos(this.getLookVec().z);

					if (forward > 0.0F)
					{
						this.motionX = this.getLookVec().x / 2;
						this.motionZ = this.getLookVec().z / 2;
					}

					if (forward < 0.0F)
					{
						this.motionX = -this.getLookVec().x / 2;
						this.motionZ = -this.getLookVec().z / 2;
					}

					if (strafe > 0.0F)
					{
						if (rotate <= 1)
						{
							this.motionX = x / 2;
							this.motionZ = z / 2;
						}
						else
						{
							this.motionX = -x / 2;
							this.motionZ = -z / 2;
						}
					}

					if (strafe < 0.0F)
					{
						if (rotate <= 1)
						{
							this.motionX =+ -x / 2;
							this.motionZ =+ -z / 2;
						}
						else
						{
							this.motionX =+ x / 2;
							this.motionZ =+ z / 2;
						}
					}
				}
				else if (this.onGround)
				{
					return;
				}

				if (forward <= 0.0F)
				{
					forward *= 0.25F;
				}

				this.stepHeight = 1.0F;
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

				if (!this.world.isRemote)
				{
					this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());

					super.travel(strafe, vertical, forward);
				}
			}
			else
			{
				this.stepHeight = 0.5F;
				this.jumpMovementFactor = 0.02F;
				super.travel(this.moveForward, this.moveVertical, this.moveStrafing);
			}
		}
		else
		{
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.travel(this.moveForward, this.moveVertical, this.moveStrafing);
		}
	}

	@Override
    protected void jump()
    {
    	super.jump();

		this.playSound(SoundEvents.ENTITY_SLIME_JUMP, 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
    }

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setInteger("Hops", this.hops);
		compound.setInteger("Flutter", this.flutter);

		compound.setBoolean("Friendly", this.isFriendly());
		compound.setInteger("SwetType", this.getType());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.hops = compound.getInteger("Hops");
		this.flutter = compound.getInteger("Flutter");

		this.setFriendly(compound.getBoolean("Friendly"));
		this.setType(compound.getInteger("SwetType"));
	}

	public boolean isFriendly()
	{
		return this.dataManager.get(FRIENDLY).booleanValue();
	}

	private void setFriendly(boolean friendly)
	{
		this.dataManager.set(FRIENDLY, friendly);
	}

	public int getType()
	{
		return this.dataManager.get(SWET_TYPE).intValue();
	}

	private void setType(int type)
	{
		this.dataManager.set(SWET_TYPE, type);
	}

	public int getAttackCooldown()
	{
		return this.dataManager.get(ATTACK_COOLDOWN).intValue();
	}

	private void setAttackCooldown(int timeLeft)
	{
		this.dataManager.set(ATTACK_COOLDOWN, timeLeft);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_SLIME_SQUISH;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_SLIME_DEATH;
	}

	@Override
    public SoundCategory getSoundCategory()
    {
        return SoundCategory.HOSTILE;
    }

	@Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		if (!this.world.isRemote)
		{
			if (!this.isFriendly())
			{
				return true;
			}

			if (!this.hasPrey())
			{
				this.capturePrey(player);
			}
			else
			{
				if (this.isPrey(player))
				{
					player.dismountRidingEntity();
				}
			}
		}

		return true;
	}

	protected EntityLivingBase getPrey()
	{
		List<?> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(6D, 6D, 6D));

		for (int i = 0; i < list.size(); i++)
		{
			Entity entity = (Entity) list.get(i);

			if ((entity instanceof EntityLivingBase) && !(entity instanceof EntitySwet) && (this.isFriendly() ? !(entity instanceof EntityPlayer) : !(entity instanceof EntityMob)))
			{
				return (EntityLivingBase) entity;
			}
		}

		return null;
	}

	public boolean isPrey(Entity entity)
	{
		if (!(entity instanceof EntityLivingBase))
		{
			return false;
		}

		if (!this.hasPrey())
		{
			return false;
		}

		return this.getPassengers().get(0) == entity;
	}

	public boolean hasPrey()
	{
		return !this.getPassengers().isEmpty() && this.getPassengers().get(0) != null;
	}

	@Override
	protected void dropFewItems(boolean var1, int var2)
	{
		ItemStack droppedItem = new ItemStack(this.getType() == 1 ? BlocksAether.aercloud : Blocks.GLOWSTONE, 1, this.getType() == 1 ? 1 : 0);

		this.entityDropItem(droppedItem, 0F);

		if (this.getType() == 1);
		{
			this.dropItem(ItemsAether.swetty_ball, 1);
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return null;
	}

}