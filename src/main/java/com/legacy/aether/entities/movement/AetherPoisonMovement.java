package com.legacy.aether.entities.movement;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class AetherPoisonMovement 
{

    public int ticks = 0;

    public double rotD, motD;

	private EntityLivingBase entity;

	public AetherPoisonMovement(EntityLivingBase entity)
	{
		this.entity = entity;
	}

	public void onUpdate()
	{
        int timeUntilHit = this.ticks % 50;

        if (this.entity.isDead)
        {
        	this.ticks = 0;
        }
        else if (this.ticks < 0)
        {
        	this.ticks++;
        }
        else if (this.ticks > 0)
        {
            this.ticks--;

            if (timeUntilHit == 0) 
            {
                this.entity.attackEntityFrom(causePoisonDamage(), 1.0F);
            }

            this.distractEntity();
        }
	}

    public void inflictPoison(int ticks)
    {
    	if (this.ticks >= 0)
    	{
        	this.ticks = ticks;
    	}
    }

    public void inflictCure(int ticks) 
    {
    	this.ticks = -ticks;
    }

    public void distractEntity()
    {
    	double gaussian = this.entity.worldObj.rand.nextGaussian();
        double newMotD = 0.1D * gaussian;
        double newRotD = (Math.PI / 4D) * gaussian;

        this.motD = 0.2D * newMotD + (0.8D) * this.motD;
        this.entity.motionX += this.motD;
        this.entity.motionZ += this.motD;
        this.rotD = 0.125D * newRotD + (1.0D - 0.125D) * this.rotD;

        this.entity.rotationYaw = (float)((double)this.entity.rotationYaw + rotD);
        this.entity.rotationPitch = (float)((double)this.entity.rotationPitch + rotD);
    }

    public static DamageSource causePoisonDamage()
    {
        return new DamageSource("aether_legacy.poison").setDamageBypassesArmor();
    }

}