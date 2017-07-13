package com.legacy.aether.common.blocks.decorative;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAerogel extends BlockBreakable
{

	public BlockAerogel() 
	{
		super(Material.ROCK, false);

		this.setHardness(1.0F);
		this.setResistance(2000F);
		this.setLightOpacity(3);
		this.setSoundType(SoundType.METAL);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

}