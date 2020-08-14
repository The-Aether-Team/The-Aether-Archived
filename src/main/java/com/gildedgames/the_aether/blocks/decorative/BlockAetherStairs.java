package com.gildedgames.the_aether.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockAetherStairs extends BlockStairs {

	public BlockAetherStairs(Block block) {
		super(block, 0);

		this.setLightOpacity(0);
	}

}