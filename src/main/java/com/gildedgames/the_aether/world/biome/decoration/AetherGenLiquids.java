package com.gildedgames.the_aether.world.biome.decoration;

import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class AetherGenLiquids extends WorldGenerator {

	public AetherGenLiquids() {
		super();
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		if (world.getBlock(x, y + 1, z) != BlocksAether.holystone) {
			return false;
		} else if (world.getBlock(x, y - 1, z) != BlocksAether.holystone) {
			return false;
		} else if (world.getBlock(x, y, z).getMaterial() != Material.air && world.getBlock(x, y, z) != BlocksAether.holystone) {
			return false;
		} else {
			int l = 0;

			if (world.getBlock(x - 1, y, z) == BlocksAether.holystone) {
				++l;
			}

			if (world.getBlock(x + 1, y, z) == BlocksAether.holystone) {
				++l;
			}

			if (world.getBlock(x, y, z - 1) == BlocksAether.holystone) {
				++l;
			}

			if (world.getBlock(x, y, z + 1) == BlocksAether.holystone) {
				++l;
			}

			int i1 = 0;

			if (world.isAirBlock(x - 1, y, z)) {
				++i1;
			}

			if (world.isAirBlock(x + 1, y, z)) {
				++i1;
			}

			if (world.isAirBlock(x, y, z - 1)) {
				++i1;
			}

			if (world.isAirBlock(x, y, z + 1)) {
				++i1;
			}

			if (l == 3 && i1 == 1) {
				world.setBlock(x, y, z, Blocks.water, 0, 2);
				world.scheduledUpdatesAreImmediate = true;
				Blocks.water.updateTick(world, x, y, z, random);
				world.scheduledUpdatesAreImmediate = false;
			}

			return true;
		}
	}

}