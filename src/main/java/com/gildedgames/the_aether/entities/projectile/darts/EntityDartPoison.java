package com.gildedgames.the_aether.entities.projectile.darts;

import com.gildedgames.the_aether.entities.effects.EffectInebriation;
import com.gildedgames.the_aether.entities.effects.PotionInebriation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.gildedgames.the_aether.items.ItemsAether;

public class EntityDartPoison extends EntityDartBase {

    public EntityDartPoison(World worldIn) {
        super(worldIn);
    }

    public EntityDartPoison(World world, EntityLivingBase entity, float velocity) {
        super(world, entity, velocity);
    }

    public void entityInit() {
        super.entityInit();
        this.setDamage(0);
    }

    @Override
    public void onDartHit(MovingObjectPosition movingobjectposition) {
        super.onDartHit(movingobjectposition);

        if (!worldObj.isRemote)
        {
            if (movingobjectposition.entityHit != null)
            {
                if (movingobjectposition.entityHit instanceof EntityLivingBase)
                {
                    ((EntityLivingBase) movingobjectposition.entityHit).addPotionEffect(new EffectInebriation(PotionInebriation.inebriation.id, 500, 0));
                }
            }
        }

        this.isDead = false;
    }

    @Override
    protected ItemStack getStack() {
        return new ItemStack(ItemsAether.dart, 1, 1);
    }

}