package com.legacy.aether.common.registry.objects;

import net.minecraft.item.ItemStack;

public class EntryInformation
{
	public ItemStack base;

	public String s, s1, s2, s3, s4, s5, s6;

	public EntryInformation(ItemStack item, String s, String s1, String s2, String s3, String s4, String s5, String s6)
	{
		this.base = item;
		this.s = s;
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		this.s4 = s4;
		this.s5 = s5;
		this.s6 = s6;
	}
}
