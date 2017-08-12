package com.legacy.aether.universal.jei.wrapper;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.collect.Lists;
import com.legacy.aether.common.freezables.IFreezableHandler;

public class FreezerRecipeWrapper implements IRecipeWrapper
{

	private final ArrayList<ItemStack> inputs;

	private final ArrayList<ItemStack> outputs;

	public IFreezableHandler freezable;

	public FreezerRecipeWrapper(IFreezableHandler recipe)
	{
		this.freezable = recipe;

		this.inputs = Lists.newArrayList();
		this.outputs = Lists.newArrayList();
	}

	@Override
	public void getIngredients(IIngredients ingredients) 
	{

	}

	@Override
	public List<ItemStack> getInputs() 
	{
		return this.inputs;
	}

	@Override
	public List<ItemStack> getOutputs() 
	{
		return this.outputs;
	}

	@Override
	public List<FluidStack> getFluidInputs()
	{
		return null;
	}

	@Override
	public List<FluidStack> getFluidOutputs() 
	{
		return null;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{

	}

	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) 
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