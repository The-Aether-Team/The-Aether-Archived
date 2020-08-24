package com.gildedgames.the_aether.entities.projectile;

import com.gildedgames.the_aether.items.ItemsAether;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
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

    	if (this.ticksInAir > 400)
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

				if (!player.isActiveItemStackBlocking())
				{
					result.entityHit.motionY += 0.5D;
				}
				else
				{
					player.getActiveItemStack().damageItem(1, player);

					if (player.getActiveItemStack().getCount() <= 0)
					{
						this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
					}
					else
					{
						this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
					}
				}

				result.entityHit.motionX += this.motionX * 1.5F;
				result.entityHit.motionZ += this.motionZ * 1.5F;
			}
		}

        this.setDead();
	}

}