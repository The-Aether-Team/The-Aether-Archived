package com.legacy.aether.server.items;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public class ItemAetherDisc extends ItemRecord
{

	public String artistName;

	public String songName;

	protected ItemAetherDisc(String s, SoundEvent event, String artist, String song)
	{
		super(s, event);
		this.artistName = artist;
		this.songName = song;
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
	public String getRecordNameLocal()
	{
		return this.artistName + " - " + this.songName;
	}

	@Override
	public ResourceLocation getRecordResource(String name)
	{
		return new ResourceLocation("aether_legacy", name);
	}

}