package com.gildedgames.the_aether.items;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

public class ItemAetherDisc extends ItemRecord
{

	public String artistName;

	public String songName;

	protected ItemAetherDisc(String s, SoundEvent event, String artist)
	{
		super(s, event);
		this.artistName = artist;
		this.songName = s;
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
	public String getRecordNameLocal()
	{
		return this.artistName + " - " + I18n.format("item.tooltip." + this.songName);
	}

}