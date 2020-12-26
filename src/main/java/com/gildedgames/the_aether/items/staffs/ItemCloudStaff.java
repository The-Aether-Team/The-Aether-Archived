package com.gildedgames.the_aether.items.staffs;

import com.gildedgames.the_aether.entities.passive.EntityMiniCloud;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
		else if (entityplayer.isSneaking())
		{
			for (Entity cloud : playerAether.clouds)
			{
				if (cloud instanceof EntityMiniCloud)
				{
					((EntityMiniCloud) cloud).lifeSpan = 0;
				}
			}
		}

		return stack;
	}

}