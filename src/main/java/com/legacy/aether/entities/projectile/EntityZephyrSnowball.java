package com.legacy.aether.entities.projectile;

import com.legacy.aether.items.ItemsAether;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityZephyrSnowball extends EntityThrowable
{

	private int ticksInAir;

    public EntityZephyrSnowball(World worldIn)
    {
    	super(worldIn);

        this.setSize(1.0F, 1.0F);
    }

	public EntityZephyrSnowball(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

    public EntityZephyrSnowball(World worldIn, EntityLivingBase throwerIn)
    {
    	super(worldIn, throwerIn);
    }

    @Override
    protected void entityInit()
    {
    	this.setNoGravity(true);
    }

    @Override
    public void onUpdate()
    {
    	super.onUpdate();

    	if (!this.onGround)
    	{
    		++this.ticksInAir;
    	}

    	if (this.ticksInAir > 600)
    	{
    		this.setDead();
    	}
    }

	@Override
	protected void onImpact(RayTraceResult result)
	{
		if (result.typeOfHit == Type.ENTITY)
		{
			Entity entity = result.entityHit;

			if (entity instanceof EntityPlayer)
			{
				EntityPlayer player = ((EntityPlayer)entity);

				if (player.inventory.armorInventory.get(0).getItem() == ItemsAether.sentry_boots)
				{
					return;
				}
			}

			result.entityHit.motionX += this.motionX * 1.5F;
			result.entityHit.motionY += 0.5D;
			result.entityHit.motionZ += this.motionZ * 1.5F;
		}

        this.setDead();
	}

}