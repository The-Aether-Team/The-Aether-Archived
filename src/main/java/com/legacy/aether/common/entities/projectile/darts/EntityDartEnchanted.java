package com.legacy.aether.common.entities.projectile.darts;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.legacy.aether.common.items.ItemsAether;

public class EntityDartEnchanted extends EntityDartBase
{

    public EntityDartEnchanted(World worldIn)
    {
        super(worldIn);
    }

    public EntityDartEnchanted(World world, EntityLivingBase entity)
    {
        super(world, entity);
    }

    public void entityInit()
    {
        super.entityInit();
        this.setDamage(6);
    }

	@Override
	protected ItemStack getArrowStack() 
	{
		return new ItemStack(ItemsAether.dart, 1, 2);
	}

}