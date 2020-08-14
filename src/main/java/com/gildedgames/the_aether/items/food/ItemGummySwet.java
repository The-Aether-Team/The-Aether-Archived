package com.gildedgames.the_aether.items.food;

import com.gildedgames.the_aether.items.util.EnumGummySwetType;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemGummySwet extends ItemAetherFood
{

	public ItemGummySwet()
	{
		super(20);

		this.setHasSubtypes(true);
		this.setCreativeTab(AetherCreativeTabs.food);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		if (tab != this.getCreativeTab() || tab == CreativeTabs.SEARCH)
		{
			return;
		}

		for (int meta = 0; meta < EnumGummySwetType.values().length ; ++meta)
		{
			subItems.add(new ItemStack(this, 1, meta));
		}
	}

	@Override
	public String getTranslationKey(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();

		return this.getTranslationKey() + "_" + EnumGummySwetType.getType(meta).toString();
	}

}