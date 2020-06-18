package com.legacy.aether.items.block;

import com.legacy.aether.blocks.util.IAetherMeta;
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
