package com.legacy.aether.items.block;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.items.ItemsAether;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemAetherSlab extends ItemSlab {

	public ItemAetherSlab(net.minecraft.block.Block p_i45355_1_, com.legacy.aether.blocks.decorative.BlockAetherSlab p_i45355_2_, com.legacy.aether.blocks.decorative.BlockAetherSlab p_i45355_3_, java.lang.Boolean p_i45355_4_) {
		super(p_i45355_1_, p_i45355_2_, p_i45355_3_, p_i45355_4_);
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return this.field_150939_a == BlocksAether.aerogel_slab ? ItemsAether.aether_loot : EnumRarity.common;
	}
}