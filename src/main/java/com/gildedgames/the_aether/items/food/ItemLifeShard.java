package com.gildedgames.the_aether.items.food;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

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

		if (!worldIn.isRemote)
		{
			playerAether.updateShardCount(0);

			if (playerAether.getShardsUsed() < playerAether.getMaxShardCount())
			{
				playerAether.updateShardCount(1);
				--heldItem.stackSize;
			}

			return heldItem;
		}
		else
		{
			if (playerAether.getShardsUsed() >= playerAether.getMaxShardCount())
			{
				Aether.proxy.sendMessage(player, StatCollector.translateToLocalFormatted("gui.item.life_shard.maxshards", playerAether.getMaxShardCount()));
			}
		}

		return heldItem;
	}

}