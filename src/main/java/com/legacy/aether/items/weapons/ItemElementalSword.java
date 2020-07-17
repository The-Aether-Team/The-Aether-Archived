package com.legacy.aether.items.weapons;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemElementalSword extends ItemSword {

	public ItemElementalSword() {
		super(ToolMaterial.EMERALD);

		this.setMaxDamage(502);
		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return ItemsAether.aether_loot;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
		if (this == ItemsAether.flaming_sword) {
			int defaultTime = 30;
			int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(entityliving1);
			if (fireAspectModifier > 0)
			{
				defaultTime += (fireAspectModifier * 4);
			}
			entityliving.setFire(defaultTime);
		} else if (this == ItemsAether.lightning_sword) {
			EntityLightningBolt lightning = new EntityLightningBolt(entityliving1.worldObj, entityliving.posX, entityliving.posY, entityliving.posZ);

			entityliving1.worldObj.spawnEntityInWorld(lightning);
		} else if (this == ItemsAether.holy_sword && entityliving.isEntityUndead()) {

			float damage = 15.0F;

			int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, itemstack);

			if (level > 0)
			{
				damage += (level * 2.5);
			}

			entityliving.attackEntityFrom(DamageSource.drown, damage);
			itemstack.damageItem(10, entityliving1);
		}

		return super.hitEntity(itemstack, entityliving, entityliving1);
	}

	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack repairStack) {
		return false;
	}

}