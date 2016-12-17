package com.legacy.aether.server.blocks.decorative;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;

public class BlockAetherFenceGate extends BlockFenceGate
{

	public BlockAetherFenceGate() 
	{
		super(EnumType.OAK);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.WOOD);
	}

	@Override
    public MapColor getMapColor(IBlockState state)
    {
    	return MapColor.WOOD;
    }

}