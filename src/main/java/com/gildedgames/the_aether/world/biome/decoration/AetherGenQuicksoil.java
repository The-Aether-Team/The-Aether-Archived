package com.gildedgames.the_aether.world.biome.decoration;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class AetherGenQuicksoil extends WorldGenerator {

	public AetherGenQuicksoil() {
		super();
	}

	public boolean generate(World world, Random random, int x, int y, int z) {
		for (int x1 = x - 3; x1 < x + 4; x1++) {
			for (int z1 = z - 3; z1 < z + 4; z1++) {
				if (world.getBlock(x1, y, z1) == Blocks.air && ((x1 - x) * (x1 - x) + (z1 - z) * (z1 - z)) < 12) {
					this.setBlockAndNotifyAdequately(world, x1, y, z1, BlocksAether.quicksoil, 0);
				}
			}
		}

		return true;
	}

}