package com.legacy.aether.items;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.Aether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemAetherDisc extends ItemRecord {

	public String artistName;

	public String songName;

	public ResourceLocation songLocation;

	public ItemAetherDisc(String s, String artist, String song) {
		super(s);

		this.artistName = artist;
		this.songName = song;
		this.songLocation = Aether.locate("records." + s);

		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
	public String getRecordNameLocal() {
		return this.artistName + " - " + this.songName;
	}

	@Override
	public ResourceLocation getRecordResource(String name) {
		return this.songLocation;
	}

}