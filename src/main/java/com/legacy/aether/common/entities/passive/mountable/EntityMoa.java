package com.legacy.aether.common.entities.passive.mountable;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.util.EntitySaddleMount;
import com.legacy.aether.common.entities.util.MoaColor;
import com.legacy.aether.common.items.ItemMoaEgg;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.registry.sounds.SoundsAether;

public class EntityMoa extends EntitySaddleMount
{

	public static final DataParameter<Integer> MOA_COLOR = EntityDataManager.<Integer>createKey(EntityMoa.class, DataSerializers.VARINT);

	public static final DataParameter<Integer> REMAINING_JUMPS = EntityDataManager.<Integer>createKey(EntityMoa.class, DataSerializers.VARINT);

	public static final DataParameter<Byte> AMMOUNT_FEED = EntityDataManager.<Byte>createKey(EntityMoa.class, DataSerializers.BYTE);

	public static final DataParameter<Boolean> PLAYER_GROWN = EntityDataManager.<Boolean>createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	public static final DataParameter<Boolean> HUNGRY = EntityDataManager.<Boolean>createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	public static final DataParameter<Boolean> SITTING = EntityDataManager.<Boolean>createKey(EntityMoa.class, DataSerializers.BOOLEAN);

	public float wingRotation, destPos, prevDestPos, prevWingRotation;

	protected int maxJumps, ticksOffGround, ticksUntilFlap, secsUntilFlying, secsUntilWalking, secsUntilHungry, secsUntilEgg;

	public EntityMoa(World world)
	{
		super(world);

		this.initAI();

		this.setSize(1.0F, 2.0F);
		this.stepHeight = 1.0F;

		this.secsUntilEgg = this.getRandomEggTime();
	}

	public EntityMoa(World world, MoaColor color)
	{
		this(world);
		this.setColor(color);
	}

	@Override
    public void move(MoverType type, double x, double y, double z)
    {
		if (!this.isSitting())
		{
			super.move(type, x, y, z);
		}
		else
		{
			super.move(type, 0, y, 0);
		}
    }

	public int getRandomEggTime()
	{
		return 775 + this.rand.nextInt(50);
	}

