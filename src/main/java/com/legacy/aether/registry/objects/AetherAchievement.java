package com.legacy.aether.registry.objects;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class AetherAchievement extends Achievement
{

	public AetherAchievement(String name, String desc, int length, int width, ItemStack stack, Achievement parentAchievement)
	{
		super(name, desc, length, width, stack, parentAchievement);
	}

	public AetherAchievement(String name, String desc, int length, int width, Item item, Achievement parentAchievement)
	{
		this(name, desc, length, width, new ItemStack(item), parentAchievement);
	}

	public AetherAchievement(String name, String desc, int length, int width, Block block, Achievement parentAchievement)
	{
		this(name, desc, length, width, new ItemStack(block), parentAchievement);
	}

	@Override
	public ITextComponent getStatName()
	{
		ITextComponent ichatcomponent = super.getStatName();
		ichatcomponent.getStyle().setColor(TextFormatting.AQUA);
		return ichatcomponent;
	}

}