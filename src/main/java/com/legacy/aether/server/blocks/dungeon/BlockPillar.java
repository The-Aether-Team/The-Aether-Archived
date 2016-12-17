package com.legacy.aether.server.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPillar extends Block
{

	public BlockPillar() 
	{
		super(Material.ROCK);
		this.setSoundType(SoundType.METAL);
		this.setHardness(2.0F);
	}

}