package com.legacy.aether.common.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPillar extends Block
{

	public BlockPillar() 
	{
		super(Material.ROCK);
		this.setSoundType(SoundType.METAL);
		this.setHardness(0.5F);
	}

}