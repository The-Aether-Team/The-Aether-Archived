package com.gildedgames.the_aether.entities.projectile.darts;

import com.gildedgames.the_aether.entities.effects.PotionsAether;
import com.gildedgames.the_aether.items.ItemsAether;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityDartPoison extends EntityDartBase
{

	public EntityLivingBase victim;

    public EntityDartPoison(World worldIn)
    {
        super(worldIn);
    }

    public EntityDartPoison(World world, EntityLivingBase entity) 
    {
		super(world, entity);
	}

    public void entityInit()
    {
        super.entityInit();
        this.setDamage(0);
    }

    @Override
    protected void arrowHit(EntityLivingBase living)
    {
    	super.arrowHit(living);

        if (!world.isRemote)
        {
            living.addPotionEffect(new PotionEffect(PotionsAether.INEBRIATION, 500, 0, false, false));
        }

    	this.isDead = false;
    }

	@Override
	protected ItemStack getArrowStack() 
	{
		return new ItemStack(ItemsAether.dart, 1, 1);
	}

}