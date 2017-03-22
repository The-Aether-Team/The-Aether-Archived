package com.legacy.aether.common.items.dungeon;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.common.items.util.EnumDungeonKeyType;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

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
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tab, List list)
    {
    	for (int meta = 0; meta < EnumDungeonKeyType.values().length; ++meta)
    	{
        	list.add(new ItemStack(this, 1, meta));
    	}
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumDungeonKeyType.getType(meta).toString();
	}

}