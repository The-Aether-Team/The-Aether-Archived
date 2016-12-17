package com.legacy.aether.server.entities.projectile.darts;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

import com.legacy.aether.server.entities.projectile.EntityPoisonNeedle;

public abstract class EntityDartBase extends EntityArrow implements IProjectile
{

    private int ticksInAir;

    public EntityDartBase(World worldIn)
    {
        super(worldIn);
    }

	public EntityDartBase(World worldIn, EntityLivingBase shooter)
	{
		super(worldIn, shooter);
	}

    public void onUpdate()
    {
        super.onUpdate();

        if (!(this instanceof EntityPoisonNeedle))
        {
            this.motionY += (double)0.05F;
        }

        if (this.ticksInAir == 500)
        {
        	this.setDead();
        }

        if (!this.onGround)
        {
        	++this.ticksInAir;
        }
    }
   
    public boolean func_189652_ae()
    {
        return true;
    }

    public void func_189654_d(boolean flight)
    {

    }

}