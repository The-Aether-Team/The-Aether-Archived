package com.legacy.aether.block.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDungeon extends Block
{

	private boolean isLightBlock;

	public BlockDungeon(boolean isLightBlock)
	{
		super(Properties.create(Material.ROCK));

		this.isLightBlock = isLightBlock;
	}

}