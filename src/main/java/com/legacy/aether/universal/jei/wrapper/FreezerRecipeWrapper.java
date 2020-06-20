package com.legacy.aether.universal.jei.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.legacy.aether.api.enchantments.AetherEnchantmentFuel;
import com.legacy.aether.api.freezables.AetherFreezableFuel;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Lists;
import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.freezables.AetherFreezable;

public class FreezerRecipeWrapper implements IRecipeWrapper
{

	private final ArrayList<ItemStack> inputs;

	private final ArrayList<ItemStack> outputs;

	private final ArrayList<ItemStack> fuels;

	public AetherFreezable freezable;

	public FreezerRecipeWrapper(AetherFreezable recipe)
	{
		this.freezable = recipe;

		this.inputs = Lists.newArrayList();
		this.fuels = Lists.newArrayList();
		this.outputs = Lists.newArrayList();

		for (AetherFreezable freezable : AetherAPI.getInstance().getFreezableValues())
		{
			this.inputs.add(freezable.getInput());
			this.outputs.add(freezable.getOutput());
		}

		for (AetherFreezableFuel fuel : AetherAPI.getInstance().getFreezableFuelValues())
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
		ingredients.setInput(ItemStack.class, this.freezable.getInput());
		ingredients.setOutput(ItemStack.class, this.freezable.getOutput());
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