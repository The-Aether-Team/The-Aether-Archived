package com.legacy.aether.common.entities.passive.mountable;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.util.EntitySaddleMount;
import com.legacy.aether.common.registry.sounds.SoundsAether;

public class EntityFlyingCow extends EntitySaddleMount
{

	public float wingFold;

	public float wingAngle;

	private float aimingForFold;

	public int maxJumps;

	public int jumpsRemaining;

	private int ticks;

	public EntityFlyingCow(World world)
	{
		super(world);

		this.ticks = 0;
		this.maxJumps = 1;
		this.jumpsRemaining = 0;
		this.stepHeight = 1.0F;
		this.ignoreFrustumCheck = true;
		this.canJumpMidAir = true;

		this.setSize(0.9F, 1.3F);
	}

	@Override
    protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
    }

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
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
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);

		nbttagcompound.setShort("Jumps", (short) this.maxJumps);
		nbttagcompound.setShort("Remaining", (short) this.jumpsRemaining);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);

		this.maxJumps = nbttagcompound.getShort("Jumps");
		this.jumpsRemaining = nbttagcompound.getShort("Remaining");
	}

	@Override
	public double getMountedYOffset()
	{
		return 1.15D;
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
		if (!this.onGround)
		{
			if (this.motionY < 0.0D && !this.isRiderSneaking())
			{
				this.motionY *= 0.6D;
			}

			if (this.onGround && !this.world.isRemote)
			{
				this.jumpsRemaining = this.maxJumps;
			}
		}
	}

	@Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack currentStack = player.getHeldItem(hand);

		if (currentStack.getItem() == Items.BUCKET)
		{
			Item milk = Items.MILK_BUCKET;

			if (currentStack.getCount() == 1)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(milk));
			}
			else if (!player.inventory.addItemStackToInventory(new ItemStack(milk)))
			{
				if (!this.world.isRemote)
				{
					this.world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(milk)));

					if (!player.capabilities.isCreativeMode)
					{
						currentStack.shrink(1);
					}
				}
			}
			else if (!player.capabilities.isCreativeMode)
			{
				currentStack.shrink(1);
			}
		}

		return super.processInteract(player, hand);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.flyingcow_say;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundsAether.flyingcow_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.flyingcow_death;
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		this.world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_COW_STEP, SoundCategory.NEUTRAL, 0.15F, 1.0F);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return new EntityFlyingCow(this.world);
	}

	protected void dropFewItems(boolean par1, int par2)
	{
		int j = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
		int k;

		for (k = 0; k < j; ++k)
		{
			this.dropItem(Items.LEATHER, 1);
		}

		j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);

		for (k = 0; k < j; ++k)
		{
			if (this.isBurning())
			{
				this.dropItem(Items.COOKED_BEEF, 1);
			}
			else
			{
				this.dropItem(Items.BEEF, 1);
			}
		}

		if (this.getSaddled())
		{
			this.dropItem(Items.SADDLE, 1);
		}
	}

	@Override
	protected double getMountJumpStrength()
	{
		return 5.0D;
	}

}