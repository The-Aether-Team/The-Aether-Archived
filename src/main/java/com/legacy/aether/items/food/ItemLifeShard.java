package com.legacy.aether.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.legacy.aether.Aether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemLifeShard extends Item {

	public ItemLifeShard() {
		super();
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return ItemsAether.aether_loot;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player) {
		PlayerAether playerAether = PlayerAether.get(player);
		ItemStack heldItem = player.getHeldItem();

		if (worldIn.isRemote) {
			return heldItem;
		}

		if (playerAether.getShardsUsed() < playerAether.getMaxShardCount()) {
			playerAether.updateShardCount(playerAether.getShardsUsed() + 1);

			--heldItem.stackSize;

			return heldItem;
		}

		Aether.proxy.sendMessage(player, "You can only use a total of " + playerAether.getMaxShardCount() + " life shards.");

		return heldItem;
	}

}