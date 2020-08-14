package com.gildedgames.the_aether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockAether extends Block {

	public BlockAether(Material material, String texture) {
		super(material);

		this.setBlockTextureName(texture);
	}

}