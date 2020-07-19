package com.legacy.aether.entities.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

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
		if (!this.world.isRemote)
		{
			if (result.typeOfHit == Type.ENTITY)
			{
				summonLightning(result);
			}
			else if (result.typeOfHit == Type.BLOCK)
			{
				if (this.world.getBlockState(result.getBlockPos()).isFullBlock())
				{
					summonLightning(result);
				}
			}
		}
	}

	private void summonLightning(RayTraceResult result)
	{
		if (this.world instanceof WorldServer)
		{
			((WorldServer)this.world).addWeatherEffect(new EntityLightningBolt(this.world, result.hitVec.x, result.hitVec.y, result.hitVec.z, false));
		}

		this.setDead();
	}
}