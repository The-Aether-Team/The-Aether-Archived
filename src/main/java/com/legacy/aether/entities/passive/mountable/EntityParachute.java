package com.legacy.aether.entities.passive.mountable;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityParachute extends Entity
{

	private EntityPlayer rider;

	public boolean isGoldenParachute;

    public EntityParachute(World world)
    {
        super(world);
        this.setSize(1, 1);
    }

	public EntityParachute(World world, double d, double d1, double d2)
	{
		this(world);

		this.setPositionAndRotation(d, d1, d2, rotationYaw, rotationPitch);
	}

	public EntityParachute(World world, EntityPlayer player, boolean isGolden)
	{
		this(world);

        if(player == null)
        {
        	this.setDead();
        }

        this.rider = player;

        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
        this.moveToEntityUsing();

        this.isGoldenParachute = isGolden;
        this.spawnExplosionParticle();
	}

	@Override
	protected void entityInit() { }

    public void spawnExplosionParticle()
    {
        if (this.world.isRemote)
        {
            for (int i = 0; i < 1; ++i)
            {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d3 = 10.0D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d0 * d3, this.posY + (double)(this.rand.nextFloat() * this.height) - d1 * d3, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d2 * d3, d0, d1, d2, new int[0]);
            }
        }
        else
        {
            this.world.setEntityState(this, (byte)20);
        }
    }

	@Override
	public void onUpdate()
	{
		if (this.isDead)
		{
		 	return;
		}

		if (this.rider == null)
		{
			this.setDead();
		}

		if (this.rider != null)
		{
			if (this.rider.motionY < -0.25F)
			{
				this.rider.motionY = -0.25F;
			}

			this.rider.fallDistance = 0F;

			this.moveToEntityUsing();
	        this.spawnExplosionParticle();
		}
	}

	private void moveToEntityUsing()
	{
		this.setPositionAndRotation(rider.posX, rider.getEntityBoundingBox().minY - (height / 2), rider.posZ, rider.rotationYaw, rider.rotationPitch);
	    this.motionX = rider.motionX;
	    this.motionY = rider.motionY;
	    this.motionZ = rider.motionZ;
	    this.rotationYaw = rider.rotationYaw;

	    if(isCollided())
	    {
	    	this.die();
	    }
	}

	private boolean isCollided()
	{
		List<?> list = this.world.getCollisionBoxes(this, getEntityBoundingBox().expand(0.0D, 0.0D, 0.0D));
		
		for (int size = 0; size < list.size();)
		{
			if (size == 0 && list.size() == 0)
			{
				return false;
			}

			AxisAlignedBB collision_list = (AxisAlignedBB) list.get(size);

			return collision_list != this.rider.getEntityBoundingBox();
		}

		return this.world.isMaterialInBB(getEntityBoundingBox(), Material.WATER);
	}

    @Override
    public void onCollideWithPlayer(EntityPlayer entityplayer) { }

	public void die()
	{
		if(this.rider != null)
		{
	        this.spawnExplosionParticle();
		}
		
		this.rider = null;
		this.isDead = true;
	}

    public static boolean entityHasRoomForCloud(World world, EntityPlayer player)
    {
    	AxisAlignedBB boundingBox = player.getEntityBoundingBox();

    	return world.getCollisionBoxes(player, boundingBox).size() == 0 && !world.isMaterialInBB(boundingBox, Material.WATER);
    }
    
    @Override
    public boolean isInRangeToRenderDist(double d)
    {
    	if(this.rider != null)
    	{
    		return this.rider.isInRangeToRenderDist(d);
    	}
    	else 
    	{
    		return super.isInRangeToRenderDist(d);
    	}
    }
    
    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

}