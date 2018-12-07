package com.legacy.aether.items.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

import com.legacy.aether.entities.projectile.EntityHammerProjectile;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemNotchHammer extends ItemSword {

	public ItemNotchHammer() {
		super(ToolMaterial.IRON);
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
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.capabilities.isCreativeMode) {
			world.playSound(entityplayer.posX, entityplayer.posY, entityplayer.posZ, "mob.ghast.fireball", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F), false);

			if (!world.isRemote) {
				EntityHammerProjectile hammerProjectile = new EntityHammerProjectile(world, entityplayer);
				hammerProjectile.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.spawnEntityInWorld(hammerProjectile);
			}
		} else if (PlayerAether.get(entityplayer).setHammerCooldown(200, itemstack.getDisplayName())) {
			itemstack.damageItem(1, entityplayer);

			world.playSound(entityplayer.posX, entityplayer.posY, entityplayer.posZ, "mob.ghast.fireball", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F), false);

			if (!world.isRemote) {
				EntityHammerProjectile hammerProjectile = new EntityHammerProjectile(world, entityplayer);
				hammerProjectile.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 1.5F, 1.0F);
				world.spawnEntityInWorld(hammerProjectile);
			}
		}

		return itemstack;
	}

}