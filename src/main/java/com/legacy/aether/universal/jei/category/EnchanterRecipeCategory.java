package com.legacy.aether.universal.jei.category;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.legacy.aether.common.Aether;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.universal.jei.wrapper.EnchanterRecipeWrapper;

public class EnchanterRecipeCategory implements IRecipeCategory<EnchanterRecipeWrapper>
{

	private static final ResourceLocation altar = Aether.locate("textures/gui/altar.png");

	private IGuiHelper guiHelper;

	private IDrawableAnimated flame, arrow;

	public EnchanterRecipeCategory(IGuiHelper guiHelper)
	{
		this.guiHelper = guiHelper;

		this.flame = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(altar, 176, 0, 14, 14), 300, IDrawableAnimated.StartDirection.TOP, true);
		this.arrow = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(altar, 176, 14, 24, 17), 200, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public String getUid() 
	{
		return "aether_legacy.enchantment";
	}

	@Override
	public String getTitle()
	{
		return "Aether Enchantments";
	}

	@Override
	public IDrawable getBackground() 
	{
		return this.guiHelper.createDrawable(altar, 25, 5, 146, 76);
	}

	@Override
	public IDrawable getIcon()
	{
		return null;
	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{
		this.flame.draw(minecraft, 32, 30);
		this.arrow.draw(minecraft, 54, 30);
	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{

	}

	@Override
	public List<String>  getTooltipStrings(int mouseX, int mouseY)
	{
		return new ArrayList<String>();
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, EnchanterRecipeWrapper recipeWrapper)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

		stacks.init(0, false, 30, 11);
		stacks.init(1, false, 30, 47);
		stacks.init(2, true, 90, 29);

		stacks.set(0, recipeWrapper.enchantment.getEnchantmentInput());
		stacks.set(1, new ItemStack(ItemsAether.ambrosium_shard));
		stacks.set(2, recipeWrapper.enchantment.getEnchantedResult());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, EnchanterRecipeWrapper recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

		stacks.init(0, false, 30, 11);
		stacks.init(1, false, 30, 47);
		stacks.init(2, true, 90, 29);

		stacks.set(0, recipeWrapper.enchantment.getEnchantmentInput());
		stacks.set(1, new ItemStack(ItemsAether.ambrosium_shard));
		stacks.set(2, recipeWrapper.enchantment.getEnchantedResult());
	}

}