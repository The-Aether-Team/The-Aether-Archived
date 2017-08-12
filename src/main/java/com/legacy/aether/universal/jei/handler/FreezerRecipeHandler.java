package com.legacy.aether.universal.jei.handler;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.legacy.aether.common.freezables.IFreezableHandler;
import com.legacy.aether.universal.jei.wrapper.FreezerRecipeWrapper;

public class FreezerRecipeHandler implements IRecipeHandler<IFreezableHandler>
{

	@Override
	public Class<IFreezableHandler> getRecipeClass()
	{
		return IFreezableHandler.class;
	}

	@Override
	public String getRecipeCategoryUid() 
	{
		return "aether_legacy.freezable";
	}

	@Override
	public String getRecipeCategoryUid(IFreezableHandler recipe) 
	{
		return this.getRecipeCategoryUid();
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(IFreezableHandler recipe) 
	{
		return new FreezerRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(IFreezableHandler recipe) 
	{
		return true;
	}

}