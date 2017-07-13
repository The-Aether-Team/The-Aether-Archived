package com.legacy.aether.common.entities.hostile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.common.blocks.util.EnumStoneType;

public class EntitySentry extends EntityLiving implements IMob
{

	public static final DataParameter<Boolean> SENTRY_AWAKE = EntityDataManager.<Boolean>createKey(EntitySentry.class, DataSerializers.BOOLEAN);

	public float timeSpotted;

    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private boolean wasOnGround;

    public EntitySentry(World world)
    {
        super(world);

        this.moveHelper = new EntitySentry.SlimeMoveHelper(this);

        this.setSize(1.0F, 1.0F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0F);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3F);
        this.setHealth(this.getMaxHealth());
    }

    public EntitySentry(World world, double x, double y, double z)
    {
        this(world);

        this.setPosition(x, y, z);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntitySentry.AISlimeFloat(this));
        this.tasks.addTask(2, new EntitySentry.AISlimeAttack(this));
        this.tasks.addTask(3, new EntitySentry.AISlimeFaceRandom(this));
        this.tasks.addTask(5, new EntitySentry.AISlimeHop(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
        this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
    }

	@Override
	protected void entityInit()
	{
		super.entityInit();

	    this.dataManager.register(SENTRY_AWAKE, false);
	}

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setBoolean("wasOnGround", this.wasOnGround);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        this.wasOnGround = tagCompund.getBoolean("wasOnGround");
    }

    protected SoundEvent getJumpSound()
    {
        return SoundEvents.ENTITY_SLIME_JUMP;
    }

    @Override
    public void onUpdate()
    {
        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.isDead = true;
        }

        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 8D);

        if (entityplayer != null)
        {
        	if (!this.isAwake())
        	{
        		if (this.timeSpotted >= 24)
        		{
        			this.setAwake(true);
        		}

        		++this.timeSpotted;
        	}
        }
        else
        {
        	this.setAwake(false);
        }

        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        super.onUpdate();

        if (this.onGround && !this.wasOnGround)
        {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);

            this.squishAmount = -0.5F;
        }
        else if (!this.onGround && this.wasOnGround)
        {
            this.squishAmount = 1.0F;
        }

        this.wasOnGround = this.onGround;
        this.alterSquishAmount();
    }

    protected void alterSquishAmount()
    {
        this.squishAmount *= 0.6F;
    }

    protected int getJumpDelay()
    {
        return this.rand.nextInt(20) + 10;
    }

    protected EntitySentry createInstance()
    {
        return new EntitySentry(this.worldObj);
    }

    @Override
    public void applyEntityCollision(Entity entityIn)
    {
        super.applyEntityCollision(entityIn);

        if (!(entityIn instanceof EntitySentry))
        {
            this.explode((EntityLivingBase)entityIn);
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
    	this.explode(entityIn);
    }

    protected void explode(EntityLivingBase entity)
    {
        if (this.isAwake() && this.canEntityBeSeen(entity) && entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F))
        {
        	entity.addVelocity(0.5D, 0.5D, 0.5D);

            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 0.1F, false);

            this.setDead(); this.setHealth(0.0F);
            this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.applyEnchantments(this, entity);
        }
    }

	public void setAwake(boolean isAwake)
	{
		this.dataManager.set(SENTRY_AWAKE, isAwake);
	}

	public boolean isAwake()
	{
		return this.dataManager.get(SENTRY_AWAKE);
	}

    public float getEyeHeight()
    {
        return 0.625F * this.height;
    }

    @Override
    protected SoundEvent getHurtSound()
    {
        return SoundEvents.ENTITY_SLIME_JUMP;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SLIME_JUMP;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    @Override 
    protected void jump()
    {
    	if (this.isAwake())
    	{
            this.motionY = 0.41999998688697815D;
            this.isAirBorne = true;
    	}
    }

	@Override
	protected void dropFewItems(boolean var1, int var2) 
	{
		if (this.rand.nextInt(5) == 0)
		{
			Block block = BlocksAether.dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Sentry).getBlock();
			this.entityDropItem(new ItemStack(block), 0F);
		}
		else
		{
			this.entityDropItem(new ItemStack(BlocksAether.dungeon_block), 0F);
		}
	}

    static class AISlimeAttack extends EntityAIBase
        {
            private EntitySentry slime;
            private int field_179465_b;

            public AISlimeAttack(EntitySentry p_i45824_1_)
            {
                this.slime = p_i45824_1_;
                this.setMutexBits(2);
            }

            public boolean shouldExecute()
            {
                EntityLivingBase entitylivingbase = this.slime.getAttackTarget();

                if (!this.slime.isAwake())
                {
                	return false;
                }

                return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).capabilities.disableDamage);
            }

            public void startExecuting()
            {
                this.field_179465_b = 300;
                super.startExecuting();
            }

            public boolean continueExecuting()
            {
                EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
                return entitylivingbase == null ? false : (!entitylivingbase.isEntityAlive() ? false : (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage ? false : --this.field_179465_b > 0));
            }

            public void updateTask()
            {
                this.slime.faceEntity(this.slime.getAttackTarget(), 10.0F, 10.0F);
                ((EntitySentry.SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.slime.rotationYaw, true);
            }
        }

    static class AISlimeFaceRandom extends EntityAIBase
        {
            private EntitySentry slime;
            private float field_179459_b;
            private int field_179460_c;

            public AISlimeFaceRandom(EntitySentry p_i45820_1_)
            {
                this.slime = p_i45820_1_;
                this.setMutexBits(2);
            }

            public boolean shouldExecute()
            {
                return this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava());
            }

            public void updateTask()
            {
                if (--this.field_179460_c <= 0)
                {
                    this.field_179460_c = 40 + this.slime.getRNG().nextInt(60);
                    this.field_179459_b = (float)this.slime.getRNG().nextInt(360);
                }

                ((EntitySentry.SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.field_179459_b, false);
            }
        }

    static class AISlimeFloat extends EntityAIBase
        {
            private EntitySentry slime;

            public AISlimeFloat(EntitySentry p_i45823_1_)
            {
                this.slime = p_i45823_1_;
                this.setMutexBits(5);
                ((PathNavigateGround)p_i45823_1_.getNavigator()).setCanSwim(true);
            }

            public boolean shouldExecute()
            {
                return this.slime.isInWater() || this.slime.isInLava();
            }

            public void updateTask()
            {
                if (this.slime.getRNG().nextFloat() < 0.8F)
                {
                    this.slime.getJumpHelper().setJumping();
                }

                ((EntitySentry.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.2D);
            }
        }

    static class AISlimeHop extends EntityAIBase
        {
            private EntitySentry slime;

            public AISlimeHop(EntitySentry p_i45822_1_)
            {
                this.slime = p_i45822_1_;
                this.setMutexBits(5);
            }

            public boolean shouldExecute()
            {
                return true;
            }

            public void updateTask()
            {
                ((EntitySentry.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.0D);
            }
        }

    static class SlimeMoveHelper extends EntityMoveHelper
        {
            private float field_179922_g;
            private int field_179924_h;
            private EntitySentry slime;
            private boolean field_179923_j;

            public SlimeMoveHelper(EntitySentry p_i45821_1_)
            {
                super(p_i45821_1_);
                this.slime = p_i45821_1_;
            }

            public void func_179920_a(float p_179920_1_, boolean p_179920_2_)
            {
                this.field_179922_g = p_179920_1_;
                this.field_179923_j = p_179920_2_;
            }

            public void setSpeed(double speedIn)
            {
                this.speed = speedIn;
            }

            public void onUpdateMoveHelper()
            {
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.field_179922_g, 30.0F);
                this.entity.rotationYawHead = this.entity.rotationYaw;
                this.entity.renderYawOffset = this.entity.rotationYaw;

                if (this.entity.onGround)
                {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                    if (this.field_179924_h-- <= 0)
                    {
                        this.field_179924_h = this.slime.getJumpDelay();

                        if (this.field_179923_j)
                        {
                            this.field_179924_h /= 3;
                        }

                        this.slime.getJumpHelper().setJumping();

                        this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                    }
                    else
                    {
                        this.slime.moveStrafing = this.slime.moveForward = 0.0F;
                        this.entity.setAIMoveSpeed(0.0F);
                    }
                }
                else
                {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                }
            }
        }
}
