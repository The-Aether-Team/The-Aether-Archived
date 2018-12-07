package com.legacy.aether.entities.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityLightningKnife extends EntityProjectileBase
{

    public EntityLightningKnife(World world)
    {
		super(world);
	}

    public EntityLightningKnife(World world, EntityLivingBase thrower)
    {
		super(world, thrower);
	}

	@Override
	protected void onImpact(MovingObjectPosition object)
	{
		this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX, this.posY, this.posZ));
		this.setDead();
	}

	@Override
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {

    }

}