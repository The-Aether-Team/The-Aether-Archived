package com.legacy.aether.items.staffs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.legacy.aether.entities.passive.EntityMiniCloud;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemCloudStaff extends Item {

	public ItemCloudStaff() {
		this.setFull3D();
		this.setMaxDamage(60);
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return ItemsAether.aether_loot;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer entityplayer) {
		PlayerAether playerAether = PlayerAether.get(entityplayer);

		if (world.isRemote) {
			return super.onItemRightClick(stack, world, entityplayer);
		}

		if (playerAether.clouds.isEmpty()) {
			EntityMiniCloud leftCloud = new EntityMiniCloud(world, entityplayer, 0);
			EntityMiniCloud rightCloud = new EntityMiniCloud(world, entityplayer, 1);

			playerAether.clouds.add(leftCloud);
			playerAether.clouds.add(rightCloud);

			world.spawnEntityInWorld(leftCloud);
			world.spawnEntityInWorld(rightCloud);

			stack.damageItem(1, entityplayer);
		}

		return stack;
	}

}