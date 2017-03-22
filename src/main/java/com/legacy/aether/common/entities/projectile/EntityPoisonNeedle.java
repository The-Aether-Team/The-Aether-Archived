package com.legacy.aether.common.entities.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.projectile.darts.EntityDartPoison;

public class EntityPoisonNeedle extends EntityDartPoison
{

    public EntityPoisonNeedle(World world)
    {
        super(world);
    }

    public EntityPoisonNeedle(World world, EntityLiving ent)
    {
        super(world, ent);
    }

    public void entityInit()
    {
        super.entityInit();
        this.setDamage(1);
    }

    public boolean func_189652_ae()
    {
        return false;
    }

}