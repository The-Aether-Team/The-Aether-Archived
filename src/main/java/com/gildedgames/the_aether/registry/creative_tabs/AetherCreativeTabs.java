package com.gildedgames.the_aether.registry.creative_tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AetherCreativeTabs {

	public static AetherTab blocks = new AetherTab("aether_blocks"),
			tools = new AetherTab("aether_tools"),
			weapons = new AetherTab("aether_weapons"),
			armor = new AetherTab("aether_armor"),
			food = new AetherTab("aether_food"),
			accessories = new AetherTab("aether_accessories"),
			material = new AetherTab("aether_material"),
			misc = new AetherTab("aether_misc");

	public static void initialization() {
		blocks.setIcon(new ItemStack(BlocksAether.aether_grass));
		tools.setIcon(new ItemStack(ItemsAether.gravitite_pickaxe));
		weapons.setIcon(new ItemStack(ItemsAether.gravitite_sword));
		armor.setIcon(new ItemStack(ItemsAether.gravitite_helmet));
		food.setIcon(new ItemStack(ItemsAether.blueberry));
		accessories.setIcon(new ItemStack(ItemsAether.gravitite_gloves));
		material.setIcon(new ItemStack(ItemsAether.ambrosium_shard));
		misc.setIcon(new ItemStack(ItemsAether.dungeon_key));
	}

	public static class AetherTab extends CreativeTabs {

		private ItemStack stack;

		public AetherTab(String unlocalizedName) {
			super(unlocalizedName);
		}

		public AetherTab(String unlocalizedName, ItemStack stack) {
			super(unlocalizedName);
			this.stack = stack;
		}

		public void setIcon(ItemStack stack) {
			this.stack = stack;
		}

		@SideOnly(Side.CLIENT)
		public String getTranslatedTabLabel() {
			return "tab." + this.getTabLabel();
		}

		@Override
		public ItemStack getIconItemStack() {
			return stack;
		}

		@Override
		public Item getTabIconItem() {
			return stack.getItem();
		}

	}

}