package com.legacy.aether.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.legacy.aether.Aether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemAetherArmor extends ItemArmor {

	private String[] defualt_location = new String[]{"textures/models/armor/iron_layer_1.png", "textures/models/armor/iron_layer_2.png"};

	public boolean shouldDefualt = false;

	private int colorization = -1;

	private String armorName;

	private Item source = null;

	public ItemAetherArmor(int armorType, ArmorMaterial material, String name, Item repair) {
		super(material, 0, armorType);

		this.source = repair;
		this.armorName = name;
		this.setCreativeTab(AetherCreativeTabs.armor);
	}

	public ItemAetherArmor(int armorType, ArmorMaterial material, String name, Item repair, int hex) {
		this(armorType, material, name, repair);

		this.source = repair;
		this.armorName = name;
		this.colorization = hex;
		this.shouldDefualt = true;
	}

	@Override
	public int getColor(ItemStack stack) {
		return this.colorization;
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return this.colorization != -1 ? this.colorization : 16777215;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		boolean leggings = this.getUnlocalizedName().contains("leggings");
		String type1 = leggings ? "layer_2" : "layer_1";

		return this.shouldDefualt ? (leggings ? defualt_location[1] : defualt_location[0]) : Aether.modAddress() + "textures/armor/" + this.armorName + "_" + type1 + ".png";
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.source == null ? false : repair.getItem() == this.source;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return !this.armorName.contains("zanite") && !this.armorName.contains("gravitite") ? ItemsAether.aether_loot : super.getRarity(stack);
	}
}