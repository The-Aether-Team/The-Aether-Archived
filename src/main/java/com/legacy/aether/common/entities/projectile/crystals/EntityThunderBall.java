package com.legacy.aether.common.entities.projectile.crystals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityThunderBall extends EntityFlying 
{

	public float sinage[];

	public EntityLivingBase target;

	public boolean firstRun;

	public int life, lifeSpan;

	private static final float sponge = 180F / 3.141593F;

	public EntityThunderBall(World world) 
	{
		super(world);
		this.lifeSpan = 200;
		this.life = this.lifeSpan;
		this.firstRun = true;
		this.sinage = new float[3];
		this.isImmuneToFire = true;

		this.setSize(0.7F, 0.7F);

		for(int i = 0; i < 3; i ++) 
		{
			this.sinage[i] = this.rand.nextFloat() * 6F;
		}
	}

	public EntityThunderBall(World world, double x, double y, double z, EntityLivingBase player)
	{
		this(world);

		this.setPosition(x, y, z);

		this.target = player;
	}

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.setHealth(20.0F);
    }

    @Override
	public void onUpdate()
	{
		super.onUpdate();

		this.life--;

		if(this.firstRun && this.target == null)
		{
			this.target = (EntityLivingBase)findPlayerToAttack();
			this.firstRun = false;
		}

		if(this.target == null || this.target.isDead || this.target.getHealth() <= 0) 
		{
			this.isDead = true;
		} 

		else if(life <= 0)
		{
			EntityLightningBolt thunder = new EntityLightningBolt(this.world, this.posX, this.posY, this.posZ, false);
			this.world.spawnEntity(thunder);
			this.isDead = true;
		} 
		else 
		{
			this.updateAnims();
			this.faceEntity(this.target, 10F, 10F);
			this.moveTowardsTarget(this.target, 0.02D);
		}
	}	

	public void moveTowardsTarget(Entity target, double speed) 
	{
		double angle1 = this.rotationYaw / sponge;
		this.motionX -= Math.sin(angle1) * speed;
		this.motionZ += Math.cos(angle1) * speed;
		
		double a = target.posY - 0.75F;
		
		if(a < this.getEntityBoundingBox().minY - 0.5F)
		{
			this.motionY -= (speed / 2);
		} 
		else if(a > this.getEntityBoundingBox().minY + 0.5F) 
		{
			this.motionY += (speed/ 2);
		} 
		else 
		{
			this.motionY +=  (a - this.getEntityBoundingBox().minY) * (speed / 2);
		}

		if(this.onGround) 
		{
			this.onGround = false;
			this.motionY = 0.1F;
		}
	}

	public void updateAnims()
	{	
		for(int i = 0; i < 3; i ++)
		{
			this.sinage[i] += (0.3F + ((float)i * 0.13F));

			if(this.sinage[i] > 3.141593F * 2F) 
			{
				this.sinage[i] -= (3.141593F * 2F);
			}
		}
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("LifeLeft", (short)life);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) 
    {
        super.readEntityFromNBT(nbttagcompound);
        life = nbttagcompound.getShort("LifeLeft");
    }

	public Entity findPlayerToAttack()
    {
        EntityPlayer entityplayer = world.getClosestPlayerToEntity(this, 16D);
        
        if(entityplayer != null && canEntityBeSeen(entityplayer))
        {
            return entityplayer;
        }
        else
        {
            return null;
        }
    }

	public void applyEntityCollision(Entity entity)
	{
		super.applyEntityCollision(entity);
		
        if(entity != null && target != null && entity == target) 
        {
			boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
			
			if(flag) 
			{
				this.moveTowardsTarget(entity, -0.1D);
			}
        }
    }

	public boolean attackEntityFrom(Entity entity, float i) 
	{
		if(entity != null)
		{
			this.moveTowardsTarget(entity, -0.15D - ((double)i / 8D));
			return true;
		}
		return false;
	}

}