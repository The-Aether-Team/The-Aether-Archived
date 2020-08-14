package com.gildedgames.the_aether.world.biome.decoration;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class AetherGenHolidayTree extends WorldGenerator {

	public AetherGenHolidayTree() {
		super(true);
	}

	@Override /* STILL No time and effort was made into fixing this ~Kino 11-12-18*/
	public boolean generate(World world, Random rand, int x, int y, int z) {
		Block block = world.getBlock(x, y - 1, z);

		if (block == BlocksAether.aether_grass) {
			for (int xss = x - 7; xss < x + 7; xss++) {
				for (int yss = z - 7; yss < z + 7; yss++) {
					if (((xss - x) * (xss - x) + (yss - z) * (yss - z)) < 29) {
						int x1 = xss;
						int z1 = yss;
						int y1 = world.getHeightValue(x1, z1);

						Block block1 = world.getBlock(x1, y1 - 1, z1);

						if (block1 != Blocks.air) {
							if (rand.nextInt(80) == 0 && (block1 == BlocksAether.aether_grass)) {
								world.setBlock(x1, y1, z1, BlocksAether.present);
							} else if (block1 == Blocks.water) {
								world.setBlock(x1, y1 - 1, z1, Blocks.ice);
								world.setBlock(x1, y1, z1, Blocks.snow_layer);
							} else if (world.getBlock(x1, y1, z1) != Blocks.snow_layer || world.getBlock(x1, y1, z1) != BlocksAether.present) {
								world.setBlock(x1, y1, z1, Blocks.snow_layer);
							}
						}
					}
				}
			}

			for (int y1 = 0; y1 <= 8; y1++) {
				world.setBlock(x, y + y1, z, BlocksAether.skyroot_log);
			}

			world.setBlock(x, y + 9, z, getBlock(rand));

			world.setBlock(x + 0, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 2, 2 + z, BlocksAether.skyroot_log);
			world.setBlock(x + 0, y + 2, 3 + z, getBlock(rand));
			world.setBlock(x + 0, y + 2, 4 + z, getBlock(rand));
			world.setBlock(x + 0, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 2, -2 + z, getBlock(rand));
			world.setBlock(x + 0, y + 2, -3 + z, getBlock(rand));
			world.setBlock(x + 0, y + 2, -4 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, 0 + z, getBlock(rand));
			world.setBlock(x + 2, y + 2, 0 + z, getBlock(rand));
			world.setBlock(x + 3, y + 2, 0 + z, getBlock(rand));
			world.setBlock(x + 4, y + 2, 0 + z, getBlock(rand));
			world.setBlock(x + -1, y + 2, 0 + z, getBlock(rand));
			world.setBlock(x + -2, y + 2, 0 + z, getBlock(rand));
			world.setBlock(x + -3, y + 2, 0 + z, BlocksAether.skyroot_log);
			world.setBlock(x + -4, y + 2, 0 + z, getBlock(rand));

			world.setBlock(x + 1, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, 2 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, 3 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, 4 + z, getBlock(rand));
			world.setBlock(x + -1, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 2, 2 + z, BlocksAether.skyroot_log);
			world.setBlock(x + -1, y + 2, 3 + z, getBlock(rand));
			world.setBlock(x + -1, y + 2, 4 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, -2 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, -3 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, -4 + z, getBlock(rand));
			world.setBlock(x + -1, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 2, -2 + z, BlocksAether.skyroot_log);
			world.setBlock(x + -1, y + 2, -3 + z, getBlock(rand));
			world.setBlock(x + -1, y + 2, -4 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + 2, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + 3, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + 4, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + 2, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + 3, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + 4, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + -2, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + -3, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + -4, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + -2, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + -3, y + 2, -1 + z, getBlock(rand));
			world.setBlock(x + -4, y + 2, -1 + z, getBlock(rand));

			world.setBlock(x + 2, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + 3, y + 2, 1 + z, getBlock(rand));
			world.setBlock(x + 2, y + 2, 1 + z, getBlock(rand));

			world.setBlock(x + -2, y + 2, 2 + z, getBlock(rand));
			world.setBlock(x + -3, y + 2, 2 + z, getBlock(rand));
			world.setBlock(x + -2, y + 2, 3 + z, getBlock(rand));

			world.setBlock(x + 2, y + 2, -2 + z, getBlock(rand));
			world.setBlock(x + 3, y + 2, -2 + z, getBlock(rand));
			world.setBlock(x + 2, y + 2, -3 + z, getBlock(rand));

			world.setBlock(x + -2, y + 2, -2 + z, getBlock(rand));
			world.setBlock(x + -3, y + 2, -2 + z, getBlock(rand));
			world.setBlock(x + -2, y + 2, -3 + z, getBlock(rand));

			world.setBlock(x + 0, y + 3, 1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 3, 2 + z, getBlock(rand));
			world.setBlock(x + 0, y + 3, 3 + z, getBlock(rand));
			world.setBlock(x + 0, y + 3, 4 + z, getBlock(rand));
			world.setBlock(x + 0, y + 3, -1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 3, -2 + z, getBlock(rand));
			world.setBlock(x + 0, y + 3, -3 + z, getBlock(rand));
			world.setBlock(x + 0, y + 3, -4 + z, getBlock(rand));
			world.setBlock(x + 1, y + 3, 0 + z, getBlock(rand));
			world.setBlock(x + 2, y + 3, 0 + z, getBlock(rand));
			world.setBlock(x + 3, y + 3, 0 + z, getBlock(rand));
			world.setBlock(x + 4, y + 3, 0 + z, getBlock(rand));
			world.setBlock(x + -1, y + 3, 0 + z, getBlock(rand));
			world.setBlock(x + -2, y + 3, 0 + z, getBlock(rand));
			world.setBlock(x + -3, y + 3, 0 + z, getBlock(rand));
			world.setBlock(x + -4, y + 3, 0 + z, getBlock(rand));

			world.setBlock(x + 1, y + 3, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 3, 2 + z, getBlock(rand));
			world.setBlock(x + 1, y + 3, 3 + z, getBlock(rand));
			world.setBlock(x + -1, y + 3, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 3, 2 + z, getBlock(rand));
			world.setBlock(x + -1, y + 3, 3 + z, getBlock(rand));
			world.setBlock(x + 1, y + 3, -1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 3, -2 + z, getBlock(rand));
			world.setBlock(x + 1, y + 3, -3 + z, getBlock(rand));
			world.setBlock(x + -1, y + 3, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 3, -2 + z, getBlock(rand));
			world.setBlock(x + -1, y + 3, -3 + z, getBlock(rand));
			world.setBlock(x + 1, y + 3, 1 + z, getBlock(rand));
			world.setBlock(x + 2, y + 3, 1 + z, getBlock(rand));
			world.setBlock(x + 3, y + 3, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 3, -1 + z, getBlock(rand));
			world.setBlock(x + 2, y + 3, -1 + z, getBlock(rand));
			world.setBlock(x + 3, y + 3, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 3, 1 + z, getBlock(rand));
			world.setBlock(x + -2, y + 3, 1 + z, getBlock(rand));
			world.setBlock(x + -3, y + 3, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 3, -1 + z, getBlock(rand));
			world.setBlock(x + -2, y + 3, -1 + z, getBlock(rand));
			world.setBlock(x + -3, y + 3, -1 + z, getBlock(rand));

			world.setBlock(x + 2, y + 3, 2 + z, getBlock(rand));
			world.setBlock(x + 3, y + 3, 2 + z, getBlock(rand));
			world.setBlock(x + 2, y + 3, 3 + z, getBlock(rand));

			world.setBlock(x + -2, y + 3, 2 + z, getBlock(rand));
			world.setBlock(x + -3, y + 3, 2 + z, getBlock(rand));
			world.setBlock(x + -2, y + 3, 3 + z, getBlock(rand));

			world.setBlock(x + 2, y + 3, -2 + z, getBlock(rand));
			world.setBlock(x + 3, y + 3, -2 + z, getBlock(rand));
			world.setBlock(x + 2, y + 3, -3 + z, getBlock(rand));

			world.setBlock(x + -2, y + 3, -1 + z, getBlock(rand));
			world.setBlock(x + -3, y + 3, -2 + z, getBlock(rand));
			world.setBlock(x + -2, y + 3, -3 + z, getBlock(rand));

			world.setBlock(x + 0, y + 4, 1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 4, 2 + z, getBlock(rand));
			world.setBlock(x + 0, y + 4, 3 + z, getBlock(rand));
			world.setBlock(x + 0, y + 4, -1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 4, -2 + z, getBlock(rand));
			world.setBlock(x + 0, y + 4, -3 + z, getBlock(rand));
			world.setBlock(x + 1, y + 4, 0 + z, getBlock(rand));
			world.setBlock(x + 2, y + 4, 0 + z, getBlock(rand));
			world.setBlock(x + 3, y + 4, 0 + z, getBlock(rand));
			world.setBlock(x + -1, y + 4, 0 + z, getBlock(rand));
			world.setBlock(x + -2, y + 4, 0 + z, getBlock(rand));
			world.setBlock(x + -3, y + 4, 0 + z, getBlock(rand));

			world.setBlock(x + 1, y + 4, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 4, 2 + z, getBlock(rand));
			world.setBlock(x + 1, y + 4, 3 + z, getBlock(rand));
			world.setBlock(x + -1, y + 4, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 4, 2 + z, getBlock(rand));
			world.setBlock(x + -1, y + 4, 3 + z, getBlock(rand));
			world.setBlock(x + 1, y + 4, -1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 4, -2 + z, getBlock(rand));
			world.setBlock(x + 1, y + 4, -3 + z, getBlock(rand));
			world.setBlock(x + -1, y + 4, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 4, -2 + z, getBlock(rand));
			world.setBlock(x + -1, y + 4, -3 + z, getBlock(rand));
			world.setBlock(x + 1, y + 4, 1 + z, getBlock(rand));
			world.setBlock(x + 2, y + 4, 1 + z, getBlock(rand));
			world.setBlock(x + 3, y + 4, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 4, -1 + z, getBlock(rand));
			world.setBlock(x + 2, y + 4, -1 + z, getBlock(rand));
			world.setBlock(x + 3, y + 4, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 4, 1 + z, getBlock(rand));
			world.setBlock(x + -2, y + 4, 1 + z, getBlock(rand));
			world.setBlock(x + -3, y + 4, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 4, -1 + z, getBlock(rand));
			world.setBlock(x + -2, y + 4, -1 + z, getBlock(rand));
			world.setBlock(x + -3, y + 4, -1 + z, getBlock(rand));

			world.setBlock(x + 2, y + 4, 2 + z, getBlock(rand));

			world.setBlock(x + -2, y + 4, 2 + z, getBlock(rand));

			world.setBlock(x + 2, y + 4, -2 + z, getBlock(rand));

			world.setBlock(x + -2, y + 4, -2 + z, getBlock(rand));

			world.setBlock(x + 0, y + 5, 1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 5, 2 + z, getBlock(rand));
			world.setBlock(x + 0, y + 5, 3 + z, getBlock(rand));
			world.setBlock(x + 0, y + 5, -1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 5, -2 + z, getBlock(rand));
			world.setBlock(x + 0, y + 5, -3 + z, getBlock(rand));
			world.setBlock(x + 1, y + 5, 0 + z, getBlock(rand));
			world.setBlock(x + 2, y + 5, 0 + z, getBlock(rand));
			world.setBlock(x + 3, y + 5, 0 + z, getBlock(rand));
			world.setBlock(x + -1, y + 5, 0 + z, getBlock(rand));
			world.setBlock(x + -2, y + 5, 0 + z, getBlock(rand));
			world.setBlock(x + -3, y + 5, 0 + z, getBlock(rand));

			world.setBlock(x + 1, y + 5, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 5, 2 + z, getBlock(rand));
			world.setBlock(x + -1, y + 5, 1 + z, BlocksAether.skyroot_log);
			world.setBlock(x + -1, y + 5, 2 + z, getBlock(rand));
			world.setBlock(x + 1, y + 5, -1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 5, -2 + z, getBlock(rand));
			world.setBlock(x + -1, y + 5, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 5, -2 + z, getBlock(rand));
			world.setBlock(x + 1, y + 5, 1 + z, getBlock(rand));
			world.setBlock(x + 2, y + 5, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 5, -1 + z, getBlock(rand));
			world.setBlock(x + 2, y + 5, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 5, 1 + z, getBlock(rand));
			world.setBlock(x + -2, y + 5, 1 + z, BlocksAether.skyroot_log);
			world.setBlock(x + -1, y + 5, -1 + z, getBlock(rand));
			world.setBlock(x + -2, y + 5, -1 + z, getBlock(rand));

			world.setBlock(x + 2, y + 5, 2 + z, getBlock(rand));

			world.setBlock(x + -2, y + 5, 2 + z, getBlock(rand));

			world.setBlock(x + 2, y + 5, -2 + z, getBlock(rand));

			world.setBlock(x + -2, y + 5, -2 + z, getBlock(rand));

			world.setBlock(x + 0, y + 6, 1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 6, 2 + z, getBlock(rand));
			world.setBlock(x + 0, y + 6, -1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 6, -2 + z, getBlock(rand));
			world.setBlock(x + 1, y + 6, 0 + z, getBlock(rand));
			world.setBlock(x + 2, y + 6, 0 + z, getBlock(rand));
			world.setBlock(x + -1, y + 6, 0 + z, getBlock(rand));
			world.setBlock(x + -2, y + 6, 0 + z, getBlock(rand));

			world.setBlock(x + 1, y + 6, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 6, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 6, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 6, -1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 6, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 6, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 6, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 6, -1 + z, getBlock(rand));

			world.setBlock(x + 0, y + 7, 1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 7, -1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 7, 0 + z, getBlock(rand));
			world.setBlock(x + -1, y + 7, 0 + z, getBlock(rand));

			world.setBlock(x + 1, y + 7, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 7, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 7, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 7, -1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 7, 1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 7, -1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 7, 1 + z, getBlock(rand));
			world.setBlock(x + -1, y + 7, -1 + z, getBlock(rand));

			world.setBlock(x + 0, y + 8, 1 + z, getBlock(rand));
			world.setBlock(x + 0, y + 8, -1 + z, getBlock(rand));
			world.setBlock(x + 1, y + 8, 0 + z, getBlock(rand));
			world.setBlock(x + -1, y + 8, 0 + z, getBlock(rand));

			world.setBlock(x, y - 1, z, BlocksAether.aether_dirt);

			for (int xss = x - 7; xss < x + 7; xss++) {
				for (int yss = z - 7; yss < z + 7; yss++) {
					if (((xss - x) * (xss - x) + (yss - z) * (yss - z)) < 29) {
						int x1 = xss;
						int z1 = yss;
						int y1 = world.getHeightValue(x1, z1);

						Block block1 = world.getBlock(x1, y1 - 1, z1);

						if (block1 != Blocks.air) {
							if (rand.nextInt(40) == 0 && (block1 == BlocksAether.aether_grass)) {
								world.setBlock(x1, y1, z1, BlocksAether.present);
							} else if (block1 == Blocks.water) {
								world.setBlock(x1, y1 - 1, z1, Blocks.ice);
								world.setBlock(x1, y1, z1, Blocks.snow_layer);
							} else if (world.getBlock(x1, y1, z1) != Blocks.snow_layer || world.getBlock(x1, y1, z1) != BlocksAether.present) {
								world.setBlock(x1, y1, z1, Blocks.snow_layer);
							}
						}
					}
				}
			}
		}

		return false;
	}

	public Block getBlock(Random rand) {
		return rand.nextInt(4) == 0 ? BlocksAether.decorated_holiday_leaves : BlocksAether.holiday_leaves;
	}
}