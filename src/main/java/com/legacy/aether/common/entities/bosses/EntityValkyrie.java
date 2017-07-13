package com.legacy.aether.common.entities.bosses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import com.legacy.aether.common.Aether;
import com.legacy.aether.common.items.ItemsAether;

public class EntityValkyrie extends EntityMob
{

	private int attackTime;

    public int angerLevel;

    public int timeLeft, chatTime;

    public double safeX, safeY, safeZ;

    public float sinage;

    public double lastMotionY;

    public int teleTimer;

    public EntityValkyrie(World world)
    {
        super(world);
        setSize(0.8F, 1.6F);
        this.teleTimer = rand.nextInt(250);
        this.timeLeft = 1200;
        this.safeX = posX;
        this.safeY = posY;
        this.safeZ = posZ;
    }

    @Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);   
	}

	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(2, new EntityAIWander(this, 0.5D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 0.65D, true));
    }

    public void swingArm()
    {
        if (!this.isSwingInProgress)
        {
        	this.isSwingInProgress = true;
        }
    }

    private void becomeAngryAt(EntityLivingBase entity)
    {
    	this.setAttackTarget(entity);
    	this.angerLevel = 200 + rand.nextInt(200);
    }

    private void chatItUp(EntityPlayer player, String s)
    {
		Side side = FMLCommonHandler.instance().getEffectiveSide();

        if (this.chatTime <= 0)
        {
        	if (side.isClient())
        	{
                Aether.proxy.sendMessage(player, s);
        	}

            this.chatTime = 60;
        }
    }

    public void teleport(double x, double y, double z, int rad) 
    {
        int a = this.rand.nextInt(rad + 1);
        int b = this.rand.nextInt(rad / 2);
        int c = rad - a;

        a *= ((this.rand.nextInt(2) * 2) - 1);
        b *= ((this.rand.nextInt(2) * 2) - 1);
        c *= ((this.rand.nextInt(2) * 2) - 1);

        x += (double) a;
        y += (double) b;
        z += (double) c;

        int newX = (int) Math.floor(x - 0.5D);
        int newY = (int) Math.floor(y - 0.5D);
        int newZ = (int) Math.floor(z - 0.5D);

        boolean flag = false;
        
        for (int q = 0; q < 32 && !flag; q++)
        {
        	this.rand.nextInt(rad / 2);
        	this.rand.nextInt(rad / 2);
            int j = newY + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            this.rand.nextInt(rad / 2);
            this.rand.nextInt(rad / 2);

            if (j > 124 || j < 5) 
            {
                continue;
            }
        }
        
        if (!flag)
        {
            teleFail();
        }
        else
        {
            spawnExplosionParticle();
            setPosition((double) newX + 0.5D, (double) newY + 0.5D, (double) newZ + 0.5D);
            this.motionX = this.motionY = this.motionZ = 0.0D; 
            this.moveForward = this.moveStrafing = this.rotationPitch = this.rotationYaw = 0.0F;
            this.isJumping = false;
            this.renderYawOffset = this.rand.nextFloat() * 360F;
            spawnExplosionParticle();
            this.teleTimer = this.rand.nextInt(40);
        }
    }

    public void teleFail()
    {
    	this.teleTimer -= (this.rand.nextInt(40) + 40);
        
        if (this.posY <= 0D)
        {
        	this.teleTimer = 446;
        }
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();
        this.teleTimer++;
        --this.attackTime;
        
        if (this.teleTimer >= 450)
        {
            if (this.getAttackTarget() != null)
            {
                teleport(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ, 7);
            }
            else if (!this.onGround) 
            {   	
                teleport(this.safeX, this.safeY, this.safeZ, 6);
            }
        }
        else if (this.teleTimer < 446 && (this.posY <= 0D || this.posY <= (this.safeY - 16D)))
        {
        	this.teleTimer = 446;
        } 
        else if ((this.teleTimer % 5) == 0 && this.getAttackTarget() != null && !canEntityBeSeen(this.getAttackTarget()))
        {
        	this.teleTimer += 100;
        }

        if (this.onGround && this.teleTimer % 10 == 0)
        {
        	this.safeX = this.posX;
        	this.safeY = this.posY;
        	this.safeZ = this.posZ;
        }

        if (this.getAttackTarget() != null && this.getAttackTarget().isDead) 
        {
        	this.setAttackTarget(null);
        	this.angerLevel = 0;
        }

        if (this.chatTime > 0) 
        {
        	this.chatTime--;
        }
    }

    @Override
    public void onUpdate()
    {
    	this.lastMotionY = motionY;
        super.onUpdate();
        
        if (!this.onGround && this.getAttackTarget() != null && this.lastMotionY >= 0.0D && this.motionY < 0.0D && getDistanceToEntity(this.getAttackTarget()) <= 16F && canEntityBeSeen(this.getAttackTarget())) 
        {
            double a = this.getAttackTarget().posX - posX;
            double b = this.getAttackTarget().posZ - posZ;
            double angle = Math.atan2(a, b);
            this.motionX = Math.sin(angle) * 0.25D;
            this.motionZ = Math.cos(angle) * 0.25D;
        }
        
        if (!this.onGround && !isOnLadder() && Math.abs(this.motionY - this.lastMotionY) > 0.07D && Math.abs(this.motionY - this.lastMotionY) < 0.09D)
        {
        	this.motionY += 0.055F;
            
            if (this.motionY < -0.275F) 
            {
            	this.motionY = -0.275F;
            }
        }

        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL && (this.getAttackTarget() != null || this.angerLevel > 0))
        {
        	this.angerLevel = 0;
            this.setAttackTarget(null);
        }

        if (!this.onGround) 
        {
        	this.sinage += 0.75F;
        }
        else 
        {
        	this.sinage += 0.15F;
        }

        if (this.sinage > 3.141593F * 2F) 
        {
        	this.sinage -= (3.141593F * 2F);
        }

    	if (this.getAttackTarget() instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)this.getAttackTarget();
    	
            if (this.getHealth() <= 0)
            {
                int pokey = rand.nextInt(3);

                if (pokey == 2) 
                {
                    chatItUp(player, "Alright, alright! You win!");
                } 
                else if (pokey == 1) 
                {
                    chatItUp(player, "Okay, I give up! Geez!");
                } 
                else
                {
                    chatItUp(player, "Oww! Fine, here's your medal...");
                }
                
                this.setDead();
            }

    		if (player.getHealth() <= 0 && player.isDead)
    		{     
    			int pokey = rand.nextInt(3);

    			if (pokey == 2) 
    			{
    				chatItUp(player, "You want a medallion? Try being less pathetic.");
            	}
    			else if (pokey == 1) 
    			{
    				chatItUp(player, "Maybe some day, " + player.getName() + "... maybe some day.");
    			}
    			else
    			{
    				chatItUp(player, "Humans aren't nearly as cute when they're dead.");
    			}

    			this.setAttackTarget(null);
    			this.angerLevel = this.chatTime = 0;
    		}
    	}
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short) angerLevel);
        nbttagcompound.setShort("TeleTimer", (short) teleTimer);
        nbttagcompound.setShort("TimeLeft", (short) timeLeft);
        nbttagcompound.setTag("SafePos", newDoubleNBTList(new double[] { safeX, safeY, safeZ }));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) 
    {
        super.readEntityFromNBT(nbttagcompound);
        angerLevel = nbttagcompound.getShort("Anger");
        teleTimer = nbttagcompound.getShort("TeleTimer");
        timeLeft = nbttagcompound.getShort("TimeLeft");
        NBTTagList nbttaglist = nbttagcompound.getTagList("SafePos", 10);
        safeX = nbttaglist.getDoubleAt(0);
        safeY = nbttaglist.getDoubleAt(1);
        safeZ = nbttaglist.getDoubleAt(2);
    }

    public boolean attackEntityFrom(DamageSource ds, float i) 
    {
        if (ds.getEntity() instanceof EntityPlayer && world.getDifficulty() != EnumDifficulty.PEACEFUL)
        {
            EntityPlayer player = (EntityPlayer)ds.getEntity();

            if (this.getAttackTarget() == null)
            {
            	this.chatTime = 0;
                int pokey = rand.nextInt(3);
                if (pokey == 2)
                {
                    chatItUp(player, "I'm not going easy on you!");
                }
                else if (pokey == 1) 
                {
                    chatItUp(player, "You're gonna regret that!");
                } 
                else 
                {
                    chatItUp(player, "Now you're in for it!");
                }
                
                this.setAttackTarget(player);
            } 
            else
            {
            	this.teleTimer -= 10;
            }

            if (ds.getEntity() instanceof EntityLivingBase)
            {
                becomeAngryAt((EntityLivingBase) ds.getEntity());
            }
        } 
        else
        {
            teleport(this.posX, this.posY, this.posZ, 8);
            extinguish();
            return false;
        }

        boolean flag = super.attackEntityFrom(ds, i);
        
        if (flag && this.getHealth() <= 0) 
        {
            spawnExplosionParticle();
            this.setDead();
        }

        return flag;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
    	boolean flag = false;

        if (this.attackTime <= 0 && entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY && entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY) 
        {
        	this.attackTime = 20;
            swingArm();
            flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), 7);
            if (entity != null && this.getAttackTarget() != null && entity == getAttackTarget() && entity instanceof EntityPlayer) 
            {
            	EntityPlayer player = (EntityPlayer) entity;
                if (player.getHealth() <= 0)
                {
                    int pokey = this.rand.nextInt(3);

                    if (pokey == 2) 
               	 	{
                        chatItUp((EntityPlayer) this.getAttackTarget(), "You want a medallion? Try being less pathetic.");
                    } 
                    else if (pokey == 1) 
               	 	{
                        chatItUp((EntityPlayer) this.getAttackTarget(),"Maybe some day, " + player.getName() + "... maybe some day.");
                    }
               	 	else 
               	 	{
                        chatItUp((EntityPlayer) this.getAttackTarget(),"Humans aren't nearly as cute when they're dead.");
                    }

                    this.setAttackTarget(null);
                    this.angerLevel = this.chatTime = 0;
               }           
            }
        }

		return flag;
    }

    @Override
    protected void dropFewItems(boolean var1, int var2) 
    {
    	dropItem(ItemsAether.victory_medal, 1);
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
    }

    @Override
    public boolean canDespawn() 
    {
        return true;
    }

    protected SoundEvent getHurtSound()
    {
    	return null;
    }
    
    protected SoundEvent getDeathSound()
    {
       return null;
    }

}