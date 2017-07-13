package com.legacy.aether.common.entities.movement;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class AetherPoisonMovement 
{

    public int poisonTime = 0;

    public double rotD, motD;

	private EntityLivingBase entityLiving;

	public AetherPoisonMovement(EntityLivingBase entity)
	{
		this.entityLiving = entity;
	}

	public void onUpdate()
	{
        int timeUntilHit = this.poisonTime % 50;

        if (this.entityLiving.isDead)
        {
        	this.poisonTime = 0;

        	return;
        }

        if (this.poisonTime < 0)
        {
        	this.poisonTime++;

            return;
        }

        if (this.poisonTime == 0)
        {
            return;
        }

        this.distractEntity();

        if (timeUntilHit == 0) 
        {
            this.entityLiving.attackEntityFrom(DamageSource.generic, 1);
        }

        this.poisonTime--;
	}

    public boolean afflictPoison() 
    {
        this.poisonTime = 500;

        return true;
    }

    public boolean curePoison(int i) 
    {
        if (this.poisonTime == -100)
        {
            return false;
        }

        this.poisonTime = -100 - i;

        return true;
    }

    public void distractEntity()
    {
    	double gaussian = this.entityLiving.worldObj.rand.nextGaussian();
        double newMotD = 0.1D * gaussian;
        double newRotD = (Math.PI / 4D) * gaussian;

        this.motD = 0.2D * newMotD + (0.8D) * this.motD;
        this.entityLiving.motionX += this.motD;
        this.entityLiving.motionZ += this.motD;
        this.rotD = 0.125D * newRotD + (1.0D - 0.125D) * this.rotD;

        this.entityLiving.rotationYaw = (float)((double)this.entityLiving.rotationYaw + rotD);
        this.entityLiving.rotationPitch = (float)((double)this.entityLiving.rotationPitch + rotD);
    }

}