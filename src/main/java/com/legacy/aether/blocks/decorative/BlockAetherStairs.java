package com.legacy.aether.blocks.decorative;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class BlockAetherStairs extends BlockStairs 
{

	public BlockAetherStairs(IBlockState modelState)
	{
		super(modelState);
		this.setLightOpacity(0);
	}

}