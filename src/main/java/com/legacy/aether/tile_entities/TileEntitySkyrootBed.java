package com.legacy.aether.tile_entities;

import com.legacy.aether.items.ItemsAether;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBed;

public class TileEntitySkyrootBed extends TileEntityBed
{
    @Override
    public ItemStack getItemStack()
    {
        return new ItemStack(ItemsAether.skyroot_bed_item, 1, 0);
    }
}
