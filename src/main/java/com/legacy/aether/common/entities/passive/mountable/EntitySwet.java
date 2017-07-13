package com.legacy.aether.common.entities.passive.mountable;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
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

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.entities.util.EntityMountable;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.player.PlayerAether;

public class EntitySwet extends EntityMountable
{

	public static final DataParameter<Boolean> FRIENDLY = EntityDataManager.<Boolean>createKey(EntitySwet.class, DataSerializers.BOOLEAN);

	public static final DataParameter<Integer> SWET_TYPE = EntityDataManager.<Integer>createKey(EntitySwet.class, DataSerializers.VARINT);

	private int slimeJumpDelay = 0;

	public int ticker;

	public int flutter;

	public int hops;

	public boolean kickoff;

	public EntitySwet(World world)
	{
		super(world);

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
		this.setHealth(25);

		this.setType(this.rand.nextInt(2));
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.5F);
		this.setSize(0.8F, 0.8F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.hops = 0;
		this.flutter = 0;
		this.ticker = 0;
		this.slimeJumpDelay = this.rand.nextInt(20) + 10;
	}

    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
    	return type == EnumCreatureType.MONSTER;
    }

	@Override
	public void updateRidden()
	{
		super.updateRidden();

		if (!this.getPassengers().isEmpty() && this.kickoff)
		{
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
				this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d, d1 - 0.25D, d2, 0.0D, 0.0D, 0.0D);
			}
		}

		super.onUpdate();

		if (this.getPassengers().isEmpty() && !this.isFriendly())
		{
			List<?> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.5D, 0.75D, 0.5D));

			int j = 0;

			if (j < list.size())
			{
				Entity entity = (Entity) list.get(j);

				if (entity instanceof EntityLivingBase && !(entity instanceof EntitySwet))
				this.capturePrey(entity);
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
	public void knockBack(Entity entity, float i, double d, double d1)
	{
		if (!this.getPassengers().isEmpty() && entity == this.getPassengers().get(0))
		{
			return;
		}
		else
		{
			super.knockBack(entity, i, d, d1);
			return;
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

	public void capturePrey(Entity entity)
	{
		this.splorch();

		this.prevPosX = this.posX = entity.posX;
		this.prevPosY = this.posY = entity.posY + 0.0099999997764825821D;
		this.prevPosZ = this.posZ = entity.posZ;
		this.prevRotationYaw = this.rotationYaw = entity.rotationYaw;
		this.prevRotationPitch = this.rotationPitch = entity.rotationPitch;
		this.motionX = entity.motionX;
		this.motionY = entity.motionY;
		this.motionZ = entity.motionZ;

		this.setSize(entity.width, entity.height);
		this.setPosition(this.posX, this.posY, this.posZ);

		entity.startRiding(this);

		this.rotationYaw = this.rand.nextFloat() * 360F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float i)
	{
		Entity entity = damageSource.getEntity();

		if (this.hops == 3 && entity == null && this.getHealth() > 1)
		{
			this.setHealth(1);
		}

		boolean flag = super.attackEntityFrom(damageSource, i);

		if (flag && !this.getPassengers().isEmpty() && (this.getPassengers().get(0) instanceof EntityLivingBase))
		{
			EntityLivingBase rider = (EntityLivingBase) this.getPassengers().get(0);

			if (entity != null && rider == entity)
			{
				if (this.rand.nextInt(3) == 0)
				{
					this.kickoff = true;
				}
			}
			else
			{
				rider.attackEntityFrom(DamageSource.causeMobDamage(this), i);

				if (this.getHealth() <= 0)
				{
					this.kickoff = true;
				}
			}
		}

		if (flag && this.getHealth() <= 0)
		{
			this.dissolve();
		}
		else if (flag && (entity instanceof EntityLivingBase))
		{
			EntityLivingBase entityliving = (EntityLivingBase) entity;

			if (entityliving.getHealth() > 0 && (this.getPassengers().isEmpty() || !this.getPassengers().isEmpty() && entityliving != this.getPassengers().get(0)))
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
		this.entityAge++; 

		if (this.isFriendly() && !this.getPassengers().isEmpty())
		{
			return;
		}

		if (this.getAttackTarget() == null && this.getPassengers().isEmpty() && this.getHealth() > 0)
		{
			if (this.onGround && this.slimeJumpDelay-- <= 0)
			{
				this.slimeJumpDelay = this.getJumpDelay();

				this.isJumping = true;

				this.motionY = 0.34999999403953552D;

				this.playSound(SoundEvents.ENTITY_SLIME_JUMP, 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

				this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
				this.moveForward = (float) (1 * 16);
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

		if (this.getAttackTarget() != null && this.getPassengers().isEmpty() && this.getHealth() > 0)
		{
			this.faceEntity(this.getAttackTarget(), 10F, 10F);
			
			if (this.onGround && this.slimeJumpDelay-- <= 0)
			{
				this.slimeJumpDelay = this.getJumpDelay();

				this.isJumping = true;

				this.motionY = 0.34999999403953552D;

				this.playSound(SoundEvents.ENTITY_SLIME_JUMP, 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

				this.moveStrafing = 1.0F;
				this.moveForward = (float) (1 * 16);
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
			if (this.onGround && this.getPassengers().isEmpty() && this.hops != 0 && this.hops != 3)
			{
				this.hops = 0;
			}

			if (this.getAttackTarget() == null && this.getPassengers().isEmpty())
			{
				Entity entity = this.getPrey();
				if (entity != null)
				{
					this.setAttackTarget((EntityLivingBase) entity);
				}
			}
			else if (this.getAttackTarget() != null && this.getPassengers().isEmpty())
			{
				if (this.getDistanceToEntity(this.getAttackTarget()) <= 9F)
				{
					if (this.onGround && this.canEntityBeSeen(this.getAttackTarget()))
					{
						this.splotch();
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
			else if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) != null && this.onGround)
			{
				if (this.hops == 0)
				{
					this.splotch();
					this.onGround = false;
					this.motionY = 0.34999999403953552D;
					this.moveForward = 0.8F;
					this.hops = 1;
					this.flutter = 5;
					this.rotationYaw += 20F * (this.rand.nextFloat() - this.rand.nextFloat());
				}
				else if (this.hops == 1)
				{
					this.splotch();
					this.onGround = false;
					this.motionY = 0.44999998807907104D;
					this.moveForward = 0.9F;
					this.hops = 2;
					this.flutter = 5;
					this.rotationYaw += 20F * (this.rand.nextFloat() - this.rand.nextFloat());
				}
				else if (this.hops == 2)
				{
					this.splotch();
					this.onGround = false;
					this.motionY = 1.25D;
					this.moveForward = 1.25F;
					this.hops = 3;
					this.flutter = 5;
					this.rotationYaw += 20F * (this.rand.nextFloat() - this.rand.nextFloat());
				}
			}

			this.ticker = 0;
		}

		if (this.onGround && this.hops >= 3)
		{
			this.dissolve();
		}
	}

	protected int getJumpDelay()
	{
		return this.rand.nextInt(20) + 10;
	}

	public void moveEntityWithHeading(float par1, float par2)
	{
		EntityPlayer rider = !this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof EntityPlayer ? (EntityPlayer) this.getPassengers().get(0) : null;

		if (rider != null)
		{
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
					par1 = rider.moveStrafing * 2F;
					par2 = rider.moveForward * 2;

					if (par1 != 0.0f || par2 != 0.0f)
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

					double x = Math.cos(this.getLookVec().xCoord);
					double z = Math.cos(this.getLookVec().zCoord);

					if (par2 > 0.0F)
					{
						this.motionX = this.getLookVec().xCoord / 2;
						this.motionZ = this.getLookVec().zCoord / 2;
					}

					if (par2 < 0.0F)
					{
						this.motionX = -this.getLookVec().xCoord / 2;
						this.motionZ = -this.getLookVec().zCoord / 2;
					}

					if (par1 > 0.0F)
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

					if (par1 < 0.0F)
					{
						if (rotate <= 1)
						{
							this.motionX = +-x / 2;
							this.motionZ = +-z / 2;
						}
						else
						{
							this.motionX = +x / 2;
							this.motionZ = +z / 2;
						}
					}
				}
				else if (this.onGround)
				{
					return;
				}

				if (par2 <= 0.0F)
				{
					par2 *= 0.25F;
					this.field_110285_bP = 0;
				}

				this.stepHeight = 1.0F;
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

				if (!this.world.isRemote)
				{
					this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
					super.moveEntityWithHeading(par1, par2);
				}
			}
			else
			{
				this.stepHeight = 0.5F;
				this.jumpMovementFactor = 0.02F;
				super.moveEntityWithHeading(this.moveForward, this.moveStrafing);
			}
		}
		else
		{
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(this.moveForward, this.moveStrafing);
		}
	}

	@Override
    protected void jump()
    {
    	super.jump();

		this.playSound(SoundEvents.ENTITY_SLIME_JUMP, 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
    }

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);

		nbttagcompound.setShort("Hops", (short) this.hops);
		nbttagcompound.setShort("Flutter", (short) this.flutter);
		nbttagcompound.setBoolean("Friendly", this.isFriendly());
		nbttagcompound.setInteger("SwetType", this.getType());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);

		this.hops = nbttagcompound.getShort("Hops");
		this.flutter = nbttagcompound.getShort("Flutter");

		this.setFriendly(nbttagcompound.getBoolean("Friendly"));
		this.setType(nbttagcompound.getInteger("SwetType"));
	}

	public boolean isFriendly()
	{
		Object obj = this.dataManager.get(FRIENDLY);

		if (!(obj instanceof Boolean))
		{
			return false;
		}

		return (Boolean) obj;
	}

	private void setFriendly(boolean friendly)
	{
		this.dataManager.set(FRIENDLY, friendly);
	}

	public int getType()
	{
		return this.dataManager.get(SWET_TYPE);
	}

	private void setType(int type)
	{
		this.dataManager.set(SWET_TYPE, type);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(FRIENDLY, false);
		this.dataManager.register(SWET_TYPE, 0);
	}

	public void splorch()
	{
		this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SLIME_ATTACK, SoundCategory.HOSTILE, 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);
	}

	public void splotch()
	{
		this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.HOSTILE, 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);
	}

	@Override
	protected SoundEvent getHurtSound()
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
	public void applyEntityCollision(Entity entity)
	{
		if (this.hops == 0 && !this.isFriendly() && this.getPassengers().isEmpty() && this.getAttackTarget() != null && entity != null && entity == this.getAttackTarget() && (entity.getRidingEntity() == null || !(entity.getRidingEntity() instanceof EntitySwet)))
		{
			this.capturePrey(entity);
		}

		super.applyEntityCollision(entity);
	}

	@Override
    public boolean processInteract(EntityPlayer entityplayer, EnumHand hand)
	{
		if (!this.world.isRemote)
		{
			if (!this.isFriendly())
			{
				return true;
			}

			if (this.getPassengers().isEmpty())
			{
				this.capturePrey(entityplayer);
			}
			else
			{
				if (this.getPassengers().get(0) == entityplayer)
				{
					entityplayer.dismountRidingEntity();
				}
			}
		}

		return true;
	}

	protected Entity getPrey()
	{
		List<?> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(6D, 6D, 6D));
		for (int i = 0; i < list.size(); i++)
		{
			Entity entity = (Entity) list.get(i);
			if ((entity instanceof EntityLivingBase) && !(entity instanceof EntitySwet) && (this.isFriendly() ? !(entity instanceof EntityPlayer) : !(entity instanceof EntityMob)))
			{
				return entity;
			}
		}

		return null;
	}

	@Override
	protected void dropFewItems(boolean var1, int var2)
	{
		ItemStack droppedItem = new ItemStack(this.getType() == 1 ? BlocksAether.aercloud : Blocks.GLOWSTONE, 1, this.getType() == 1 ? 1 : 0);
		this.entityDropItem(droppedItem, 0F);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return null;
	}

	@Override
    public int getMaxSpawnedInChunk()
    {
        return 6;
    }

}