package com.gildedgames.the_aether.entities.projectile;

import com.gildedgames.the_aether.entities.projectile.darts.EntityDartPoison;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

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

    @Override
    public boolean hasNoGravity()
    {
        return false;
    }

}