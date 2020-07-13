package com.legacy.aether.items.weapons;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

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
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return false;
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase target, EntityLivingBase attacker)
    {
        String s = EntityList.getEntityString(target);

		if(s != null && (s.toLowerCase().contains("pig") || s.toLowerCase().contains("phyg") || s.toLowerCase().contains("taegore") || target.getUniqueID().toString().equals("1d680bb6-2a9a-4f25-bf2f-a1af74361d69")))
		{
			if(target.getHealth() > 0)
			{
				target.attackEntityFrom(DamageSource.causeMobDamage(attacker), 9999);
			}
		}

        return super.hitEntity(itemstack, target, attacker);
    }

}