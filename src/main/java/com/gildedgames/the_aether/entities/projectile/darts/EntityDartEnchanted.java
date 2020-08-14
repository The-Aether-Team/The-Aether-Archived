package com.gildedgames.the_aether.entities.projectile.darts;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gildedgames.the_aether.items.ItemsAether;

public class EntityDartEnchanted extends EntityDartBase {

    public EntityDartEnchanted(World worldIn) {
        super(worldIn);
    }

    public EntityDartEnchanted(World world, EntityLivingBase entity, float velocity) {
        super(world, entity, velocity);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.setDamage(6);
    }

    @Override
    protected ItemStack getStack() {
        return new ItemStack(ItemsAether.dart, 1, 2);
    }

}