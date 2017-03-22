package com.legacy.aether.universal.jei.handler;

import com.legacy.aether.common.registry.objects.AetherEnchantment;
import com.legacy.aether.universal.jei.wrapper.EnchanterRecipeWrapper;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class EnchanterRecipeHandler implements IRecipeHandler<AetherEnchantment>
{

	@Override
	public Class<AetherEnchantment> getRecipeClass()
	{
		return AetherEnchantment.class;
	}

	@Override
	public String getRecipeCategoryUid() 
	{
		return "aether_legacy.enchantment";
	}

	@Override
	public String getRecipeCategoryUid(AetherEnchantment recipe) 
	{
		return this.getRecipeCategoryUid();
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(AetherEnchantment recipe) 
	{
		return new EnchanterRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(AetherEnchantment recipe) 
	{
		return true;
	}

}