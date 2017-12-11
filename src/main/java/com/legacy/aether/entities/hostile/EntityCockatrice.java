package com.legacy.aether.entities.hostile;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.legacy.aether.entities.passive.mountable.EntityMoa;
import com.legacy.aether.entities.projectile.EntityPoisonNeedle;
import com.legacy.aether.registry.sounds.SoundsAether;

public class EntityCockatrice extends EntityMob
{

	public float wingRotation, destPos, prevDestPos, prevWingRotation;

	public int shootTime, ticksUntilFlap;

	public EntityCockatrice(World world)
	{
		super(world);

		this.stepHeight = 1.0F;
		this.setSize(1.0F, 2.0F);
	}

	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.setHealth(20);
    }

	@Override
	public boolean getCanSpawnHere()
	{
		return this.rand.nextInt(25) == 0 && super.getCanSpawnHere();
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect)
	{
		return effect.getPotion() == MobEffects.POISON ? false : super.isPotionApplicable(effect);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.getAttackTarget() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) this.getAttackTarget();

			if (player.capabilities.isCreativeMode || this.getHealth() <= 0.0F || this.isDead || this.getAttackTarget().isDead || this.getAttackTarget().getDistance(this) > 12D)
			{
				this.setAttackTarget(null);
				this.shootTime = 0;
				return;
			}
			
			double d = this.getAttackTarget().posX - this.posX;
			double d1 = this.getAttackTarget().posZ - this.posZ;

            this.getLookHelper().setLookPositionWithEntity(this.getAttackTarget(), 30.0F, 30.0F);

			if (this.shootTime >= 20 && this.canEntityBeSeen(this.getAttackTarget()))
			{
				this.shootTarget();
				this.shootTime = -60;
			}

			if (this.shootTime < 20)
			{
				this.shootTime += 2;
			}

			this.rotationYaw = (float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
		}

		this.updateWingRotation();

		if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			this.setDead();
		}
	}

	public void shootTarget()
	{
		if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			return;
		}

		double d1, d2;
		d1 = this.getAttackTarget().posX - this.posX;
		d2 = this.getAttackTarget().posZ - this.posZ;
		double d3 = 1.5D / Math.sqrt((d1 * d1) + (d2 * d2) + 0.1D);
		d1 = d1 * d3;
		d2 = d2 * d3;
		EntityPoisonNeedle entityarrow = new EntityPoisonNeedle(this.world, this);
		entityarrow.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 1.0F, 1.0F);
		entityarrow.posY = this.posY + 1.55D;
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
		this.world.spawnEntity(entityarrow);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (!this.onGround && this.motionY < 0.0D)
		{
			this.motionY *= 0.59999999999999998D;
		}
	}

	public void updateWingRotation()
	{
		if (!this.onGround)
		{
			if (this.ticksUntilFlap == 0)
			{
				this.world.playSound(null, new BlockPos(this), SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7f, 1.0f) + MathHelper.clamp(this.rand.nextFloat(), 0f, 0.3f));

				this.ticksUntilFlap = 8;
			}
			else
			{
				this.ticksUntilFlap--;
			}
		}

		this.prevWingRotation = this.wingRotation;
		this.prevDestPos = this.destPos;

		this.destPos += 0.2D;
		this.destPos = EntityMoa.minMax(0.01F, 1.0F, this.destPos);

		if (this.onGround)
		{
			this.destPos = 0.0F;
		}

		this.wingRotation += 1.233F;
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.moa_say;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundsAether.moa_say;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.moa_say;
	}

	@Override
	protected void dropFewItems(boolean var1, int var2)
	{
		this.dropItem(Items.FEATHER, 1 + this.rand.nextInt(4));
	}

}