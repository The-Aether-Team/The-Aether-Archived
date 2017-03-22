package com.legacy.aether.universal.jei.handler;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.legacy.aether.common.registry.objects.AetherFreezable;
import com.legacy.aether.universal.jei.wrapper.FreezerRecipeWrapper;

public class FreezerRecipeHandler implements IRecipeHandler<AetherFreezable>
{

	@Override
	public Class<AetherFreezable> getRecipeClass()
	{
		return AetherFreezable.class;
	}

	@Override
	public String getRecipeCategoryUid() 
	{
		return "aether_legacy.freezable";
	}

	@Override
	public String getRecipeCategoryUid(AetherFreezable recipe) 
	{
		return this.getRecipeCategoryUid();
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(AetherFreezable recipe) 
	{
		return new FreezerRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(AetherFreezable recipe) 
	{
		return true;
	}

}