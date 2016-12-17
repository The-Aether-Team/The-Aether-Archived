package com.legacy.aether.server.entities.projectile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLightningKnife extends Entity
{

    private EntityLivingBase thrower;

    private int ticksInAir;

    public EntityLightningKnife(World world)
    {
        super(world);
        this.setSize(0.25F, 0.25F);
    }

    protected void entityInit() 
    {
    	
    }

    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10.0D;

        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }

    public EntityLightningKnife(World world, EntityLivingBase entityliving)
    {
        super(world);
        this.thrower = entityliving;
        this.setSize(0.25F, 0.25F);
        this.setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        float f = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
    }

    public EntityLightningKnife(World world, double d, double d1, double d2)
    {
        super(world);

        this.setSize(0.25F, 0.25F);
        this.setPositionAndRotation(d, d1, d2, this.rotationYaw, this.rotationPitch);
    }

    public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= (double)f2;
        y /= (double)f2;
        z /= (double)f2;
        x += this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y += this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z += this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x *= (double) velocity;
        y *= (double) velocity;
        z *= (double) velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f3 = MathHelper.sqrt_double(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, (double)f3) * 180.0D / Math.PI);
    }

    @Override
    public void setVelocity(double d, double d1, double d2)
    {
        this.motionX = d;
        this.motionY = d1;
        this.motionZ = d2;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(d * d + d2 * d2);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(d, d2) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(d1, (double)f) * 180.0D / Math.PI);
        }
    }

    @Override
    public void onUpdate()
    {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;

        super.onUpdate();

        ++this.ticksInAir;

        Vec3d var15 = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        RayTraceResult movingobjectposition = this.worldObj.rayTraceBlocks(var15, vec3d1, false, true, true);

        var15 = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (movingobjectposition != null)
        {
            vec3d1 = new Vec3d(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        if (!this.worldObj.isRemote)
        {
            Entity f = null;
            List<?> f1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double f2 = 0.0D;

            for (int f3 = 0; f3 < f1.size(); ++f3)
            {
                Entity entity1 = (Entity)f1.get(f3);

                if (entity1.canBeCollidedWith() && (entity1 != this.thrower || this.ticksInAir >= 5))
                {
                    float f4 = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f4, (double)f4, (double)f4);
                    RayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(var15, vec3d1);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = var15.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < f2 || f2 == 0.0D)
                        {
                            f = entity1;
                            f2 = d1;
                        }
                    }
                }
            }

            if (f != null)
            {
                movingobjectposition = new RayTraceResult(f);
            }
        }

        if (movingobjectposition != null)
        {
            EntityLightningBolt lightning;

            int var17;
            int var16;
            int var20;

            if (movingobjectposition.entityHit != null)
            {
                if (!movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.thrower), 0.0F))
                {

                }

                var16 = MathHelper.floor_double(movingobjectposition.entityHit.getEntityBoundingBox().minX);
                var17 = MathHelper.floor_double(movingobjectposition.entityHit.getEntityBoundingBox().minY);
                var20 = MathHelper.floor_double(movingobjectposition.entityHit.getEntityBoundingBox().minZ);

                lightning = new EntityLightningBolt(this.worldObj, (double)var16, (double)var17, (double)var20, false);
                lightning.setLocationAndAngles((double)var16, (double)var17, (double)var20, this.rotationYaw, 0.0F);

                this.worldObj.spawnEntityInWorld(lightning);
            }
            else
            {
                var16 = MathHelper.floor_double(this.posX);
                var17 = MathHelper.floor_double(this.posY);
                var20 = MathHelper.floor_double(this.posZ);

                lightning = new EntityLightningBolt(this.worldObj, this.posX, this.posY, this.posZ, false);
                lightning.setLocationAndAngles((double)var16, (double)var17, (double)var20, this.rotationYaw, 0.0F);

                this.worldObj.spawnEntityInWorld(lightning);
            }

            for (var16 = 0; var16 < 8; ++var16)
            {
                this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }

            this.setDead();
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;

        float var18 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var18) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {

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

        float var19 = 0.99F;
        float var21 = 0.03F;

        if (this.isInWater())
        {
            for (int var22 = 0; var22 < 4; ++var22)
            {
                float var23 = 0.25F;
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)var23, this.posY - this.motionY * (double)var23, this.posZ - this.motionZ * (double)var23, this.motionX, this.motionY, this.motionZ);
            }

            var19 = 0.8F;
        }

        this.motionX *= (double)var19;
        this.motionY *= (double)var19;
        this.motionZ *= (double)var19;
        this.motionY -= (double)var21;
        this.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {

    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {

    }

}