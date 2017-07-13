package com.legacy.aether.common.entities.block;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityTNTPresent extends Entity
{

    public int fuse;

    public EntityTNTPresent(World world)
    {
        super(world);
        this.fuse = 10;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
    }

    public EntityTNTPresent(World world, double d, double d1, double d2)
    {
        this(world);
        this.setPosition(d, d1, d2);
        float f = (float)(Math.random() * Math.PI * 2.0D);
        this.motionX = (double)(-MathHelper.sin(f * (float)Math.PI / 180.0F) * 0.02F);
        this.motionY = 0.20000000298023224D;
        this.motionZ = (double)(-MathHelper.cos(f * (float)Math.PI / 180.0F) * 0.02F);
        this.fuse = 10;
        this.prevPosX = d;
        this.prevPosY = d1;
        this.prevPosZ = d2;
    }

    protected void entityInit()
    {
    	
    }

    protected boolean canTriggerWalking()
    {
        return false;
    }

    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }

        if (this.fuse-- <= 0)
        {
            if (!this.worldObj.isRemote)
            {
                this.setDead();
                this.explode();
            }
        }
        else
        {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    private void explode()
    {
        float f = 0.4F;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
    }

    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("Fuse", (byte)this.fuse);
    }

    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        this.fuse = nbttagcompound.getByte("Fuse");
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

}