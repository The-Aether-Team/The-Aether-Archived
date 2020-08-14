package com.gildedgames.the_aether.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.blocks.BlocksAether;

public class BlockAetherFence extends BlockFence {

	public BlockAetherFence() {
		super(Aether.find("skyroot_planks"), Material.wood);

		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setStepSound(soundTypeWood);
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canConnectFenceTo(IBlockAccess p_149826_1_, int p_149826_2_, int p_149826_3_, int p_149826_4_) {
		Block block = p_149826_1_.getBlock(p_149826_2_, p_149826_3_, p_149826_4_);
		return block != this && block != Blocks.fence_gate && block != BlocksAether.skyroot_fence_gate ? (block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false) : true;
	}

}