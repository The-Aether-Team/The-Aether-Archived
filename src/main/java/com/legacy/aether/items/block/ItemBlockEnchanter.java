package com.legacy.aether.items.block;

import com.legacy.aether.AetherConfig;
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
