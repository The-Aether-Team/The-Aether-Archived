package com.legacy.aether.entities.passive.mountable;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import com.legacy.aether.entities.ai.aerbunny.AerbunnyAIHop;
import com.legacy.aether.entities.passive.EntityAetherAnimal;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.registry.sounds.SoundsAether;

public class EntityAerbunny extends EntityAetherAnimal
{

	public static final DataParameter<Integer> PUFF = EntityDataManager.<Integer>createKey(EntityAerbunny.class, DataSerializers.VARINT);

	public int puffiness;

    private int jumpTicks;

    private int jumps;

	public EntityAerbunny(World world)
    {
        super(world);

        this.ignoreFrustumCheck = true;

        this.setSize(0.4F, 0.4F);
    }

	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWander(this, 2D, 6));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, ItemsAether.blue_berry, false));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.tasks.addTask(6, new AerbunnyAIHop(this));
    }

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.setHealth(5.0F);
	}

	@Override
    public boolean isInRangeToRenderDist(double par1)
    {
        return true;
    }

	@Override
    public double getYOffset()
    {
        return 0.4D;
    }

	@Override
    protected void jump()
    {
        this.spawnExplosionParticle();
        this.setPuffiness(11);
        --this.jumps;
        super.jump();
    }

	@Override
    public void spawnExplosionParticle()
    {
        if (this.world.isRemote)
        {
            for (int i = 0; i < 5; ++i)
            {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d3 = 10.0D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d0 * d3, this.posY + (double)(this.rand.nextFloat() * this.height) - d1 * d3, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d2 * d3, d0, d1, d2, new int[0]);
            }
        }
        else
        {
            this.world.setEntityState(this, (byte)20);
        }
    }

	@Override
    public boolean canRiderInteract()
    {
        return true;
    }

    public int getPuffinessClient()
    {
        return this.puffiness;
    }

    public int getPuffiness()
    {
        return this.dataManager.get(PUFF);
    }

    public void setPuffinessClient(int i)
    {
        this.puffiness = i;
    }

    public void setPuffiness(int i)
    {
        this.dataManager.set(PUFF, i);
    }

	@Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(PUFF, new Integer(0));
    }

    public void onUpdate()
    {
    	this.setPuffinessClient(this.getPuffinessClient() - 1);
        this.setPuffiness(this.getPuffiness() - 1);

        if (this.getPuffinessClient() < 0)
        {
        	this.setPuffinessClient(0);
        }

        if (this.getPuffiness() < 0)
        {
            this.setPuffiness(0);
        }

        super.onUpdate();
    }

    public void onLivingUpdate()
    {
        if (this.onGround)
        {
            this.jumps = 1;
            this.jumpTicks = 10;
        }
        else if (this.jumpTicks > 0)
        {
            --this.jumpTicks;
        }

        if (this.isJumping && !this.isInWater() && !this.isInLava() && !this.onGround && this.jumpTicks == 0 && this.jumps > 0)
        {
        	if(this.moveForward != 0.0F)
            {
                this.jump();
            }
            this.jumpTicks = 10;
        }

        if (this.motionY < -0.1D)
        {
            this.motionY = -0.1D;
        }

        if (this.getRidingEntity() != null && this.getRidingEntity() instanceof EntityPlayer)
        {
        	EntityPlayer player = (EntityPlayer) this.getRidingEntity();

            if (this.world.isRemote)
            {
                for(int k = 0; k < 3; k++) 
                {
                	double d2 = (float)posX + rand.nextFloat() * 0.25F;
                    double d5 = (float)posY + height + 0.125F;
                    double d8 = (float)posZ + rand.nextFloat() * 0.25F;
                    float f1 = rand.nextFloat() * 360F;
                    this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, -Math.sin(0.01745329F * f1) * 0.75D, d5 - 0.25D, Math.cos(0.01745329F * f1) * 0.75D, d2, 0.125D, d8, new int[0]);
                }
            }

            this.getNavigator().clearPath();

            this.setRotation(player.rotationYaw, player.rotationPitch);

            player.fallDistance = 0.0F;

            if(!player.onGround && !player.isElytraFlying())
            {
            	if (!player.capabilities.isFlying)
            	{
                	player.motionY += 0.05000000074505806D;
            	}

            	player.fallDistance = 0.0F;

            	if (player.motionY < -0.22499999403953552D)
            	{
            		if (PlayerAether.get((EntityPlayer) this.getRidingEntity()).isJumping())
            		{
            			this.setPuffinessClient(11);
            	        this.spawnExplosionParticle();
            	        player.motionY = 0.125D;
            		}
            	}
            }
        }

        super.onLivingUpdate();
    }

    @Override
	public void fall(float distance, float damageMultiplier) {}

    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
        return false;
    }

    @Override
    public boolean isOnLadder()
    {
        return false;
    }
    
    @Override
    public boolean processInteract(EntityPlayer entityplayer, EnumHand hand)
    {
        ItemStack itemstack = entityplayer.getHeldItem(hand);

        if (itemstack.getItem() == Items.NAME_TAG)
        {
            return super.processInteract(entityplayer, hand);
        }
        else
        {
        	this.world.playSound(this.posX, this.posY, this.posZ, SoundsAether.aerbunny_lift, SoundCategory.NEUTRAL, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);

            if (this.isRiding())
            {
                this.dismountRidingEntity();
            }
            else
            {
                this.startRiding(entityplayer);
            }

            return true;
        }
    }

    @Override
    protected void dropFewItems(boolean var1, int var2)
    {
        this.dropItem(Items.STRING, 1);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage)
    {
        return source.getImmediateSource() == this.getRidingEntity() ? false : super.attackEntityFrom(source, damage);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return this.onGround;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundsAether.aerbunny_hurt;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsAether.aerbunny_death;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable)
    {
        return new EntityAerbunny(this.world);
    }

}