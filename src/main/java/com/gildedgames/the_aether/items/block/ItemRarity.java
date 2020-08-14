package com.gildedgames.the_aether.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemRarity extends ItemBlock
{
    private final EnumRarity rarity;

    public ItemRarity(Block block, EnumRarity rarity)
    {
        super(block);
        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return this.rarity;
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

}
