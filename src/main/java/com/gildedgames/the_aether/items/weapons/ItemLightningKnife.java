package com.gildedgames.the_aether.items.weapons;

import com.gildedgames.the_aether.entities.projectile.EntityLightningKnife;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLightningKnife extends Item {
	public ItemLightningKnife() {
		this.setMaxStackSize(16);
		this.setCreativeTab(AetherCreativeTabs.weapons);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return ItemsAether.aether_loot;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heldItem, World worldIn, EntityPlayer playerIn) {
		if (!playerIn.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, heldItem) == 0) {
			--heldItem.stackSize;
		}

		worldIn.playSoundEffect(playerIn.posX, playerIn.posY, playerIn.posZ, "aether_legacy:projectile.shoot", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote) {
			EntityLightningKnife lightningKnife = new EntityLightningKnife(worldIn, playerIn);

			lightningKnife.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);

			worldIn.spawnEntityInWorld(lightningKnife);
		}

		return heldItem;
	}

}