package com.legacy.aether.entities.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityLightningKnife extends EntityThrowable
{

    public EntityLightningKnife(World worldIn)
    {
    	super(worldIn);
    }

	public EntityLightningKnife(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

    public EntityLightningKnife(World worldIn, EntityLivingBase throwerIn)
    {
    	super(worldIn, throwerIn);
    }

	@Override
	protected void onImpact(RayTraceResult result)
	{
		if (result.typeOfHit != Type.MISS)
		{
            EntityLightningBolt lightning = new EntityLightningBolt(this.worldObj, result.hitVec.xCoord, result.hitVec.yCoord, result.hitVec.zCoord, false);

            this.worldObj.spawnEntityInWorld(lightning);
		}

        this.setDead();
	}

}