package com.gildedgames.the_aether.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSkyrootPlank extends Block
{

	public BlockSkyrootPlank() 
	{
		super(Material.WOOD);

		this.setHardness(2F);
		this.setResistance(5F);
		this.setSoundType(SoundType.WOOD);
	}

}