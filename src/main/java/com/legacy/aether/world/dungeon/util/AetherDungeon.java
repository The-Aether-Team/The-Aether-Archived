package com.legacy.aether.world.dungeon.util;

import java.util.Random;

import com.legacy.aether.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class AetherDungeon extends WorldGenerator {

	protected final Random random = new Random();

	public int chance;

	public Block airState = Blocks.air, blockState, extraBlockState;

	public boolean replaceAir, replaceSolid;

	public AetherDungeon() {
		super();
	}

	public void setBlocks(Block blockState) {
		this.blockState = blockState;
		this.extraBlockState = null;
		this.chance = 0;
	}

	public void setBlocks(Block blockState, Block extraBlockState, int chances) {
		this.blockState = blockState;
		this.extraBlockState = extraBlockState;
		this.chance = chances;

		if (this.chance < 1) {
			this.chance = 1;
		}
	}

	public void addLineX(World world, Random rand, PositionData pos, int radius) {
		for (int lineX = pos.getX(); lineX < pos.getX() + radius; lineX++) {
			Block block = world.getBlock(lineX, pos.getY(), pos.getZ());

			if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
				if (this.chance == 0) {
					this.setBlockAndNotifyAdequately(world, lineX, pos.getY(), pos.getZ(), this.blockState, 0);

					return;
				}

				if (rand.nextInt(this.chance) == 0) {
					this.setBlockAndNotifyAdequately(world, lineX, pos.getY(), pos.getZ(), this.extraBlockState, 0);
				} else {
					this.setBlockAndNotifyAdequately(world, lineX, pos.getY(), pos.getZ(), this.blockState, 0);
				}
			}
		}
	}

	public void addLineY(World world, Random rand, PositionData pos, int radius) {
		for (int lineY = pos.getY(); lineY < pos.getY() + radius; lineY++) {
			Block block = world.getBlock(pos.getX(), lineY, pos.getZ());

			if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
				if (this.chance == 0) {
					this.setBlockAndNotifyAdequately(world, pos.getX(), lineY, pos.getZ(), this.blockState, 0);

					return;
				}

				if (rand.nextInt(this.chance) == 0) {
					this.setBlockAndNotifyAdequately(world, pos.getX(), lineY, pos.getZ(), this.extraBlockState, 0);
				} else {
					this.setBlockAndNotifyAdequately(world, pos.getX(), lineY, pos.getZ(), this.blockState, 0);
				}
			}
		}
	}

	public void addLineZ(World world, Random rand, PositionData pos, int radius) {
		for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius; lineZ++) {
			Block block = world.getBlock(pos.getX(), pos.getY(), lineZ);

			if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
				if (this.chance == 0) {
					this.setBlockAndNotifyAdequately(world, pos.getX(), pos.getY(), lineZ, this.blockState, 0);

					return;
				}

				if (rand.nextInt(this.chance) == 0) {
					this.setBlockAndNotifyAdequately(world, pos.getX(), pos.getY(), lineZ, this.extraBlockState, 0);
				} else {
					this.setBlockAndNotifyAdequately(world, pos.getX(), pos.getY(), lineZ, this.blockState, 0);
				}
			}
		}
	}

	public void addPlaneX(World world, Random rand, PositionData pos, PositionData radius) {
		for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++) {
			for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++) {
				Block block = world.getBlock(pos.getX(), lineY, lineZ);

				if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
					if (this.chance == 0) {
						this.setBlockAndNotifyAdequately(world, pos.getX(), lineY, lineZ, this.blockState, 0);

						return;
					}

					if (rand.nextInt(this.chance) == 0) {
						this.setBlockAndNotifyAdequately(world, pos.getX(), lineY, lineZ, this.extraBlockState, 0);
					} else {
						this.setBlockAndNotifyAdequately(world, pos.getX(), lineY, lineZ, this.blockState, 0);
					}
				}
			}
		}
	}

	public void addPlaneY(World world, Random rand, PositionData pos, PositionData radius)//, int radiusX, int radiusZ)
	{
		for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++) {
			for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++) {
				Block block = world.getBlock(lineX, pos.getY(), lineZ);

				if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
					if (this.chance == 0) {
						this.setBlockAndNotifyAdequately(world, lineX, pos.getY(), lineZ, this.blockState, 0);

						return;
					}

					if (rand.nextInt(this.chance) == 0) {
						this.setBlockAndNotifyAdequately(world, lineX, pos.getY(), lineZ, this.extraBlockState, 0);
					} else {
						this.setBlockAndNotifyAdequately(world, lineX, pos.getY(), lineZ, this.blockState, 0);
					}
				}
			}
		}
	}

	public void addPlaneZ(World world, Random rand, PositionData pos, PositionData radius)//, int radiusX, int radiusY)
	{
		for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++) {
			for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++) {
				Block block = world.getBlock(lineX, lineY, pos.getZ());

				if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
					if (this.chance == 0) {
						this.setBlockAndNotifyAdequately(world, lineX, lineY, pos.getZ(), this.blockState, 0);

						return;
					}

					if (rand.nextInt(this.chance) == 0) {
						this.setBlockAndNotifyAdequately(world, lineX, lineY, pos.getZ(), this.extraBlockState, 0);
					} else {
						this.setBlockAndNotifyAdequately(world, lineX, lineY, pos.getZ(), this.blockState, 0);
					}
				}
			}
		}
	}

	public void addHollowBox(World world, Random rand, PositionData pos, PositionData radius) {
		Block temp1 = this.blockState;
		Block temp2 = this.extraBlockState;

		this.setBlocks(this.airState, this.airState, this.chance);
		this.addSolidBox(world, rand, pos, radius);
		this.setBlocks(temp1, temp2, this.chance);
		this.addPlaneY(world, rand, pos, radius);
		this.addPlaneY(world, rand, new PositionData(pos.getX(), pos.getY() + (radius.getY() - 1), pos.getZ()), radius);
		this.addPlaneX(world, rand, pos, radius);
		this.addPlaneX(world, rand, new PositionData(pos.getX() + (radius.getX() - 1), pos.getY(), pos.getZ()), radius);
		this.addPlaneZ(world, rand, pos, radius);
		this.addPlaneZ(world, rand, new PositionData(pos.getX(), pos.getY(), pos.getZ() + (radius.getZ() - 1)), radius);
	}

	public void addSquareTube(World world, Random rand, PositionData pos, PositionData radius, int angel) {
		Block temp1 = this.blockState;
		Block temp2 = this.extraBlockState;

		this.setBlocks(this.airState, this.airState, this.chance);
		this.addSolidBox(world, rand, pos, radius);
		this.setBlocks(temp1, temp2, this.chance);

		if (angel == 0 || angel == 2) {
			this.addPlaneY(world, rand, pos, radius);
			this.addPlaneY(world, rand, new PositionData(pos.getX(), pos.getY() + (radius.getY() - 1), pos.getZ()), radius);
		}

		if (angel == 1 || angel == 2) {
			this.addPlaneX(world, rand, pos, radius);
			this.addPlaneX(world, rand, new PositionData(pos.getX() + (radius.getX() - 1), pos.getY(), pos.getZ()), radius);
		}

		if (angel == 0 || angel == 1) {
			this.addPlaneZ(world, rand, pos, radius);
			this.addPlaneZ(world, rand, new PositionData(pos.getX(), pos.getY(), pos.getZ() + (radius.getZ() - 1)), radius);
		}
	}

	public void addSolidBox(World world, Random rand, PositionData pos, PositionData radius) {
		for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++) {
			for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++) {
				for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++) {
					Block block = world.getBlock(lineX, lineY, lineZ);

					if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
						if (this.chance == 0) {
							this.setBlockAndNotifyAdequately(world, lineX, lineY, lineZ, this.blockState, 0);

							return;
						}

						if (rand.nextInt(this.chance) == 0) {
							this.setBlockAndNotifyAdequately(world, lineX, lineY, lineZ, this.extraBlockState, 0);
						} else {
							this.setBlockAndNotifyAdequately(world, lineX, lineY, lineZ, this.blockState, 0);
						}
					}
				}
			}
		}
	}

	public boolean isBoxSolid(World world, PositionData pos, PositionData radius) {
		boolean flag = true;

		for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++) {
			for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++) {
				for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++) {
					if (world.getBlock(lineX, lineY, lineZ) == Blocks.air) {
						flag = false;
					}
				}
			}
		}

		return flag;
	}

	public boolean isBoxEmpty(World world, PositionData pos, PositionData radius) {
		boolean flag = true;

		for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++) {
			for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++) {
				for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++) {
					if (world.getBlock(lineX, lineY, lineZ) != Blocks.air) {
						flag = false;
					}
				}
			}
		}

		return flag;
	}

	public boolean hasBlock(World world, PositionData pos, PositionData radius, Block block) {
		boolean flag = false;

		for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++) {
			for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++) {
				for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++) {
					if (world.getBlock(lineX, lineY, lineZ) == block) {
						flag = true;
					}
				}
			}
		}

		return flag;
	}

	protected void setBlock(World world, Random random, int x, int y, int z, Block state, Block extraState, int chance) {
		if (random.nextInt(chance) == 0) {
			this.setBlockAndNotifyAdequately(world, x, y, z, extraState, 0);
		} else {
			this.setBlockAndNotifyAdequately(world, x, y, z, state, 0);
		}
	}

}