package com.gildedgames.the_aether.world.biome.decoration;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class AetherGenLakes extends WorldGenerator {

	public AetherGenLakes() {

	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		if (world.getBlock(x, y, z) == BlocksAether.carved_stone
				|| world.getBlock(x, y, z) == BlocksAether.locked_carved_stone
				|| world.getBlock(x, y, z) == BlocksAether.angelic_stone
				|| world.getBlock(x, y, z) == BlocksAether.locked_angelic_stone
				|| world.getBlock(x, y, z) == BlocksAether.hellfire_stone
				|| world.getBlock(x, y, z) == BlocksAether.locked_hellfire_stone)
		{
			return false;
		}
		
		x -= 8;

		for (z -= 8; y > 5 && world.isAirBlock(x, y, z); --y) {
			;
		}

		if (y <= 4) {
			return false;
		} else {
			y -= 4;
			boolean[] aboolean = new boolean[2048];
			int l = random.nextInt(4) + 4;
			int i1;

			for (i1 = 0; i1 < l; ++i1) {
				double d0 = random.nextDouble() * 6.0D + 3.0D;
				double d1 = random.nextDouble() * 4.0D + 2.0D;
				double d2 = random.nextDouble() * 6.0D + 3.0D;
				double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
				double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
				double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

				for (int k1 = 1; k1 < 15; ++k1) {
					for (int l1 = 1; l1 < 15; ++l1) {
						for (int i2 = 1; i2 < 7; ++i2) {
							double d6 = ((double) k1 - d3) / (d0 / 2.0D);
							double d7 = ((double) i2 - d4) / (d1 / 2.0D);
							double d8 = ((double) l1 - d5) / (d2 / 2.0D);
							double d9 = d6 * d6 + d7 * d7 + d8 * d8;

							if (d9 < 1.0D) {
								aboolean[(k1 * 16 + l1) * 8 + i2] = true;
							}
						}
					}
				}
			}

			int j1;
			int j2;
			boolean flag;

			for (i1 = 0; i1 < 16; ++i1) {
				for (j2 = 0; j2 < 16; ++j2) {
					for (j1 = 0; j1 < 8; ++j1) {
						flag = !aboolean[(i1 * 16 + j2) * 8 + j1] && (i1 < 15 && aboolean[((i1 + 1) * 16 + j2) * 8 + j1] || i1 > 0 && aboolean[((i1 - 1) * 16 + j2) * 8 + j1] || j2 < 15 && aboolean[(i1 * 16 + j2 + 1) * 8 + j1] || j2 > 0 && aboolean[(i1 * 16 + (j2 - 1)) * 8 + j1] || j1 < 7 && aboolean[(i1 * 16 + j2) * 8 + j1 + 1] || j1 > 0 && aboolean[(i1 * 16 + j2) * 8 + (j1 - 1)]);

						if (flag) {
							Material material = world.getBlock(x + i1, y + j1, z + j2).getMaterial();

							if (j1 >= 4 && material.isLiquid()) {
								return false;
							}

							if (j1 < 4 && !material.isSolid() && world.getBlock(x + i1, y + j1, z + j2) != Blocks.water) {
								return false;
							}
						}
					}
				}
			}

			for (i1 = 0; i1 < 16; ++i1) {
				for (j2 = 0; j2 < 16; ++j2) {
					for (j1 = 0; j1 < 8; ++j1) {
						if (aboolean[(i1 * 16 + j2) * 8 + j1]) {
							world.setBlock(x + i1, y + j1, z + j2, j1 >= 4 ? Blocks.air : Blocks.water, 0, 2);
						}
					}
				}
			}

			for (i1 = 0; i1 < 16; ++i1) {
				for (j2 = 0; j2 < 16; ++j2) {
					for (j1 = 4; j1 < 8; ++j1) {
						if (aboolean[(i1 * 16 + j2) * 8 + j1] && world.getBlock(x + i1, y + j1 - 1, z + j2) == BlocksAether.aether_dirt && world.getSavedLightValue(EnumSkyBlock.Sky, x + i1, y + j1, z + j2) > 0) {
							world.setBlock(x + i1, y + j1 - 1, z + j2, BlocksAether.aether_grass, 0, 2);
						}
					}
				}
			}

			for (i1 = 0; i1 < 16; ++i1) {
				for (j2 = 0; j2 < 16; ++j2) {
					byte b0 = 4;

					if (world.isBlockFreezable(x + i1, y + b0, z + j2)) {
						world.setBlock(x + i1, y + b0, z + j2, Blocks.ice, 0, 2);
					}
				}
			}

			return true;
		}
	}

}