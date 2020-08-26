package com.gildedgames.the_aether.client.renders.items;

import com.gildedgames.the_aether.tile_entities.TileEntitySkyrootBed;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SkyrootBedItemStackRenderer extends TileEntityItemStackRenderer
{
    private final TileEntitySkyrootBed skyroot_bed = new TileEntitySkyrootBed();

    @Override
    public void renderByItem(ItemStack itemStackIn, float partialTicks)
    {
        this.skyroot_bed.setItemValues(itemStackIn);
        TileEntityRendererDispatcher.instance.render(this.skyroot_bed, 0.0D, 0.0D, 0.0D, 0.0F);
    }
}
