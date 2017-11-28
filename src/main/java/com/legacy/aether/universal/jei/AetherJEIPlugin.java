package com.legacy.aether.universal.jei;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.item.ItemStack;

import com.legacy.aether.api.AetherRegistry;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.universal.jei.category.EnchanterRecipeCategory;
import com.legacy.aether.universal.jei.category.FreezerRecipeCategory;
import com.legacy.aether.universal.jei.handler.EnchanterRecipeHandler;
import com.legacy.aether.universal.jei.handler.FreezerRecipeHandler;

@JEIPlugin
public class AetherJEIPlugin implements IModPlugin
{

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) 
	{

	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry)
	{

	}

	@Override
	public void register(IModRegistry registry)
	{
		registry.addRecipeCategories(new EnchanterRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
				new FreezerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));

		registry.addRecipeHandlers(new EnchanterRecipeHandler(), new FreezerRecipeHandler());

		registry.addRecipeCategoryCraftingItem(new ItemStack(BlocksAether.enchanter), "aether_legacy.enchantment");
		registry.addRecipeCategoryCraftingItem(new ItemStack(BlocksAether.freezer), "aether_legacy.freezable");

		registry.addRecipes(AetherRegistry.getInstance().getEnchantmentValues());
		registry.addRecipes(AetherRegistry.getInstance().getFreezableValues());
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) 
	{

	}

}