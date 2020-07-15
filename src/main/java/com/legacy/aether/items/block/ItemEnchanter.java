package com.legacy.aether.items.block;

import com.legacy.aether.AetherConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemEnchanter extends ItemBlock
{
    public ItemEnchanter(Block block)
    {
        super(block);
    }

    public String getTranslationKey(ItemStack stack)
    {
        return AetherConfig.visual_options.legacy_altar_name ? "tile.enchanter" : "tile.altar";
    }

    public String getTranslationKey()
    {
        return AetherConfig.visual_options.legacy_altar_name ? "tile.enchanter" : "tile.altar";
    }
}
