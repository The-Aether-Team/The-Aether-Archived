package com.gildedgames.the_aether.items.block;

import com.gildedgames.the_aether.blocks.decorative.BlockAetherSlab;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemAetherSlab extends ItemSlab {

	public ItemAetherSlab(net.minecraft.block.Block p_i45355_1_, BlockAetherSlab p_i45355_2_, BlockAetherSlab p_i45355_3_, java.lang.Boolean p_i45355_4_) {
		super(p_i45355_1_, p_i45355_2_, p_i45355_3_, p_i45355_4_);
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return this.field_150939_a == BlocksAether.aerogel_slab ? ItemsAether.aether_loot : EnumRarity.common;
	}
}