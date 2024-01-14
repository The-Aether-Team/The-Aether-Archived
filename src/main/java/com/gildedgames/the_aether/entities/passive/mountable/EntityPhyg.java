package com.gildedgames.the_aether.entities.passive.mountable;

import javax.annotation.Nullable;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.registry.AetherLootTables;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import com.gildedgames.the_aether.entities.util.EntitySaddleMount;
import com.gildedgames.the_aether.items.ItemsAether;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityPhyg extends EntitySaddleMount
{

	public float wingFold;

	public float wingAngle;

	private float aimingForFold;

	public int maxJumps;

	public int jumpsRemaining;

	public int ticks;

	public EntityPhyg(World world)
	{
		super(world);
		this.setSize(0.9F, 0.9F);
		
		this.jumpsRemaining = 0;
		this.maxJumps = 1;
		this.stepHeight = 1.0F;

		this.ignoreFrustumCheck = true;
		this.canJumpMidAir = true;
	}

	@Override
    protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, ItemsAether.blue_berry, false));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
    }

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		this.setHealth(10);
		
		if (this.getSaddled())
		{
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
			this.setHealth(20);
		}
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.onGround)
		{
			this.wingAngle *= 0.8F;
			this.aimingForFold = 0.1F;
			this.jumpsRemaining = this.maxJumps;
		}
		else
		{
			this.aimingForFold = 1.0F;
		}

		this.ticks++;

		this.wingAngle = this.wingFold * (float) Math.sin(this.ticks / 31.83098862F);
		this.wingFold += (this.aimingForFold - this.wingFold) / 5F;
		this.fallDistance = 0;
		this.fall();
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.phyg_death;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundsAether.phyg_hurt;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.phyg_say;
	}

	@Override
	public double getMountedYOffset()
	{
		return 0.65D;
	}

	@Override
	public float getMountedMoveSpeed()
	{
		return 0.3F;
	}

	@Override
	protected void jump()
	{
		if (this.getPassengers().isEmpty())
		{
			super.jump();
		}
	}

	private void fall()
	{
		if (this.motionY < 0.0D && !this.isRiderSneaking())
		{
			this.motionY *= 0.6D;
		}

		if (!this.onGround && !this.isJumping)
		{
			if (this.onGround)
			{
				this.jumpsRemaining = this.maxJumps;
			}
		}
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return this.rand.nextInt(AetherConfig.spawnrates.phyg_spawnrate) == 0 && super.getCanSpawnHere();
	}

	@Override
	protected double getMountJumpStrength()
	{
		return 5.0D;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		this.world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PIG_STEP, SoundCategory.NEUTRAL, 0.15F, 1.0F);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);

		this.maxJumps = nbttagcompound.getShort("Jumps");
		this.jumpsRemaining = nbttagcompound.getShort("Remaining");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);

		nbttagcompound.setShort("Jumps", (short) this.maxJumps);
		nbttagcompound.setShort("Remaining", (short) this.jumpsRemaining);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return new EntityPhyg(this.world);
	}
	
	@Nullable
    protected ResourceLocation getLootTable()
    {
        return AetherLootTables.phyg;
    }

}