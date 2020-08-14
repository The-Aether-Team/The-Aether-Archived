package com.gildedgames.the_aether.api;

import net.minecraft.util.ResourceLocation;

public class RegistryEntry {

	private ResourceLocation location;

	public RegistryEntry setRegistryName(String modid, String location) {
		this.location = new ResourceLocation(modid, location);

		return this;
	}

	public RegistryEntry setRegistryName(String location) {
		this.location = new ResourceLocation(location);

		return this;
	}

	public RegistryEntry setRegistryName(ResourceLocation location) {
		this.location = location;

		return this;
	}

	public ResourceLocation getRegistryName() {
		return this.location;
	}

}