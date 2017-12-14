package com.legacy.aether.items.weapons.projectile;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import com.legacy.aether.items.util.EnumDartType;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemDart extends Item
{

    public ItemDart()
    {
        super();
        this.setHasSubtypes(true);
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return stack.getMetadata() == 2 ? EnumRarity.RARE : super.getRarity(stack);
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int i = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumDartType.values()[i].toString();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
		if (tab != AetherCreativeTabs.weapons || tab == CreativeTabs.SEARCH)
		{
			return;
		}

        for (int var4 = 0; var4 < EnumDartType.values().length ; ++var4)
        {
        	subItems.add(new ItemStack(this, 1, var4));
        }
    }

}