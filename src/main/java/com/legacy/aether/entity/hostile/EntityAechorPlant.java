package com.legacy.aether.entity.hostile;

import com.legacy.aether.block.BlocksAether;
import com.legacy.aether.entity.EntityTypesAether;
import com.legacy.aether.entity.passive.EntityAetherAnimal;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class EntityAechorPlant extends EntityAetherAnimal implements IRangedAttackMob
{

	public float sinage;

	public int poisonRemaining;

	public int size;

	public EntityAechorPlant(World world)
	{
		super(EntityTypesAether.AECHOR_PLANT, world);

		this.size = this.rand.nextInt(4) + 1;
		this.sinage = this.rand.nextFloat() * 6.0F;
		this.poisonRemaining = this.rand.nextInt(4) + 2;

		this.setCanPickUpLoot(false);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.setSize(0.75F + (float) this.size * 0.125F, 0.5F + (float) this.size * 0.075F);
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAIAttackRanged(this, 0.0D, 30, 1.0F));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	}

	@Override
	public int getMaxSpawnedInChunk()
	{
		return 3;
	}

	@Override
	public void livingTick()
	{
		super.livingTick();

		if (this.hurtTime > 0)
		{
			this.sinage += 0.9F;
		}
		else if (this.getAttackTarget() != null)
		{
			this.sinage += 0.3F;
		}
		else
		{
			this.sinage += 0.1F;
		}

		if (this.sinage > 6.283186F)
		{
			this.sinage -= 6.283186F;
		}
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
	{
		if (state.getBlock() != BlocksAether.AETHER_GRASS)
		{
			this.onDeath(DamageSource.GENERIC);
		}
	}

	@Override
	public void knockBack(Entity entity, float strength, double xRatio, double zRatio)
	{
		if (this.getHealth() < 0.0F)
		{
			super.knockBack(entity, strength, xRatio, zRatio);
		}
	}

	@Override
	protected void collideWithEntity(Entity entityIn)
	{
		if (!entityIn.isRidingSameEntity(this) && !this.noClip && !entityIn.noClip)
		{
			double d0 = this.posX - entityIn.posX;
			double d1 = this.posZ - entityIn.posZ;
			double d2 = MathHelper.absMax(d0, d1);

			if (d2 >= 0.009999999776482582D)
			{
				d2 = (double) MathHelper.sqrt(d2);
				d0 /= d2;
				d1 /= d2;

				double d3 = 1.0D / d2;

				if (d3 > 1.0D)
				{
					d3 = 1.0D;
				}

				d0 *= d3;
				d1 *= d3;
				d0 *= 0.05000000074505806D;
				d1 *= 0.05000000074505806D;
				d0 *= (double) (1.0F - entityIn.entityCollisionReduction);
				d1 *= (double) (1.0F - entityIn.entityCollisionReduction);

				if (!entityIn.isBeingRidden())
				{
					entityIn.addVelocity(-d0, 0.0D, -d1);
				}
			}
		}
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		if (!this.world.getDifficulty().equals(EnumDifficulty.PEACEFUL))
		{
			//double x = target.posX - this.posX;
			//double z = target.posZ - this.posZ;
			//double distance = 1.5D / Math.sqrt(x * x + z * z + 0.1D);
			//double var10000 = x * distance;
			//var10000 = z * distance;
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		return super.processInteract(player, hand);
	}

	@Override
	public void writeAdditional(NBTTagCompound compound)
	{
		super.writeAdditional(compound);

		compound.setInt("Size", this.size);
	}

	@Override
	public void readAdditional(NBTTagCompound compound)
	{
		super.readAdditional(compound);

		this.size = compound.getInt("Size");
	}

	@Override
	public boolean canEntityBeSeen(Entity entity)
	{
		double distance = (double) this.getDistance(entity);

		return distance <= 4.0D && super.canEntityBeSeen(entity);
	}

	@Override
	public boolean canSpawn(IWorld worldIn, boolean spawnerSpawned)
	{
		return this.rand.nextInt(400) == 0 && super.canSpawn(worldIn, spawnerSpawned);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity)
	{
		return null;
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return null;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_GENERIC_BIG_FALL;
	}

	@Override
	public void setSwingingArms(boolean swingingArms)
	{
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	public boolean canDespawn()
	{
		return true;
	}

}