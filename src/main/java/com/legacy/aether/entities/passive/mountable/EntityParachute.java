package com.legacy.aether.entities.passive.mountable;

import io.netty.buffer.ByteBuf;

import java.util.List;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityParachute extends Entity implements IEntityAdditionalSpawnData
{

	private EntityPlayer ridingPlayer;

	public boolean isGoldenParachute;

    public EntityParachute(World world)
    {
        super(world);

        this.setSize(1.0F, 1.0F);
    }

	public EntityParachute(World world, EntityPlayer player, boolean isGolden)
	{
		this(world);

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.ridingPlayer = player;
        this.isGoldenParachute = isGolden;

        this.moveToEntityUsing();
        this.spawnExplosionParticle();
	}

	@Override
	protected void entityInit() 
	{

	}

    public void spawnExplosionParticle()
    {
        if (this.worldObj.isRemote)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d0 * d3, this.posY  - 0.5D + (double)(this.rand.nextFloat() * this.height) - d1 * d3, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d2 * d3, d0, d1, d2);
        }
        else
        {
            this.worldObj.setEntityState(this, (byte)20);
        }
    }

	@Override
	public void onUpdate()
	{
		if (this.isDead || this.ridingPlayer == null)
		{
			this.setDead();

		 	return;
		}

		if (this.ridingPlayer != null)
		{
			if (this.ridingPlayer.motionY < -0.25F)
			{
				this.ridingPlayer.motionY = -0.25F;
			}

			this.ridingPlayer.fallDistance = 0F;

			this.moveToEntityUsing();
	        this.spawnExplosionParticle();
		}
	}

	private void moveToEntityUsing()
	{
		this.setPositionAndRotation(this.ridingPlayer.posX, this.ridingPlayer.boundingBox.minY - (this.height / 2) - 0.5D, this.ridingPlayer.posZ, this.ridingPlayer.rotationYaw, this.ridingPlayer.rotationPitch);

	    this.motionX = this.ridingPlayer.motionX;
	    this.motionY = this.ridingPlayer.motionY;
	    this.motionZ = this.ridingPlayer.motionZ;
	    this.rotationYaw = this.ridingPlayer.rotationYaw;

	    if(this.isCollided())
	    {
	    	this.die();
	    }
	}

	private boolean isCollided()
	{
		List<?> list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox);

		for (int size = 0; size < list.size();)
		{
			if (size == 0 && list.size() == 0)
			{
				return false;
			}

			AxisAlignedBB collision_list = (AxisAlignedBB) list.get(size);

			return collision_list != this.ridingPlayer.boundingBox;
		}

		return this.worldObj.isAABBInMaterial(this.boundingBox, Material.water);
	}

    @Override
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {

    }

	public void die()
	{
		if(this.ridingPlayer != null)
		{
	        this.spawnExplosionParticle();
		}
		
		this.ridingPlayer = null;
		this.isDead = true;
	}

    public static boolean entityHasRoomForCloud(World world, EntityPlayer player)
    {
    	AxisAlignedBB boundingBox = player.boundingBox;

    	return world.getCollidingBoundingBoxes(player, boundingBox).size() == 0 && !world.isAABBInMaterial(boundingBox, Material.water);
    }

    @Override
    public boolean isInRangeToRenderDist(double d)
    {
    	if(this.ridingPlayer != null)
    	{
    		return this.ridingPlayer.isInRangeToRenderDist(d);
    	}

		return super.isInRangeToRenderDist(d);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) 
	{

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{

	}

	@Override
	public void writeSpawnData(ByteBuf buffer) 
	{
		buffer.writeBoolean(this.isGoldenParachute);
		buffer.writeInt(this.ridingPlayer.getEntityId());
	}

	@Override
	public void readSpawnData(ByteBuf buffer) 
	{
		this.isGoldenParachute = buffer.readBoolean();
		this.ridingPlayer = (EntityPlayer) this.worldObj.getEntityByID(buffer.readInt());
	}

}