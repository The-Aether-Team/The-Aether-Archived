package com.legacy.aether.universal.jei;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.enchantments.AetherEnchantment;
import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.universal.jei.category.EnchanterRecipeCategory;
import com.legacy.aether.universal.jei.category.FreezerRecipeCategory;
import com.legacy.aether.universal.jei.wrapper.EnchanterRecipeWrapper;
import com.legacy.aether.universal.jei.wrapper.FreezerRecipeWrapper;

@JEIPlugin
public class AetherJEIPlugin implements IModPlugin
{

	@Override
	public void register(IModRegistry registry)
	{
		registry.handleRecipes(AetherEnchantment.class, new IRecipeWrapperFactory<AetherEnchantment>() {

			@Override
			public IRecipeWrapper getRecipeWrapper(AetherEnchantment recipe) 
			{
				return new EnchanterRecipeWrapper(recipe);
			}
		}, "aether_legacy.enchantment");

		registry.handleRecipes(AetherFreezable.class, new IRecipeWrapperFactory<AetherFreezable>() {

			@Override
			public IRecipeWrapper getRecipeWrapper(AetherFreezable recipe) 
			{
				return new FreezerRecipeWrapper(recipe);
			}
		}, "aether_legacy.freezable");

		registry.addRecipeCatalyst(new ItemStack(BlocksAether.enchanter), "aether_legacy.enchantment");
		registry.addRecipeCatalyst(new ItemStack(BlocksAether.freezer), "aether_legacy.freezable");

		registry.addRecipes(AetherAPI.getInstance().getEnchantmentValues(), "aether_legacy.enchantment");
		registry.addRecipes(AetherAPI.getInstance().getFreezableValues(), "aether_legacy.freezable");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) 
	{
		registry.addRecipeCategories(new EnchanterRecipeCategory(registry.getJeiHelpers().getGuiHelper()), new FreezerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) 
	{

	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry)
	{

	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) 
	{

	}

}