package com.legacy.aether.common.entities.projectile.crystals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.bosses.EntityFireMinion;
import com.legacy.aether.common.entities.bosses.sun_spirit.EntitySunSpirit;

public class EntityFireBall extends EntityFlying
{

    public float[] sinage;

    public double smotionX;
    public double smotionY;
    public double smotionZ;

    public int life;
    public int lifeSpan;

    public boolean smacked;

    public EntityFireBall(World var1)
    {
        super(var1);
        this.lifeSpan = 300;
        this.life = this.lifeSpan;
        this.setSize(0.9F, 0.9F);
        this.sinage = new float[3];
        this.isImmuneToFire = true;
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);

        for (int var2 = 0; var2 < 3; ++var2)
        {
            this.sinage[var2] = this.rand.nextFloat() * 6.0F;
        }
    }

    public EntityFireBall(World var1, double x, double y, double z)
    {
        this(var1);

        this.setPositionAndRotation(x, y, z, this.rotationYaw, this.rotationPitch);

        this.smotionX = (0.2D + (double)this.rand.nextFloat() * 0.15D) * (this.rand.nextInt(2) == 0 ? 1.0D : -1.0D);
        this.smotionY = (0.2D + (double)this.rand.nextFloat() * 0.15D) * (this.rand.nextInt(2) == 0 ? 1.0D : -1.0D);
        this.smotionZ = (0.2D + (double)this.rand.nextFloat() * 0.15D) * (this.rand.nextInt(2) == 0 ? 1.0D : -1.0D);
    }

    public void onUpdate()
    {
        super.onUpdate();

        --this.life;

        if (this.life <= 0)
        {
            this.splode();
            this.isDead = true;
        }

        this.updateAnims();
    }

    public void splode()
    {
    	this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 2.0F, this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.2F);

        for (int var1 = 0; var1 < 40; ++var1)
        {
            double var2 = (double)((this.rand.nextFloat() - 0.5F) * 0.5F);
            double var4 = (double)((this.rand.nextFloat() - 0.5F) * 0.5F);
            double var6 = (double)((this.rand.nextFloat() - 0.5F) * 0.5F);

            this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, var2, var4, var6);
        }
    }

    public void updateAnims()
    {
        for (int var1 = 0; var1 < 3; ++var1)
        {
            this.sinage[var1] += 0.3F + (float)var1 * 0.13F;

            if (this.sinage[var1] > ((float)Math.PI * 2F))
            {
                this.sinage[var1] -= ((float)Math.PI * 2F);
            }
        }
    }

    public void updateAITasks()
    {
    	super.updateAITasks();
        this.motionX = this.smotionX;
        this.motionY = this.smotionY;
        this.motionZ = this.smotionZ;

        if (this.isCollided)
        {
            int var1 = MathHelper.floor(this.posX);
            int var2 = MathHelper.floor(this.getEntityBoundingBox().minY);
            int var3 = MathHelper.floor(this.posZ);

            if (this.smotionX > 0.0D && this.world.getBlockState(new BlockPos(var1 + 1, var2, var3)).getBlock() != Blocks.AIR)
            {
                this.motionX = this.smotionX = -this.smotionX;
            }
            else if (this.smotionX < 0.0D && this.world.getBlockState(new BlockPos(var1 - 1, var2, var3)).getBlock() != Blocks.AIR)
            {
                this.motionX = this.smotionX = -this.smotionX;
            }

            if (this.smotionY > 0.0D && this.world.getBlockState(new BlockPos(var1, var2 + 1, var3)).getBlock() != Blocks.AIR)
            {
                this.motionY = this.smotionY = -this.smotionY;
            }
            else if (this.smotionY < 0.0D && this.world.getBlockState(new BlockPos(var1, var2 - 1, var3)).getBlock() != Blocks.AIR)
            {
                this.motionY = this.smotionY = -this.smotionY;
            }

            if (this.smotionZ > 0.0D && this.world.getBlockState(new BlockPos(var1, var2, var3 + 1)).getBlock() != Blocks.AIR)
            {
                this.motionZ = this.smotionZ = -this.smotionZ;
            }
            else if (this.smotionZ < 0.0D && this.world.getBlockState(new BlockPos(var1, var2, var3 - 1)).getBlock() != Blocks.AIR)
            {
                this.motionZ = this.smotionZ = -this.smotionZ;
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1)
    {
        super.writeEntityToNBT(var1);
        var1.setShort("life", (short)this.life);
        var1.setTag("selfMotion", this.newDoubleNBTList(new double[] {this.smotionX, this.smotionY, this.smotionZ}));
        var1.setBoolean("smacked", this.smacked);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1)
    {
        super.readEntityFromNBT(var1);
        this.life = var1.getShort("life");
        this.smacked = var1.getBoolean("smacked");
        NBTTagList var2 = var1.getTagList("selfMotion", 10);
        this.smotionX = var2.getDoubleAt(0);
        this.smotionY = var2.getDoubleAt(1);
        this.smotionZ = var2.getDoubleAt(2);
    }

    @Override
    public void applyEntityCollision(Entity var1)
    {
        super.applyEntityCollision(var1);
        boolean var2 = false;

        if (var1 != null && var1 instanceof EntityLivingBase && !(var1 instanceof EntityFireBall))
        {
            if (!(var1 instanceof EntitySunSpirit) && !(var1 instanceof EntityFireMinion))
            {
                var2 = var1.attackEntityFrom(DamageSource.causeMobDamage(this), 5);

                if (var2)
                {
                    this.setFire(100);
                }
            }
        }

        if (var2)
        {
            this.splode();
            this.isDead = true;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource var1, float var2)
    {
        if (var1.getEntity() != null)
        {
            Vec3d var3 = var1.getEntity().getLookVec();

            if (var3 != null)
            {
                this.smotionX = var3.xCoord;
                this.smotionY = var3.yCoord;
                this.smotionZ = var3.zCoord;
            }

            this.smacked = true;
            return true;
        }
        else
        {
            return false;
        }
    }

}