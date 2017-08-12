package com.legacy.aether.common.entities.util;

import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

public class MoaColor
{

	public String moaName;

	private MoaProperties properties;

	public int colorID, hexColor;

	public CreativeTabs creativeTab;

	private static int moaColorID = -1;

	public MoaColor(String moaName, int hexColor, MoaProperties properties)
	{
		this.colorID = ++moaColorID;

		this.moaName = moaName;
		this.hexColor = hexColor;
		this.properties = properties;

		this.creativeTab = AetherCreativeTabs.misc;
	}

	public MoaColor(String moaName, int hexColor, MoaProperties properties, CreativeTabs creativeTab)
	{
		this(moaName, hexColor, properties);

		this.creativeTab = creativeTab;
	}

	public ResourceLocation getTexture(boolean saddled)
	{
		if (this.properties.hasCustomTexture())
		{
			return this.properties.getCustomTexture(saddled);
		}

		return new ResourceLocation("aether_legacy", "textures/entities/moa/" + (saddled ? "saddle_" : "moa_") + this.moaName.toLowerCase() + ".png");
	}

	public MoaProperties getMoaProperties()
	{
		return this.properties;
	}

	public CreativeTabs getCreativeTab()
	{
		return this.creativeTab;
	}

	public String getName()
	{
		return this.moaName;
	}

	public int getID()
	{
		return this.colorID;
	}

	public int getMoaEggColor()
	{
		return this.hexColor;
	}

}