package com.legacy.aether.common.entities.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityPhoenixArrow extends EntityTippedArrow implements IProjectile
{

	private boolean isSpectral = false;

    private int duration = 200;

	public EntityPhoenixArrow(World worldIn) 
	{
		super(worldIn);
	}

	public EntityPhoenixArrow(World worldIn, EntityLivingBase shooter, boolean isSpectral)
	{
		super(worldIn, shooter);

		this.isSpectral = isSpectral;
	}

	@Override
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
            	if (this.isSpectral)
            	{
                    this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
            	}

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
	protected ItemStack getArrowStack() 
	{
		return this.isSpectral ? new ItemStack(Items.SPECTRAL_ARROW) : super.getArrowStack();
	}

    @Override
    protected void arrowHit(EntityLivingBase living)
    {
        super.arrowHit(living);

        living.setFire(10);
        
        if (this.isSpectral)
        {
            PotionEffect potioneffect = new PotionEffect(MobEffects.GLOWING, this.duration, 0);
            living.addPotionEffect(potioneffect);
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

        if (compound.hasKey("Spectral"))
        {
        	this.isSpectral = compound.getBoolean("Spectral");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setInteger("Duration", this.duration);
        compound.setBoolean("Spectral", this.isSpectral);
    }

}