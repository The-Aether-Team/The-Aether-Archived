package com.gildedgames.the_aether.blocks.portal;

import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class AetherPortalSize {

	private final World world;
	private final int axis;
	public final int rightDir;
	public final int leftDir;
	public int portalBlockCount = 0;
	public ChunkCoordinates bottomLeft;
	public int height;
	public int width;

	public AetherPortalSize(World worldIn, int x, int y, int z, int axis) {
		this.world = worldIn;
		this.axis = axis;

		this.leftDir = BlockPortal.field_150001_a[axis][0];
		this.rightDir = BlockPortal.field_150001_a[axis][1];

		for (int i1 = y; y > i1 - 21 && y > 0 && this.isEmptyBlock(worldIn.getBlock(x, y - 1, z)); --y) {
			;
		}

		int i = this.getDistanceUntilEdge(x, y, z, this.leftDir) - 1;

		if (i >= 0) {
			this.bottomLeft = new ChunkCoordinates(x + i * Direction.offsetX[this.leftDir], y, z + i * Direction.offsetZ[this.leftDir]);
			this.width = this.getDistanceUntilEdge(this.bottomLeft.posX, this.bottomLeft.posY, this.bottomLeft.posZ, this.rightDir);

			if (this.width < 2 || this.width > 21) {
				this.bottomLeft = null;
				this.width = 0;
			}
		}

		if (this.bottomLeft != null) {
			this.height = this.calculatePortalHeight();
		}
	}

	protected int getDistanceUntilEdge(int x, int y, int z, int leftDir) {
		int j1 = Direction.offsetX[leftDir];
		int k1 = Direction.offsetZ[leftDir];
		int i1;
		Block block;

		for (i1 = 0; i1 < 22; ++i1) {
			block = this.world.getBlock(x + j1 * i1, y, z + k1 * i1);

			if (!this.isEmptyBlock(block)) {
				break;
			}

			Block block1 = this.world.getBlock(x + j1 * i1, y - 1, z + k1 * i1);

			if (block1 != Blocks.glowstone) {
				break;
			}
		}

		block = this.world.getBlock(x + j1 * i1, y, z + k1 * i1);
		return block == Blocks.glowstone ? i1 : 0;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	protected int calculatePortalHeight() {
		label24:

		for (this.height = 0; this.height < 21; ++this.height) {
			for (int i = 0; i < this.width; ++i) {
				int k = this.bottomLeft.posX + i * Direction.offsetX[BlockPortal.field_150001_a[this.axis][1]];
				int l = this.bottomLeft.posZ + i * Direction.offsetZ[BlockPortal.field_150001_a[this.axis][1]];
				Block block = this.world.getBlock(k, this.bottomLeft.posY + this.height, l);

				if (!this.isEmptyBlock(block)) {
					break label24;
				}

				if (block == BlocksAether.aether_portal) {
					++this.portalBlockCount;
				}

				if (i == 0) {
					block = this.world.getBlock(k + Direction.offsetX[BlockPortal.field_150001_a[this.axis][0]], this.bottomLeft.posY + this.height, l + Direction.offsetZ[BlockPortal.field_150001_a[this.axis][0]]);

					if (block != Blocks.glowstone) {
						break label24;
					}
				} else if (i == this.width - 1) {
					block = this.world.getBlock(k + Direction.offsetX[BlockPortal.field_150001_a[this.axis][1]], this.bottomLeft.posY + this.height, l + Direction.offsetZ[BlockPortal.field_150001_a[this.axis][1]]);

					if (block != Blocks.glowstone) {
						break label24;
					}
				}
			}
		}

		for (int j = 0; j < this.width; ++j) {
			int i = this.bottomLeft.posX + j * Direction.offsetX[BlockPortal.field_150001_a[this.axis][1]];
			int k = this.bottomLeft.posY + this.height;
			int l = this.bottomLeft.posZ + j * Direction.offsetZ[BlockPortal.field_150001_a[this.axis][1]];

			if (this.world.getBlock(i, k, l) != Blocks.glowstone) {
				this.height = 0;
				break;
			}
		}

		if (this.height <= 21 && this.height >= 3) {
			return this.height;
		} else {
			this.bottomLeft = null;
			this.width = 0;
			this.height = 0;
			return 0;
		}
	}

	protected boolean isEmptyBlock(Block blockIn) {
		return blockIn.getMaterial() == Material.air || blockIn == Blocks.fire || blockIn == BlocksAether.aether_portal;
	}

	public boolean isValid() {
		return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
	}

	public void placePortalBlocks() {
		for (int i = 0; i < this.width; ++i) {
			int j = this.bottomLeft.posX + Direction.offsetX[this.rightDir] * i;
			int k = this.bottomLeft.posZ + Direction.offsetZ[this.rightDir] * i;

			for (int l = 0; l < this.height; ++l) {
				int i1 = this.bottomLeft.posY + l;
				this.world.setBlock(j, i1, k, BlocksAether.aether_portal, this.axis, 2);
			}
		}
	}

}