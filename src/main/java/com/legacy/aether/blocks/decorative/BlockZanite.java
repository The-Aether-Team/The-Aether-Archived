package com.legacy.aether.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockZanite extends Block
{

	public BlockZanite()
	{
		super(Material.IRON);
		this.setHardness(3F);
		this.setSoundType(SoundType.METAL);
	}

	@Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
    {
    	return true;
    }

}