	public void initAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIWander(this, 0.30F));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(6, new EntityAIMate(this, 0.25F));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();

		MoaColor color = MoaColor.getRandomColor(this.world);

		this.dataManager.register(MOA_COLOR, color.ID);
		this.dataManager.register(REMAINING_JUMPS, color.jumps);
		this.dataManager.register(PLAYER_GROWN, false);
		this.dataManager.register(AMMOUNT_FEED, Byte.valueOf((byte) 0));
		this.dataManager.register(HUNGRY, false);
		this.dataManager.register(SITTING, false);
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
		this.setHealth(35);
	}

	public boolean isSitting()
	{
		return this.dataManager.get(SITTING);
	}

	public void setSitting(boolean isSitting)
	{
		this.dataManager.set(SITTING, isSitting);
	}

	public boolean isHungry()
	{
		return this.dataManager.get(HUNGRY);
	}

	public void setHungry(boolean hungry)
	{
		this.dataManager.set(HUNGRY, hungry);
	}

	public byte getAmountFed()
	{
		return this.dataManager.get(AMMOUNT_FEED);
	}

	public void setAmountFed(int amountFed)
	{
		this.dataManager.set(AMMOUNT_FEED, Byte.valueOf((byte) amountFed));
	}

	public void increaseAmountFed(int amountFed)
	{
		this.setAmountFed(this.getAmountFed() + amountFed);
	}

	public boolean isPlayerGrown()
	{
		return this.dataManager.get(PLAYER_GROWN);
	}

	public void setPlayerGrown(boolean playerGrown)
	{
		this.dataManager.set(PLAYER_GROWN, playerGrown);
	}

	public int getMaxJumps()
	{
		return this.maxJumps;
	}

	public void setMaxJumps(int maxJumps)
	{
		this.maxJumps = maxJumps;
	}

	public int getRemainingJumps()
	{
		return this.dataManager.get(REMAINING_JUMPS);
	}

	public void setRemainingJumps(int jumps)
	{
		this.dataManager.set(REMAINING_JUMPS, jumps);
	}

	public MoaColor getColor()
	{
		return MoaColor.getColor(this.dataManager.get(MOA_COLOR));
	}

	public void setColor(MoaColor color)
	{
		this.dataManager.set(MOA_COLOR, color.ID);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.setMaxJumps(getColor().jumps);

		if (this.isJumping)
		{
			this.motionY += 0.05F;
		}

		this.updateWingRotation();
		this.fall();
		
		if (this.secsUntilHungry > 0)
		{
			if (this.ticksExisted % 20 == 0)
			{
				this.secsUntilHungry--;
			}
		}
		else if (!this.isHungry())
		{
			this.setHungry(true);
		}

		if (!this.world.isRemote && !this.isChild() && this.getPassengers().isEmpty())
		{
			if (this.secsUntilEgg > 0)
			{
				if (this.ticksExisted % 20 == 0)
				{
					this.secsUntilEgg--;
				}
			}
			else
			{
				this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				this.entityDropItem(ItemMoaEgg.getStackFromColor(this.getColor()), 0);

				this.secsUntilEgg = this.getRandomEggTime();
			}
		}

		this.fallDistance = 0.0F;
	}

	public void resetHunger()
	{
		if (!this.world.isRemote)
		{
			this.setHungry(false);
		}

		this.secsUntilHungry = 40 + this.rand.nextInt(40);
	}

	public void updateWingRotation()
	{
		if (!this.onGround)
		{
			if (this.ticksUntilFlap == 0)
			{
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundsAether.moa_flap, SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7f, 1.0f) + MathHelper.clamp(this.rand.nextFloat(), 0f, 0.3f));

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
		this.destPos = minMax(0.01F, 1.0F, this.destPos);

		if (onGround)
		{
			this.destPos = 0.0F;
		}

		this.wingRotation += 1.233F;
	}

	public static float minMax(float min, float max, float value)
	{
		return Math.min(max, Math.max(min, value));
	}

	@Override
	public void onMountedJump(float par1, float par2)
	{
		if (this.getRemainingJumps() > 0 && this.motionY < 0.0D)
		{
			if (!this.onGround)
			{
				this.motionY = 0.7D;
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundsAether.moa_flap, SoundCategory.NEUTRAL, 0.15F, MathHelper.clamp(this.rand.nextFloat(), 0.7f, 1.0f) + MathHelper.clamp(this.rand.nextFloat(), 0f, 0.3f));

				if (!this.world.isRemote)
				{
					this.setRemainingJumps(this.getRemainingJumps() - 1);
				}

				if (!this.world.isRemote)
				{
					this.spawnExplosionParticle();
				}
			}
			else
			{
				this.motionY = 0.89D;
			}
		}
	}

	@Override
	public float getMountedMoveSpeed()
	{
		return this.getColor().ID == 1 ? 0.6F : 0.3F;
	}

	public void setToAdult()
	{
		this.setGrowingAge(0);
	}

	@Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if (stack != ItemStack.EMPTY && this.isPlayerGrown())
		{
			Item currentItem = stack.getItem();

			if (this.isChild() && this.isHungry())
			{
				if (this.getAmountFed() < 3 && currentItem == ItemsAether.aechor_petal)
				{
					if (!player.capabilities.isCreativeMode)
					{
						stack.shrink(1);
					}

					this.increaseAmountFed(1);

					if (this.getAmountFed() >= 3)
					{
						this.setToAdult();
					}
					else
					{
						this.resetHunger();
					}
				}
			}
			
			if (currentItem == ItemsAether.nature_staff)
			{
				stack.damageItem(2, player);

				this.setSitting(this.isSitting() ? false : true);

				return true;
			}
		}

		return super.processInteract(player, hand);
	}

	@Override
	public boolean canSaddle()
	{
		return !this.isChild() && this.isPlayerGrown();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);

		nbt.setBoolean("playerGrown", this.isPlayerGrown());
		nbt.setInteger("remainingJumps", this.getRemainingJumps());
		nbt.setInteger("color", this.getColor().ID);
		nbt.setByte("amountFed", this.getAmountFed());
		nbt.setBoolean("isHungry", this.isHungry());
		nbt.setBoolean("isSitting", this.isSitting());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);

		this.setPlayerGrown(nbt.getBoolean("playerGrown"));
		this.setRemainingJumps(nbt.getInteger("remainingJumps"));
		this.setColor(MoaColor.getColor(nbt.getInteger("color")));
		this.setAmountFed(nbt.getByte("amountFed"));
		this.setHungry(nbt.getBoolean("isHungry"));
		this.setSitting(nbt.getBoolean("isSitting"));
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsAether.moa_say;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundsAether.moa_say;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.moa_say;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PIG_STEP, SoundCategory.NEUTRAL, 0.15F, 1.0F);
	}

	@Override
	protected void dropFewItems(boolean var1, int var2)
	{
		super.dropFewItems(var1, var2);

		this.dropItem(Items.FEATHER, 3);
	}

	public void fall()
	{
		boolean blockBeneath = !this.world.isAirBlock(new BlockPos(this).down());

		if (this.motionY < 0.0D && !this.isRiderSneaking())
		{
			this.motionY *= 0.6D;
		}

		if (blockBeneath)
		{
			this.setRemainingJumps(this.maxJumps);
		}
	}

	@Override
	public void jump()
	{
		if (!this.isSitting() && this.getPassengers().isEmpty())
		{
			super.jump();
		}
	}

	@Override
	public double getMountedYOffset()
	{
		return 1.25D;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable matingAnimal)
	{
		return new EntityMoa(this.world, this.getColor());
	}
}