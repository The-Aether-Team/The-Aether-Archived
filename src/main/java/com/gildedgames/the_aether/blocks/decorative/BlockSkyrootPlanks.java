package com.gildedgames.the_aether.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.gildedgames.the_aether.Aether;

public class BlockSkyrootPlanks extends Block {

	public BlockSkyrootPlanks() {
		super(Material.wood);

		this.setHardness(2F);
		this.setResistance(5F);
		this.setStepSound(soundTypeWood);
		this.setBlockTextureName(Aether.find("skyroot_planks"));
	}

}