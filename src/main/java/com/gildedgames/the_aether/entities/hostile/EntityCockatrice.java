package com.gildedgames.the_aether.entities.hostile;

import javax.annotation.Nullable;

import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import com.gildedgames.the_aether.entities.projectile.EntityPoisonNeedle;
import com.gildedgames.the_aether.registry.AetherLootTables;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

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
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 1.0D, 8.0F));
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

		if (this.getAttackTarget() != null)
		{
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

		EntityArrow entityarrow = new EntityPoisonNeedle(this.world, this);
		double d0 = getAttackTarget().posX - this.posX;
		double d1 = getAttackTarget().getEntityBoundingBox().minY + (double)(getAttackTarget().height / 3.0F) - entityarrow.posY;
		double d2 = getAttackTarget().posZ - this.posZ;
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.0F, (float)(14 - this.world.getDifficulty().getId() * 4));
		this.playSound(SoundsAether.cockatrice_attack, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
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
				this.world.playSound(null, new BlockPos(this), SoundsAether.cockatrice_flap, SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7f, 1.0f) + MathHelper.clamp(this.rand.nextFloat(), 0f, 0.3f));

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
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (source.getTrueSource() != null)
		{
			if (source.getTrueSource() instanceof EntityLivingBase)
			{
				this.setAttackTarget((EntityLivingBase) source.getTrueSource());
			}
		}

		return super.attackEntityFrom(source, amount);
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
		return SoundsAether.cockatrice_say;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundsAether.cockatrice_say;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.cockatrice_say;
	}
	
	@Override
	public double getMountedYOffset()
	{
		return 1.25D;
	}

	@Nullable
    protected ResourceLocation getLootTable()
    {
        return AetherLootTables.cockatrice;
    }

}