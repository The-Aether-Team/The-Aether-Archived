package com.gildedgames.the_aether.blocks.decorative;

import net.minecraft.block.BlockTorch;

public class BlockAmbrosiumTorch extends BlockTorch {

	public BlockAmbrosiumTorch() {
		super();

		this.setTickRandomly(true);
		this.setLightLevel(0.9375F);
		this.setStepSound(soundTypeWood);
	}

}