package com.legacy.aether.common.items.weapons;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;

import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

public class ItemPigSlayer extends ItemSword
{

	private Random rand = new Random();

    public ItemPigSlayer()
    {
        super(ToolMaterial.IRON);
        this.setMaxDamage(200);
        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1)
    {
		if(entityliving == null || entityliving1 == null)
		{
			return false;
		}

        String s = EntityList.getEntityString((Entity)entityliving);

		if(s != null && (s.toLowerCase().contains("pig") || s.toLowerCase().contains("phyg"))) 
		{
			if(entityliving.getHealth() > 0)
			{
				entityliving.hurtTime = 0;
				entityliving.setHealth(1.0F);;
				entityliving.attackEntityFrom(DamageSource.causeMobDamage(entityliving1), 9999);
			}

			for(int j = 0; j < 20; j++)
			{
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                double d3 = 5D;
                entityliving.world.spawnParticle(EnumParticleTypes.FLAME, (entityliving.posX + (double)(rand.nextFloat() * entityliving.width * 2.0F)) - (double)entityliving.width - d * d3, (entityliving.posY + (double)(rand.nextFloat() * entityliving.height)) - d1 * d3, (entityliving.posZ + (double)(rand.nextFloat() * entityliving.width * 2.0F)) - (double)entityliving.width - d2 * d3, d, d1, d2);
            }

			entityliving.setDead();
		}

        return super.hitEntity(itemstack, entityliving, entityliving1);
    }

}