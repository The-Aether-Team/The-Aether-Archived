package com.legacy.aether.items.dungeon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.util.EnumDungeonKeyType;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

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
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumDungeonKeyType.getType(meta).toString();
	}

}