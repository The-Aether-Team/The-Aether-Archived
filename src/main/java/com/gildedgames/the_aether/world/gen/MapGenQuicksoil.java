package com.gildedgames.the_aether.world.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class MapGenQuicksoil extends MapGenBase {

	@Override
	public void func_151539_a(IChunkProvider provider, World p_151539_2_, int p_151539_3_, int p_151539_4_, Block[] blocks) {
		if (this.rand.nextInt(10) == 0) {
			for (int x = 3; x < 12; x++) {
				for (int z = 3; z < 12; z++) {
					for (int n = 3; n < 48; n++) {
						int pos = (z * 16 + x) * 128 + n;

						if (blocks[pos] == Blocks.air && blocks[(z * 16 + x) * 128 + (n + 1)] == BlocksAether.aether_grass && blocks[(z * 16 + x) * 128 + (n + 2)] == Blocks.air) {
							this.generate(blocks, x, n, z);
							n += 128;
						}
					}
				}
			}
		}
	}

	private void generate(Block[] blocks, int posX, int posY, int posZ) {
		for (int x = posX - 3; x < posX + 4; x++) {
			for (int z = posZ - 3; z < posZ + 4; z++) {
				int position = (z * 16 + x) * 128 + posY;

				if (blocks[position] == Blocks.air && ((x - posX) * (x - posX) + (z - posZ) * (z - posZ)) < 12) {
					blocks[position] = BlocksAether.quicksoil;
				}
			}
		}
	}

}