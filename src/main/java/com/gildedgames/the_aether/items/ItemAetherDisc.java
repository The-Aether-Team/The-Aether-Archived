package com.gildedgames.the_aether.items;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

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

}