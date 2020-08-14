package com.gildedgames.the_aether.items;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.network.AetherGuiHandler;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLoreBook extends Item {

	public ItemLoreBook() {
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return ItemsAether.aether_loot;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stackIn, World worldIn, EntityPlayer playerIn) {
		playerIn.openGui(Aether.instance, AetherGuiHandler.lore, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);

		return playerIn.getHeldItem();
	}

}