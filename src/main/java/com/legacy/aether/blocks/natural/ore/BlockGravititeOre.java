package com.legacy.aether.blocks.natural.ore;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import com.legacy.aether.blocks.util.BlockFloating;

public class BlockGravititeOre extends BlockFloating
{

	public BlockGravititeOre()
	{
		super(Material.ROCK, false);

		this.setHardness(5F);
		this.setResistance(5F);
		this.setSoundType(SoundType.STONE);
	}

}