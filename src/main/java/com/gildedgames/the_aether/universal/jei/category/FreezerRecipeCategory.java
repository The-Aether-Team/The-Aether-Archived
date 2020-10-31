package com.gildedgames.the_aether.universal.jei.category;

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
import net.minecraft.util.ResourceLocation;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.universal.jei.wrapper.FreezerRecipeWrapper;

public class FreezerRecipeCategory implements IRecipeCategory<FreezerRecipeWrapper>
{

	private static final ResourceLocation altar = Aether.locate("textures/gui/altar.png");

	private IGuiHelper guiHelper;

	private IDrawableAnimated flame, arrow;

	public FreezerRecipeCategory(IGuiHelper guiHelper)
	{
		this.guiHelper = guiHelper;

		this.flame = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(altar, 176, 0, 14, 14), 300, IDrawableAnimated.StartDirection.TOP, true);
		this.arrow = guiHelper.createAnimatedDrawable(guiHelper.createDrawable(altar, 176, 14, 24, 17), 200, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public String getUid() 
	{
		return "aether_legacy.freezable";
	}

	@Override
	public String getTitle()
	{
		return "Aether Freezables";
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
	public List<String>  getTooltipStrings(int mouseX, int mouseY)
	{
		return new ArrayList<String>();
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FreezerRecipeWrapper recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

		stacks.init(0, false, 30, 11);
		stacks.init(1, false, 30, 47);
		stacks.init(2, true, 90, 29);

		stacks.set(0, recipeWrapper.freezable.getInput());
		stacks.set(1, recipeWrapper.getFuels());
		stacks.set(2, recipeWrapper.freezable.getOutput());
	}

	@Override
	public String getModName()
	{
		return "aether_legacy";
	}

}