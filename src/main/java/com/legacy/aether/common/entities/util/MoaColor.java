package com.legacy.aether.common.entities.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoaColor
{

	public String name;

	public int ID, RGB, jumps, chance;

	private static int totalChance;

	public static List<String> names = new ArrayList<String>();

	public static List<MoaColor> colors = new ArrayList<MoaColor>();

	static
	{
		new MoaColor(0, 0x7777ff, 3, 100, "Blue");
		new MoaColor(1, -802168, 2, 50, "Orange");
		new MoaColor(2, 0xffffff, 4, 20, "White");
		new MoaColor(3, 0x222222, 8, 5, "Black");
	}

	public MoaColor(int i, int j, int k, int l, String s)
	{
		ID = i;
		RGB = j;
		jumps = k;
		chance = l;
		totalChance += l;
		name = s;
		colors.add(this);
		names.add(name);
	}

	public ResourceLocation getTexture()
	{	
		return new ResourceLocation("aether_legacy", "textures/entities/moa/" + ("moa_") + name.toLowerCase() + ".png");
	}

	public static MoaColor getRandomColor(World world)
	{
		int i = world.rand.nextInt(totalChance);
		
		for(int j = 0; j < colors.size(); j++)
		{
			if(i < colors.get(j).chance)
			{
				return colors.get(j);
			}
			else
			{
				i -= colors.get(j).chance;
			}
		}
		
		return colors.get(0);
	}

	public static MoaColor getColor(int ID)
	{
		for(int i = 0; i < colors.size(); i++)
		{
			if(colors.get(i).ID == ID)
			{
				return colors.get(i);
			}
		}
		
		return colors.get(0);
	}

	public static String[] getNames()
	{
		String[] namesArray = new String[names.size()];
		namesArray = names.toArray(namesArray);
		
		return namesArray;
	}

}