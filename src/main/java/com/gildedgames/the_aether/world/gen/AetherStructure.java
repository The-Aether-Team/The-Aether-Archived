package com.gildedgames.the_aether.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public abstract class AetherStructure extends StructureComponent {

	public int chance;

	public Block airState = Blocks.air, blockState, extraBlockState;

	public int meta, extraMeta;

	public boolean replaceAir, replaceSolid;

	public Random random;

	public World worldObj;

	public StructureBoundingBox dungeonBoundingBox;

	private int startX, startY, startZ;

	public void setBlocks(Block blockState, int meta) {
		this.blockState = blockState;
		this.meta = 0;
		this.extraBlockState = null;
		this.extraMeta = 0;
		this.chance = 0;
	}

	public void setBlocks(Block blockState) {
		this.blockState = blockState;
		this.extraBlockState = null;
		this.chance = 0;
	}

	public void setBlocks(Block blockState, int meta, Block extraBlockState, int extraMeta, int chance) {
		this.blockState = blockState;
		this.meta = meta;
		this.extraBlockState = extraBlockState;
		this.extraMeta = extraMeta;
		this.chance = chance;

		if (this.chance < 1) {
			this.chance = 1;
		}
	}

	public void setBlocks(Block blockState, Block extraBlockState, int chance) {
		this.blockState = blockState;
		this.meta = 0;
		this.extraBlockState = extraBlockState;
		this.extraMeta = 0;
		this.chance = chance;

		if (this.chance < 1) {
			this.chance = 1;
		}
	}

	public void setStructureOffset(int x, int y, int z) {
		this.startX = x;
		this.startY = y;
		this.startZ = z;
	}

	public void addLineX(int x, int y, int z, int xRange) {
		for (int lineX = x; lineX < x + xRange; lineX++) {
			Block block = this.getBlockState(lineX + this.startX, y + this.startY, z + this.startZ);

			if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
				this.setBlock(lineX + this.startX, y + this.startY, z + this.startZ);
			}
		}
	}

	public void addLineY(int x, int y, int z, int yRange) {
		for (int lineY = y; lineY < y + yRange; lineY++) {
			Block block = this.getBlockState(x + this.startX, lineY + this.startY, z + this.startZ);

			if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
				this.setBlock(x + this.startX, lineY + this.startY, z + this.startZ);
			}
		}
	}

	public void addLineZ(int x, int y, int z, int zRange) {
		for (int lineZ = z; lineZ < z + zRange; lineZ++) {
			Block block = this.getBlockState(x + this.startX, y + this.startY, lineZ + this.startZ);

			if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
				this.setBlock(x + this.startX, y + this.startY, lineZ + this.startZ);
			}
		}
	}

	public void addPlaneX(int x, int y, int z, int yRange, int zRange) {
		for (int lineY = y; lineY < y + yRange; lineY++) {
			for (int lineZ = z; lineZ < z + zRange; lineZ++) {
				Block block = this.getBlockState(x + this.startX, lineY + this.startY, lineZ + this.startZ);

				if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
					this.setBlock(x + this.startX, lineY + this.startY, lineZ + this.startZ);
				}
			}
		}
	}

	public void addPlaneY(int x, int y, int z, int xRange, int zRange) {
		for (int lineX = x; lineX < x + xRange; lineX++) {
			for (int lineZ = z; lineZ < z + zRange; lineZ++) {
				Block block = this.getBlockState(lineX + this.startX, y + this.startY, lineZ + this.startZ);

				if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
					this.setBlock(lineX + this.startX, y + this.startY, lineZ + this.startZ);
				}
			}
		}
	}

	public void addPlaneZ(int x, int y, int z, int xRange, int yRange) {
		for (int lineX = x; lineX < x + xRange; lineX++) {
			for (int lineY = y; lineY < y + yRange; lineY++) {
				Block block = this.getBlockState(lineX + this.startX, lineY + this.startY, z + this.startZ);

				if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
					this.setBlock(lineX + this.startX, lineY + this.startY, z + this.startZ);
				}
			}
		}
	}

	public void addHollowBox(int x, int y, int z, int xRange, int yRange, int zRange) {
		Block temp1 = this.blockState;
		Block temp2 = this.extraBlockState;

		this.setBlocks(this.airState, this.airState, this.chance);
		this.addSolidBox(x, y, z, xRange, yRange, zRange);
		this.setBlocks(temp1, temp2, this.chance);
		this.addPlaneY(x, y, z, xRange, zRange);
		this.addPlaneY(x, y + (yRange - 1), z, xRange, zRange);
		this.addPlaneX(x, y, z, yRange, zRange);
		this.addPlaneX(x + (xRange - 1), y, z, yRange, zRange);
		this.addPlaneZ(x, y, z, xRange, yRange);
		this.addPlaneZ(x, y, z + (zRange - 1), xRange, yRange);
	}

	public void addSquareTube(int x, int y, int z, int xRange, int yRange, int zRange, int angel) {
		Block temp1 = this.blockState;
		Block temp2 = this.extraBlockState;

		this.setBlocks(this.airState, this.airState, this.chance);
		this.addSolidBox(x, y, z, xRange, yRange, zRange);
		this.setBlocks(temp1, temp2, this.chance);

		if (angel == 0 || angel == 2) {
			this.addPlaneY(x, y, z, xRange, zRange);
			this.addPlaneY(x, y + (yRange - 1), z, xRange, zRange);
		}

		if (angel == 1 || angel == 2) {
			this.addPlaneX(x, y, z, yRange, zRange);
			this.addPlaneX(x + (xRange - 1), y, z, yRange, zRange);
		}

		if (angel == 0 || angel == 1) {
			this.addPlaneZ(x, y, z, xRange, yRange);
			this.addPlaneZ(x, y, z + (zRange - 1), xRange, yRange);
		}
	}

	public void addSolidBox(int x, int y, int z, int xRange, int yRange, int zRange) {
		for (int lineX = x; lineX < x + xRange; lineX++) {
			for (int lineY = y; lineY < y + yRange; lineY++) {
				for (int lineZ = z; lineZ < z + zRange; lineZ++) {
					Block block = this.getBlockState(lineX + this.startX, lineY + this.startY, lineZ + this.startZ);

					if ((this.replaceAir || block != Blocks.air) && (this.replaceSolid || block == Blocks.air)) {
						this.setBlock(lineX + this.startX, lineY + this.startY, lineZ + this.startZ);
					}
				}
			}
		}
	}

	public boolean isBoxSolid(int x, int y, int z, int xRange, int yRange, int zRange) {
		boolean flag = true;

		for (int lineX = x; lineX < x + xRange; lineX++) {
			for (int lineY = y; lineY < y + yRange; lineY++) {
				for (int lineZ = z; lineZ < z + zRange; lineZ++) {
					if (this.getBlockState(lineX + this.startX, lineY + this.startY, lineZ + this.startZ) == Blocks.air) {
						flag = false;
					}
				}
			}
		}

		return flag;
	}

	public boolean isBoxEmpty(int x, int y, int z, int xRange, int yRange, int zRange) {
		boolean flag = true;

		for (int lineX = x; lineX < x + xRange; lineX++) {
			for (int lineY = y; lineY < y + yRange; lineY++) {
				for (int lineZ = z; lineZ < z + zRange; lineZ++) {
					if (this.getBlockState(lineX + this.startX, lineY + this.startY, lineZ + this.startZ) != Blocks.air) {
						flag = false;
					}
				}
			}
		}

		return flag;
	}

	public TileEntity getTileEntityFromPosWithOffset(int x, int y, int z) {
		return !this.dungeonBoundingBox.isVecInside(this.getActualX(x, z), this.getActualY(y), this.getActualZ(x, z)) ? null : this.worldObj.getTileEntity(this.getActualX(x, z), this.getActualY(y), this.getActualZ(x, z));
	}

	public Block getBlockStateWithOffset(int x, int y, int z) {
		return this.getBlockAtCurrentPosition(this.worldObj, x + this.startX, y + this.startY, z + this.startZ, this.dungeonBoundingBox);
	}

	public Block getBlockState(int x, int y, int z) {
		return this.getBlockAtCurrentPosition(this.worldObj, x, y, z, this.dungeonBoundingBox);
	}

	public void setBlockWithOffset(int x, int y, int z, Block state, int meta) {
		this.placeBlockAtCurrentPosition(this.worldObj, state, meta, x + this.startX, y + this.startY, z + this.startZ, this.dungeonBoundingBox);
	}

	public void setBlock(int x, int y, int z, Block state, int meta) {
		this.placeBlockAtCurrentPosition(this.worldObj, state, meta, x, y, z, this.dungeonBoundingBox);
	}

	public void setBlockWithOffset(int x, int y, int z) {
		if (this.chance == 0) {
			this.setBlock(x + this.startX, y + this.startY, z + this.startZ, this.blockState, this.meta);
			return;
		}

		if (this.random.nextInt(this.chance) == 0) {
			this.placeBlockAtCurrentPosition(this.worldObj, this.extraBlockState, this.extraMeta, x + this.startX, y + this.startY, z + this.startZ, this.dungeonBoundingBox);
		} else {
			this.placeBlockAtCurrentPosition(this.worldObj, this.blockState, this.meta, x + this.startX, y + this.startY, z + this.startZ, this.dungeonBoundingBox);
		}
	}

	public void setBlock(int x, int y, int z) {
		if (this.chance == 0) {
			this.setBlock(x, y, z, this.blockState, this.meta);
			return;
		}

		if (this.random.nextInt(this.chance) == 0) {
			this.placeBlockAtCurrentPosition(this.worldObj, this.extraBlockState, this.extraMeta, x, y, z, this.dungeonBoundingBox);
		} else {
			this.placeBlockAtCurrentPosition(this.worldObj, this.blockState, this.meta, x, y, z, this.dungeonBoundingBox);
		}
	}

	public boolean spawnEntity(Entity entity, int structureX, int structureY, int structureZ) {
		int posX = this.getActualX(structureX, structureZ);
		int posY = this.getActualY(structureY);
		int posZ = this.getActualZ(structureX, structureZ);

		if (this.dungeonBoundingBox.isVecInside(posX, posY, posZ)) {
			entity.setLocationAndAngles((double) posX + 0.5D, (double) posY + 0.5D, (double) posZ + 0.5D, 0.0F, 0.0F);

			if (entity instanceof EntityLivingBase) {
				EntityLivingBase livingEntity = ((EntityLivingBase) entity);

				livingEntity.heal(livingEntity.getMaxHealth());
			}

			if (!this.worldObj.isRemote) //Not taking chances ~Kino
			{
				this.worldObj.spawnEntityInWorld(entity);
			}

			return true;
		}

		return false;
	}

	public int getActualX(int structureX, int structureZ) {
		return this.getXWithOffset(structureX + this.startX, structureZ + this.startZ);
	}

	public int getActualY(int structureY) {
		return this.getYWithOffset(structureY + this.startY);
	}

	public int getActualZ(int structureX, int structureZ) {
		return this.getZWithOffset(structureX + this.startX, structureZ + this.startZ);
	}

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
		this.worldObj = worldIn;
		this.random = randomIn;
		this.dungeonBoundingBox = structureBoundingBoxIn;

		return this.generate();
	}

	public abstract boolean generate();

	@Override
	protected void func_143012_a(NBTTagCompound tagCompound) {

	}

	@Override
	protected void func_143011_b(NBTTagCompound tagCompound) {

	}

}