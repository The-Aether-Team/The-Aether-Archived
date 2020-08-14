package com.gildedgames.the_aether.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockRarity extends ItemBlock {

    private final EnumRarity rarity;

    public ItemBlockRarity(Block block, EnumRarity rarity) {
        super(block);

        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return this.rarity;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
