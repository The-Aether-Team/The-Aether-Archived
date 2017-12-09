package com.legacy.aether.items.dungeon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

	@SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
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