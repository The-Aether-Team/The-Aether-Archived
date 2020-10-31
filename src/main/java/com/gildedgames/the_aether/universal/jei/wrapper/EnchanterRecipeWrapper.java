package com.gildedgames.the_aether.universal.jei.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.the_aether.api.enchantments.AetherEnchantmentFuel;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.enchantments.AetherEnchantment;

public class EnchanterRecipeWrapper implements IRecipeWrapper
{

	private final ArrayList<ItemStack> inputs;

	private final ArrayList<ItemStack> outputs;

	private final ArrayList<ItemStack> fuels;

	public AetherEnchantment enchantment;

	public EnchanterRecipeWrapper(AetherEnchantment recipe)
	{
		this.enchantment = recipe;

		this.inputs = Lists.newArrayList();
		this.fuels = Lists.newArrayList();
		this.outputs = Lists.newArrayList();

		for (AetherEnchantment enchantment : AetherAPI.getInstance().getEnchantmentValues())
		{
			this.inputs.add(enchantment.getInput());
			this.outputs.add(enchantment.getOutput());
		}

		for (AetherEnchantmentFuel fuel : AetherAPI.getInstance().getEnchantmentFuelValues())
		{
			this.fuels.add(fuel.getFuelStack());
		}
	}

	public ArrayList<ItemStack> getFuels()
	{
		return this.fuels;
	}

	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInput(ItemStack.class, this.enchantment.getInput());
		ingredients.setOutput(ItemStack.class, this.enchantment.getOutput());
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{

	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return null;
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) 
	{
		return false;
	}

}