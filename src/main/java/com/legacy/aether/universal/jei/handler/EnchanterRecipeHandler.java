package com.legacy.aether.universal.jei.handler;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.legacy.aether.common.enchantments.IEnchantmentHandler;
import com.legacy.aether.universal.jei.wrapper.EnchanterRecipeWrapper;

public class EnchanterRecipeHandler implements IRecipeHandler<IEnchantmentHandler>
{

	@Override
	public Class<IEnchantmentHandler> getRecipeClass()
	{
		return IEnchantmentHandler.class;
	}

	@Override
	public String getRecipeCategoryUid() 
	{
		return "aether_legacy.enchantment";
	}

	@Override
	public String getRecipeCategoryUid(IEnchantmentHandler recipe) 
	{
		return this.getRecipeCategoryUid();
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(IEnchantmentHandler recipe) 
	{
		return new EnchanterRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(IEnchantmentHandler recipe) 
	{
		return true;
	}

}