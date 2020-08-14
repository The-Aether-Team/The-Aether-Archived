package com.gildedgames.the_aether.items.block;

import com.gildedgames.the_aether.AetherConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockEnchanter extends ItemBlockMetadata
{
    public ItemBlockEnchanter(Block block) {
        super(block);
    }

    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return AetherConfig.legacyAltarName() ? "tile.enchanter" : "tile.altar";
    }

    public String getUnlocalizedName()
    {
        return AetherConfig.legacyAltarName() ? "tile.enchanter" : "tile.altar";
    }
}
