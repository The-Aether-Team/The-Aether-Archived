package com.legacy.aether.items.weapons;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemElementalSword extends ItemSword
{

	public ItemElementalSword()
	{
		super(ToolMaterial.DIAMOND);
		this.setMaxDamage(502);
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
		if (this == ItemsAether.flaming_sword)
		{
			int defaultTime = 30;

			int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(entityliving1);

			if (fireAspectModifier > 0)
			{
				defaultTime += (fireAspectModifier * 4);
			}

			entityliving.setFire(defaultTime);
		}
		else if (this == ItemsAether.lightning_sword)
		{
			EntityLightningBolt lightning = new EntityLightningBolt(entityliving1.world, entityliving.posX, entityliving.posY, entityliving.posZ, false);
			entityliving1.world.spawnEntity(lightning);
		}
		else if (this == ItemsAether.holy_sword && entityliving.isEntityUndead())
		{
			float damage = 15.0F;

			int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, itemstack);

			if (level > 0)
			{
				damage += (level * 2.5);
			}

			entityliving.attackEntityFrom(DamageSource.DROWN, damage);
			itemstack.damageItem(10, entityliving1);
		}

		return super.hitEntity(itemstack, entityliving, entityliving1);
    }

	@Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
    {
    	return false;
    }

}