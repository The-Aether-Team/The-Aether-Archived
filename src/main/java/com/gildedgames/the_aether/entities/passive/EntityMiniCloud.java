package com.gildedgames.the_aether.entities.passive;

import com.gildedgames.the_aether.entities.projectile.crystals.EntityIceyBall;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityMiniCloud extends EntityFlying
{

    public EntityPlayer owner;

    public int shotTimer, lifeSpan;

    public boolean hasOwner, direction, hasSpawned = false;

    public double targetX, targetY, targetZ;

    public EntityMiniCloud(World worldObj)
    {
        super(worldObj);
        this.entityCollisionReduction = 1.75F;
    }

    public EntityMiniCloud(World worldObj, EntityPlayer owner, int direction)
    {
        this(worldObj);
        this.setSize(0.5F, 0.45F);
        this.owner = owner;
        this.direction = direction == 0;
        this.lifeSpan = 3600;
        this.getTargetPos();
        this.hasOwner = true;
        this.noClip = true;
        this.setPosition(this.targetX, this.targetY, this.targetZ);
        this.rotationPitch = this.owner.rotationPitch;
        this.rotationYaw = this.owner.rotationYaw;
    }

    public boolean isInRangeToRenderDist(double var1)
    {
        return true;
    }

    public void getTargetPos()
    {
        if (this.getDistance(this.owner) > 2.0F)
        {
            this.targetX = this.owner.posX;
            this.targetY = this.owner.posY - 0.10000000149011612D;
            this.targetZ = this.owner.posZ;
        }
        else
        {
            double var1 = (double)this.owner.rotationYaw;

            if (this.direction)
            {
                var1 -= 90.0D;
            }
            else
            {
                var1 += 90.0D;
            }

            var1 /= -(180D / Math.PI);
            this.targetX = this.owner.posX + Math.sin(var1) * 1.05D;
            this.targetY = this.owner.posY - 0.10000000149011612D;
            this.targetZ = this.owner.posZ + Math.cos(var1) * 1.05D;
        }
    }

    public boolean atShoulder()
    {
        double var1 = this.posX - this.targetX;
        double var3 = this.posY - this.targetY;
        double var5 = this.posZ - this.targetZ;
        return Math.sqrt(var1 * var1 + var3 * var3 + var5 * var5) < 0.3D;
    }

    public void approachTarget()
    {
        double var1 = this.targetX - this.posX;
        double var3 = this.targetY - this.posY;
        double var5 = this.targetZ - this.posZ;
        double var7 = Math.sqrt(var1 * var1 + var3 * var3 + var5 * var5) * 3.25D;
        this.motionX = (this.motionX + var1 / var7) / 2.0D;
        this.motionY = (this.motionY + var3 / var7) / 2.0D;
        this.motionZ = (this.motionZ + var5 / var7) / 2.0D;
        Math.atan2(var1, var5);
    }

    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setShort("LifeSpan", (short)this.lifeSpan);
        var1.setShort("ShotTimer", (short)this.shotTimer);
        this.hasOwner = this.owner != null;
        var1.setBoolean("GotPlayer", this.hasOwner);
        var1.setBoolean("direction", this.direction);
    }

    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.lifeSpan = var1.getShort("LifeSpan");
        this.shotTimer = var1.getShort("ShotTimer");
        this.hasOwner = var1.getBoolean("GotPlayer");
        this.direction = var1.getBoolean("direction");
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();

        --this.lifeSpan;

        if (this.lifeSpan <= 0)
        {
            this.spawnExplosionParticle();
            this.isDead = true;
        }
        else
        {
        	if (!this.hasOwner || this.owner == null)
        	{
        		this.setDead();
        		return;
        	}

            if (this.hasOwner && this.owner == null)
            {
            	this.hasOwner = false;
                this.setDead();
                return;
            }

            if (this.shotTimer > 0)
            {
                --this.shotTimer;
            }

            if (!this.owner.isDead)
            {
                this.getTargetPos();

                if (this.atShoulder())
                {
                    this.motionX *= 0.65D;
                    this.motionY *= 0.65D;
                    this.motionZ *= 0.65D;

        			this.rotationYaw = this.owner.rotationYaw + (this.direction ? 1F : -1F);;
        			this.rotationPitch = this.owner.rotationPitch;
        			this.rotationYawHead = this.owner.rotationYawHead;

                    if (this.shotTimer <= 0 && this.owner.swingProgress > 0.0F)
                    {
                        float var7 = this.rotationYaw - (this.direction ? 1.0F : -1.0F);
                        double var1 = this.posX + Math.sin((double)var7 / -(180D / Math.PI)) * 1.6D;
                        double var3 = this.posY - -1.0D;
                        double var5 = this.posZ + Math.cos((double)var7 / -(180D / Math.PI)) * 1.6D;
                        EntityIceyBall iceCrystal = new EntityIceyBall(this.world, var1, var3, var5, true);
                        Vec3d var9 = this.getLookVec();

                        iceCrystal.shootingEntity = this.owner;

                        if (var9 != null)
                        {
                        	iceCrystal.smotionX = var9.x * 1.5D;
                        	iceCrystal.smotionY = var9.y * 1.5D;
                        	iceCrystal.smotionZ = var9.z * 1.5D;
                        }

                        iceCrystal.smacked = true;

                        if (!this.world.isRemote)
                        {
                            this.world.spawnEntity(iceCrystal);
                        }

                        this.world.playSound(this.posX, this.posY, this.posZ, SoundsAether.zephyr_shoot, SoundCategory.NEUTRAL, 0.75F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);

                        this.shotTimer = 40;
                    }
                }
                else
                {
                    this.approachTarget();
                }
            }
            else
            {
                this.spawnExplosionParticle();
                this.isDead = true;
            }
        }
    }

    public boolean attackEntityFrom(DamageSource var1, float var2)
    {
    	if (var1.getImmediateSource() == this.owner || var1.getDamageType() == "inWall")
    	{
    		return false;
    	}

        return super.attackEntityFrom(var1, var2);
    }

}