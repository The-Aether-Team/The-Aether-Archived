package com.legacy.aether.block.dungeon;

import net.minecraft.block.state.IBlockState;

public class BlockLockedDungeon extends BlockDungeon
{

	private IBlockState unlockedState;

	public BlockLockedDungeon()
	{
		super(false);
	}

}