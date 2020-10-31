package com.gildedgames.the_aether.items.dungeon;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.util.EnumDungeonKeyType;

public class ItemDungeonKey extends Item
{

	public ItemDungeonKey()
	{
		super();
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
		if (tab != this.getCreativeTab() || tab == CreativeTabs.SEARCH)
		{
			return;
		}

    	for (int meta = 0; meta < EnumDungeonKeyType.values().length; ++meta)
    	{
    		subItems.add(new ItemStack(this, 1, meta));
    	}
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

	@Override
	public String getTranslationKey(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();

		return this.getTranslationKey() + "_" + EnumDungeonKeyType.getType(meta).toString();
	}

}