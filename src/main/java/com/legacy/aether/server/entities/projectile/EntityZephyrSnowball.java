package com.legacy.aether.server.entities.projectile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

import com.legacy.aether.server.entities.hostile.EntityZephyr;
import com.legacy.aether.server.items.ItemsAether;

public class EntityZephyrSnowball extends Entity implements IThrowableEntity
{
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    protected boolean inGround = false;
    public int field_9406_a = 0;
    public EntityLivingBase shootingEntity;
    private int ticksInAir = 0;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;

    public EntityZephyrSnowball(World world)
    {
        super(world);
        this.setSize(1.0F, 1.0F);
    }

    protected void entityInit() {}

    public EntityZephyrSnowball(World world, EntityLivingBase entityliving, double d, double d1, double d2)
    {
        super(world);
        this.shootingEntity = entityliving;
        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(entityliving.posX, entityliving.posY, entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        this.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        this.motionX = this.motionY = this.motionZ = 0.0D;
        d += this.rand.nextGaussian() * 0.4D;
        d1 += this.rand.nextGaussian() * 0.4D;
        d2 += this.rand.nextGaussian() * 0.4D;
        double d3 = (double)MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
        this.accelerationX = d / d3 * 0.08000000000000002D;
        this.accelerationY = d1 / d3 * 0.08000000000000002D;
        this.accelerationZ = d2 / d3 * 0.08000000000000002D;
    }

    public void onUpdate()
    {
        super.onUpdate();

        if (this.motionX * this.motionY * this.motionZ < 0.1D && this.ticksInAir > 40 && !this.worldObj.isRemote)
        {
            this.setDead();
        }

        if (this.ticksInAir > 600 && !this.worldObj.isRemote)
        {
            this.setDead();
        }

        if (this.field_9406_a > 0)
        {
            --this.field_9406_a;
        }

        if (this.inGround)
        {
            this.inGround = false;
            this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
            this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
            this.ticksInAir = 0;
        }
        else
        {
            ++this.ticksInAir;
        }

        Vec3d var15 = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult movingobjectposition = this.worldObj.rayTraceBlocks(var15, vec3d1, true);
        var15 = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (movingobjectposition != null)
        {
            vec3d1 = new Vec3d(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;

        for (int f = 0; f < list.size(); ++f)
        {
            Entity f1 = (Entity)list.get(f);

            if (f1.canBeCollidedWith() && (f1 != this.shootingEntity || this.ticksInAir >= 25))
            {
                float k = 0.3F;
                AxisAlignedBB f3 = f1.getEntityBoundingBox().expand((double)k, (double)k, (double)k);
                RayTraceResult movingobjectposition1 = f3.calculateIntercept(var15, vec3d1);

                if (movingobjectposition1 != null)
                {
                    double d1 = var15.distanceTo(movingobjectposition1.hitVec);

                    if (d1 < d || d == 0.0D)
                    {
                        entity = f1;
                        d = d1;
                    }
                }
            }
        }

        if (entity != null)
        {
            movingobjectposition = new RayTraceResult(entity);
        }

        if (movingobjectposition != null)
        {
            if (movingobjectposition.entityHit != null && movingobjectposition.entityHit != this.shootingEntity)
            {
                if (movingobjectposition.entityHit instanceof EntityPlayer && ((EntityPlayer)movingobjectposition.entityHit).inventory.armorInventory[0] != null && ((EntityPlayer)movingobjectposition.entityHit).inventory.armorInventory[0].getItem() == ItemsAether.sentry_boots)
                {
                    this.setDead();
                }
                else if (movingobjectposition.entityHit instanceof EntityPlayer && ((EntityPlayer)movingobjectposition.entityHit).capabilities.isCreativeMode)
                {
                    this.setDead();
                }
                else if (movingobjectposition.entityHit != null && !(movingobjectposition.entityHit instanceof EntityZephyr))
                {
                    movingobjectposition.entityHit.motionX += this.motionX;
                    movingobjectposition.entityHit.motionY += 0.5D;
                    movingobjectposition.entityHit.motionZ += this.motionZ;
                }
            }

            if (!this.worldObj.isRemote)
            {
                this.setDead();
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var16) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float var17 = 0.95F;

        if (this.handleWaterMovement())
        {
            for (int var19 = 0; var19 < 4; ++var19)
            {
                float var18 = 0.25F;
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)var18, this.posY - this.motionY * (double)var18, this.posZ - this.motionZ * (double)var18, this.motionX, this.motionY, this.motionZ);
            }

            var17 = 0.8F;
        }

        this.motionX += this.accelerationX;
        this.motionY += this.accelerationY;
        this.motionZ += this.accelerationZ;
        this.motionX *= (double)var17;
        this.motionY *= (double)var17;
        this.motionZ *= (double)var17;
        this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        this.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("xTile", (short)this.xTile);
        nbttagcompound.setShort("yTile", (short)this.yTile);
        nbttagcompound.setShort("zTile", (short)this.zTile);
        nbttagcompound.setByte("shake", (byte)this.field_9406_a);
        nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        this.xTile = nbttagcompound.getShort("xTile");
        this.yTile = nbttagcompound.getShort("yTile");
        this.zTile = nbttagcompound.getShort("zTile");
        this.field_9406_a = nbttagcompound.getByte("shake") & 255;
        this.inGround = nbttagcompound.getByte("inGround") == 1;
    }

    public float getCollisionBorderSize()
    {
        return 1.0F;
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    public Entity getThrower()
    {
        return null;
    }

    public void setThrower(Entity entity) {}

}