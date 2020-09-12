package com.gildedgames.the_aether.universal.jei;

import com.gildedgames.the_aether.universal.jei.category.EnchanterRecipeCategory;
import com.gildedgames.the_aether.universal.jei.category.FreezerRecipeCategory;
import com.gildedgames.the_aether.universal.jei.wrapper.EnchanterRecipeWrapper;
import com.gildedgames.the_aether.universal.jei.wrapper.FreezerRecipeWrapper;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.enchantments.AetherEnchantment;
import com.gildedgames.the_aether.api.freezables.AetherFreezable;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.client.gui.GuiEnchanter;
import com.gildedgames.the_aether.client.gui.GuiFreezer;

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
				if (!recipe.getOutput().isEmpty())
				{
					return new EnchanterRecipeWrapper(recipe);
				}
				else
				{
					return null;
				}
			}
		}, "aether_legacy.enchantment");

		registry.handleRecipes(AetherFreezable.class, new IRecipeWrapperFactory<AetherFreezable>() {

			@Override
			public IRecipeWrapper getRecipeWrapper(AetherFreezable recipe)
			{
				if (!recipe.getOutput().isEmpty())
				{
					return new FreezerRecipeWrapper(recipe);
				}
				else
				{
					return null;
				}
			}
		}, "aether_legacy.freezable");

		registry.addRecipeCatalyst(new ItemStack(BlocksAether.enchanter), "aether_legacy.enchantment");
		registry.addRecipeCatalyst(new ItemStack(BlocksAether.freezer), "aether_legacy.freezable");

		registry.addRecipes(AetherAPI.getInstance().getEnchantmentValues(), "aether_legacy.enchantment");
		registry.addRecipes(AetherAPI.getInstance().getFreezableValues(), "aether_legacy.freezable");

		registry.addRecipeClickArea(GuiFreezer.class, 80, 35, 22, 15, "aether_legacy.freezable");
        registry.addRecipeClickArea(GuiEnchanter.class, 80, 35, 22, 15, "aether_legacy.enchantment");

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