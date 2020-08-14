package com.gildedgames.the_aether.registry.achievements;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class AetherAchievement extends Achievement {

	public AetherAchievement(String name, String desc, int length, int width, ItemStack stack, Achievement parentAchievement) {
		super(name, desc, length, width, stack, parentAchievement);
	}

	public AetherAchievement(String name, String desc, int length, int width, Item item, Achievement parentAchievement) {
		this(name, desc, length, width, new ItemStack(item), parentAchievement);
	}

	public AetherAchievement(String name, String desc, int length, int width, Block block, Achievement parentAchievement) {
		this(name, desc, length, width, new ItemStack(block), parentAchievement);
	}

	@Override
	public IChatComponent func_150951_e() {
		IChatComponent ichatcomponent = super.func_150951_e();
		ichatcomponent.getChatStyle().setColor(EnumChatFormatting.AQUA);
		return ichatcomponent;
	}

}