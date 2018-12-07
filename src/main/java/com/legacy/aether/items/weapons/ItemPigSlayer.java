package com.legacy.aether.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemPigSlayer extends ItemSword {

	public ItemPigSlayer() {
		super(ToolMaterial.IRON);
		this.setMaxDamage(200);
		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return ItemsAether.aether_loot;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
		if (entityliving == null || entityliving1 == null) {
			return false;
		}

		String s = EntityList.getEntityString((Entity) entityliving);

		if (s != null && (s.toLowerCase().contains("pig") || s.toLowerCase().contains("phyg") || s.toLowerCase().contains("taegore") || entityliving.getUniqueID().toString().equals("1d680bb6-2a9a-4f25-bf2f-a1af74361d69"))) {
			if (entityliving.getHealth() > 0) {
				entityliving.attackEntityFrom(DamageSource.causeMobDamage(entityliving1), 9999);
			}

			for (int j = 0; j < 20; j++) {
				double d = itemRand.nextGaussian() * 0.02D;
				double d1 = itemRand.nextGaussian() * 0.02D;
				double d2 = itemRand.nextGaussian() * 0.02D;
				double d3 = 5D;
				entityliving.worldObj.spawnParticle("flame", (entityliving.posX + (double) (itemRand.nextFloat() * entityliving.width * 2.0F)) - (double) entityliving.width - d * d3, (entityliving.posY + (double) (itemRand.nextFloat() * entityliving.height)) - d1 * d3, (entityliving.posZ + (double) (itemRand.nextFloat() * entityliving.width * 2.0F)) - (double) entityliving.width - d2 * d3, d, d1, d2);
			}
		}

		return super.hitEntity(itemstack, entityliving, entityliving1);
	}

}