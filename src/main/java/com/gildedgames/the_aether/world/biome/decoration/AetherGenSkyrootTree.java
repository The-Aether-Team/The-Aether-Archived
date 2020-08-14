package com.gildedgames.the_aether.world.biome.decoration;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class AetherGenSkyrootTree extends WorldGenAbstractTree {

	public AetherGenSkyrootTree(boolean notify) {
		super(notify);
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		int l = random.nextInt(3) + 4;

		Block j1 = world.getBlock(x, y - 1, z);

		if (j1 != BlocksAether.aether_grass && j1 != BlocksAether.aether_dirt) {
			return false;
		}

		this.setBlockAndNotifyAdequately(world, x, y - 1, z, BlocksAether.aether_dirt, 0);

		for (int k1 = (y - 3) + l; k1 <= y + l; k1++) {
			int j2 = k1 - (y + l);
			int i3 = 1 - j2 / 2;

			for (int k3 = x - i3; k3 <= x + i3; k3++) {
				int l3 = k3 - x;

				for (int i4 = z - i3; i4 <= z + i3; i4++) {
					int j4 = i4 - z;

					if ((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !world.getBlock(k3, k1, i4).isOpaqueCube()) {
						this.setBlockAndNotifyAdequately(world, k3, k1, i4, BlocksAether.skyroot_leaves, 0);
					}
				}

			}
		}

		for (int l1 = 0; l1 < l; l1++) {
			Block k2 = world.getBlock(x, y + l1, z);

			if (k2 == Blocks.air || k2 == BlocksAether.skyroot_leaves) {
				this.setBlockAndNotifyAdequately(world, x, y + l1, z, BlocksAether.skyroot_log, 0);
			}
		}

		return true;
	}

}