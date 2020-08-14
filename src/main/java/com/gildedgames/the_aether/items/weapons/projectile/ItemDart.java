package com.gildedgames.the_aether.items.weapons.projectile;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import com.gildedgames.the_aether.items.util.EnumDartType;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemDart extends Item
{

    public ItemDart()
    {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return stack.getMetadata() == 2 ? EnumRarity.RARE : super.getRarity(stack);
    }

	@Override
	public String getTranslationKey(ItemStack itemstack)
	{
		int i = itemstack.getItemDamage();

		return this.getTranslationKey() + "_" + EnumDartType.values()[i].toString();
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
    	if (this.isInCreativeTab(tab))
        {
            for (int meta = 0; meta < EnumDartType.values().length; ++meta)
            {
            	subItems.add(new ItemStack(this, 1, meta));
            }
        }
    }

}