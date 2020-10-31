package com.gildedgames.the_aether.items.block;

import com.gildedgames.the_aether.blocks.util.IAetherMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemSubtype extends ItemBlock
{

	public ItemSubtype(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
	}

	@Override
	public String getTranslationKey(ItemStack itemstack)
	{
		return "tile." + ((IAetherMeta)this.block).getMetaName(itemstack);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

}