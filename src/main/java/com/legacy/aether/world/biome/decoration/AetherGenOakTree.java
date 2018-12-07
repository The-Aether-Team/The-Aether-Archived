package com.legacy.aether.world.biome.decoration;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import com.legacy.aether.blocks.BlocksAether;

public class AetherGenOakTree extends WorldGenAbstractTree {

	public AetherGenOakTree() {
		super(true);
	}

	public boolean branch(World world, Random random, int x, int y, int z, int slant) {
		int directionX = random.nextInt(3) - 1;
		int directionY = slant;
		int directionZ = random.nextInt(3) - 1;
		int i = x;
		int k = z;

		for (int n = 0; n < random.nextInt(2); n++) {
			x += directionX;
			y += directionY;
			z += directionZ;
			i -= directionX;
			k -= directionZ;

			if (world.getBlock(x, y, z) == BlocksAether.golden_oak_leaves) {
				world.setBlock(x, y, z, BlocksAether.golden_oak_log);
				world.setBlock(i, y, k, BlocksAether.golden_oak_log);
			}
		}

		return true;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		if (world.getBlock(x, y - 1, z) != BlocksAether.aether_grass && world.getBlock(x, y - 1, z) != BlocksAether.aether_dirt) {
			return false;
		}

		int height = 9;

		for (int x1 = x - 3; x1 < x + 4; x1++) {
			for (int y1 = y + 5; y1 < y + 12; y1++) {
				for (int z1 = z - 3; z1 < z + 4; z1++) {
					if ((x1 - x) * (x1 - x) + (y1 - y - 8) * (y1 - y - 8) + (z1 - z) * (z1 - z) < 12 + random.nextInt(5) && world.isAirBlock(x1, y1, z1)) {
						world.setBlock(x1, y1, z1, BlocksAether.golden_oak_leaves);
					}
				}
			}
		}

		for (int n = 0; n < height; n++) {
			if (n > 4) {
				if (random.nextInt(3) > 0) {
					branch(world, random, x, y + n, z, n / 4 - 1);
				}
			}

			world.setBlock(x, y + n, z, BlocksAether.golden_oak_log);
		}

		return true;
	}

}