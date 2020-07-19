package com.legacy.aether.entities.projectile;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityTippedPhoenixArrow extends EntityTippedArrow implements IProjectile
{
    private int duration = 200;

    public EntityTippedPhoenixArrow(World worldIn)
    {
        super(worldIn);
    }

    public EntityTippedPhoenixArrow(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
    }

    public void onUpdate()
    {
        super.onUpdate();

        if (this.world.isRemote)
        {
            if (this.inGround)
            {
                if (this.timeInGround % 5 == 0)
                {
                    this.spawnPotionParticles(1);
                }
            }
            else
            {
                this.spawnPotionParticles(2);
            }
        }
    }

    private void spawnPotionParticles(int particleCount)
    {
        for (int j = 0; j < particleCount; ++j)
        {
            this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextGaussian() / 5D), this.posY + (this.rand.nextGaussian() / 5D), this.posZ + (this.rand.nextGaussian() / 3D), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("Duration"))
        {
            this.duration = compound.getInteger("Duration");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setInteger("Duration", this.duration);
    }

    protected void arrowHit(EntityLivingBase living)
    {
        super.arrowHit(living);

        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, (EntityLivingBase) this.shootingEntity) > 0)
        {
            living.setFire(10);
        }
    }
}
