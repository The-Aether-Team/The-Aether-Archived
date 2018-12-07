package com.legacy.aether.entities.passive.mountable;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.hostile.swet.EnumSwetType;
import com.legacy.aether.entities.util.EntityMountable;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;

public class EntitySwet extends EntityMountable
{

	private int slimeJumpDelay = 0;

	public int ticker;

	public int flutter;

	public int hops;

	public boolean kickoff;

	public EntitySwet(World world)
	{
		super(world);

		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25);
		this.setHealth(25);

		this.setSwetType(this.rand.nextInt(2));
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.5F);
		this.setSize(0.8F, 0.8F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.hops = 0;
		this.flutter = 0;
		this.ticker = 0;
		this.slimeJumpDelay = this.rand.nextInt(20) + 10;
	}

	@Override
	public void entityInit()
	{
		super.entityInit();

		this.dataWatcher.addObject(20, new Byte((byte) 0));
		this.dataWatcher.addObject(21, new Byte((byte) this.rand.nextInt(EnumSwetType.values().length)));
	}

	@Override
	public void updateRidden()
	{
		super.updateRidden();

		if (this.riddenByEntity != null && this.kickoff)
		{
			this.riddenByEntity.mountEntity(null);
			this.kickoff = false;
		}
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.getAttackTarget() != null)
		{
			for (int i = 0; i < 3; i++)
			{
				double d = (float) this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
				double d1 = (float) this.posY + this.height;
				double d2 = (float) this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;

				this.worldObj.spawnParticle("splash", d, d1 - 0.25D, d2, 0.0D, 0.0D, 0.0D);
			}
		}

		if (this.riddenByEntity == null && !this.isFriendly())
		{
			List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.5D, 0.75D, 0.5D), new IEntitySelector()
			{
				@Override
				public boolean isEntityApplicable(Entity entity)
				{
					return !(entity instanceof EntitySwet) && entity instanceof EntityLivingBase && entity.ridingEntity == null;
				}
			});

			for (int j = 0; j < list.size() && this.riddenByEntity == null; ++j)
			{
				EntityLivingBase entity = (EntityLivingBase) list.get(j);

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
	public void fall(float distance)
	{
		if (!this.isFriendly())
		{
			super.fall(distance);

			if (this.hops >= 3 && this.getHealth() >= 0)
			{
				this.dissolve();
			}
		}
	}

	@Override
	public void knockBack(Entity entity, float damage, double distanceX, double distanceZ)
	{
		if (this.riddenByEntity != entity)
		{
			super.knockBack(entity, damage, distanceX, distanceZ);
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

			this.worldObj.spawnParticle("splash", this.posX + (double) f2, this.boundingBox.minY + 1.25D, this.posZ + (double) f3, (double) f2 * 1.5D + this.motionX, 4D, (double) f3 * 1.5D + this.motionZ);
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

		entity.mountEntity(this);

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

		if (flag && this.riddenByEntity != null && (this.riddenByEntity instanceof EntityLivingBase))
		{
			EntityLivingBase rider = (EntityLivingBase) this.riddenByEntity;

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

			if (entityliving.getHealth() > 0 && (this.riddenByEntity == null || this.riddenByEntity != null && entityliving != this.riddenByEntity))
			{
				this.setAttackTarget((EntityLivingBase) entity);
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
	public void updateEntityActionState()
	{
		super.updateEntityActionState();
		this.entityAge++; 

		if (this.isFriendly() && this.riddenByEntity != null)
		{
			return;
		}

		if (this.getAttackTarget() == null && this.riddenByEntity == null && this.getHealth() > 0)
		{
			if (this.onGround && this.slimeJumpDelay-- <= 0)
			{
				this.slimeJumpDelay = this.getJumpDelay();

				this.isJumping = true;

				this.motionY = 0.34999999403953552D;

				this.playSound("mob.slime.small", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

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

		if (this.getAttackTarget() != null && this.riddenByEntity == null && this.getHealth() > 0)
		{
        	float f = MathHelper.wrapAngleTo180_float(20.0F);

            if (f > 20.0F)
            {
                f = 10.0F;
            }

            if (f < -20.0F)
            {
                f = -210.0F;
            }

            this.rotationYaw = f + this.getAttackTarget().rotationYaw + 214.0F;
			//this.faceEntity(this.getAttackTarget(), 10F, 20F);
			
			if (this.onGround && this.slimeJumpDelay-- <= 0)
			{
				this.slimeJumpDelay = this.getJumpDelay();

				this.isJumping = true;

				this.motionY = 0.34999999403953552D;

				this.playSound("mob.slime.small", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

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
			if (this.onGround && this.riddenByEntity == null && this.hops != 0 && this.hops != 3)
			{
				this.hops = 0;
			}

			if (this.getAttackTarget() == null && this.riddenByEntity == null)
			{
				Entity entity = this.getPrey();
				if (entity != null)
				{
					this.setAttackTarget((EntityLivingBase) entity);
				}
			}
			else if (this.getAttackTarget() != null && this.riddenByEntity == null)
			{
				if (this.getDistanceToEntity(this.getAttackTarget()) <= 9F)
				{
					if (this.onGround && this.canEntityBeSeen(this.getAttackTarget()))
					{
						this.splotch();
						this.flutter = 10;
						this.isJumping = true;
						this.moveForward = 16.0F;
						this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
						this.rotationYaw += 5F * (this.rand.nextFloat() - this.rand.nextFloat());
					}
				}
				else
				{
					this.setAttackTarget(null);
					this.isJumping = false;
					this.moveStrafing = this.moveForward = 0.0F;
				}
			}
			else if (this.riddenByEntity != null && this.riddenByEntity != null && this.onGround)
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
		EntityPlayer rider = this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer ? (EntityPlayer) this.riddenByEntity : null;

		if (rider != null)
		{
			PlayerAether aetherRider = PlayerAether.get(rider);

			if (aetherRider == null)
			{
				return;
			}

			this.setFriendly(aetherRider.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.swet_cape)) ? true : false);

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

					int rotate = MathHelper.floor_double(rider.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

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

				if (!this.worldObj.isRemote)
				{
					this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
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

		this.playSound("mob.slime.small", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
    }

	public void setSwetType(int id)
	{
		this.dataWatcher.updateObject(21, (byte) id);
	}

	public EnumSwetType getSwetType()
	{
		int id = this.dataWatcher.getWatchableObjectByte(21);

		return EnumSwetType.get(id);
	}

	public void setFriendly(boolean friendly)
	{
		this.dataWatcher.updateObject(20, (byte) (friendly ? 1 : 0));
	}

	public boolean isFriendly()
	{
		return this.dataWatcher.getWatchableObjectByte(20) == (byte)1;
	}

	public void splorch()
	{
		this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.attack", 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);
	}

	public void splotch()
	{
		this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.slime.small", 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.slime.small";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.slime.small";
	}

	@Override
	public void applyEntityCollision(Entity entity)
	{
		if (this.hops == 0 && !this.isFriendly() && this.riddenByEntity == null && this.getAttackTarget() != null && entity != null && entity == this.getAttackTarget() && (entity.ridingEntity == null || !(entity.ridingEntity instanceof EntitySwet)))
		{
			this.capturePrey(entity);
		}

		super.applyEntityCollision(entity);
	}

	@Override
    public boolean interact(EntityPlayer player)
	{
		if (!this.worldObj.isRemote && this.isFriendly())
		{
			if (this.riddenByEntity == null)
			{
				this.capturePrey(player);
			}
			else if (this.riddenByEntity == player)
			{
				player.mountEntity(null);
			}
		}

		return super.interact(player);
	}

	protected Entity getPrey()
	{
		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(6D, 6D, 6D));
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
	protected void dropFewItems(boolean recentlyHit, int lootLevel)
	{
		int count = this.rand.nextInt(3);

        if (lootLevel > 0)
        {
        	count += this.rand.nextInt(lootLevel + 1);
        }

		this.entityDropItem(new ItemStack(this.getSwetType().getId() == 0 ? BlocksAether.aercloud : Blocks.glowstone, count, this.getSwetType().getId() == 0 ? 1 : 0), 1.0F);
		this.entityDropItem(new ItemStack(ItemsAether.swet_ball, count), 1.0F);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return null;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setShort("Hops", (short) this.hops);
		compound.setShort("Flutter", (short) this.flutter);
		compound.setBoolean("isFriendly", this.isFriendly());
		compound.setInteger("swetType", this.getSwetType().getId());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.hops = compound.getShort("Hops");
		this.flutter = compound.getShort("Flutter");

		this.setFriendly(compound.getBoolean("isFriendly"));
		this.setSwetType(compound.getInteger("swetType"));
	}

}