package com.legacy.aether.server.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockZanite extends Block
{

	public BlockZanite()
	{
		super(Material.IRON);
		this.setHardness(3F);
		this.setSoundType(SoundType.METAL);
	}

}