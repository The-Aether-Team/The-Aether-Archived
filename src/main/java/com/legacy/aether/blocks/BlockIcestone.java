package com.legacy.aether.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockIcestone extends Block {

	public BlockIcestone() {
		super(Material.ice);

		this.setHardness(3F);
		this.setTickRandomly(true);
		this.setStepSound(soundTypeGlass);
		this.setHarvestLevel("pickaxe", 1);
		this.setBlockTextureName("aether_legacy:icestone");
	}

	@Override
	public void onBlockAdded(World worldIn, int xIn, int yIn, int zIn) {
		for (int x = xIn - 3; x <= (xIn + 3); ++x) {
			for (int y = yIn - 3; y <= (yIn + 3); ++y) {
				for (int z = zIn - 3; z <= (zIn + 3); ++z) {
					Block block = worldIn.getBlock(x, y, z);

					if (block == Blocks.water || block == Blocks.flowing_water) {
						worldIn.setBlock(x, y, z, Blocks.ice);
					} else if (block == Blocks.lava || block == Blocks.flowing_lava) {
						worldIn.setBlock(x, y, z, Blocks.obsidian);
					}
				}
			}
		}
	}

}