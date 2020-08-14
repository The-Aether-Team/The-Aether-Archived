package com.gildedgames.the_aether.api.moa;

import com.gildedgames.the_aether.api.RegistryEntry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

public class AetherMoaType extends RegistryEntry {

	private MoaProperties properties;

	public int hexColor;

	public CreativeTabs creativeTab;

	public AetherMoaType(int hexColor, MoaProperties properties) {
		this.hexColor = hexColor;
		this.properties = properties;

		this.creativeTab = CreativeTabs.tabMisc;
	}

	public AetherMoaType(int hexColor, MoaProperties properties, CreativeTabs creativeTab) {
		this(hexColor, properties);

		this.creativeTab = creativeTab;
	}

	public ResourceLocation getTexture(boolean saddled, boolean isBeingRidden) {
		if (this.properties.hasCustomTexture()) {
			return this.properties.getCustomTexture(saddled, isBeingRidden);
		}

		return new ResourceLocation("aether_legacy", "textures/entities/moa/" + ("moa_") + this.getRegistryName().getResourcePath().toLowerCase() + ".png");
	}

	public MoaProperties getMoaProperties() {
		return this.properties;
	}

	public CreativeTabs getCreativeTab() {
		return this.creativeTab;
	}

	public int getMoaEggColor() {
		return this.hexColor;
	}

}