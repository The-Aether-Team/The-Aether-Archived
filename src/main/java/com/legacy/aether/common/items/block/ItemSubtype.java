package com.legacy.aether.common.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.legacy.aether.common.blocks.util.IAetherMeta;

public class ItemSubtype extends ItemBlock
{

	public ItemSubtype(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return "tile." + ((IAetherMeta)this.block).getMetaName(itemstack);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

}