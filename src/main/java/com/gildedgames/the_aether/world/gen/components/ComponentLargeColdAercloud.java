package com.gildedgames.the_aether.world.gen.components;

import java.util.Random;

import com.gildedgames.the_aether.world.gen.AetherStructure;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class ComponentLargeColdAercloud extends AetherStructure {

	private NBTTagCompound data = new NBTTagCompound();

	private int xTendency, zTendency;

	public ComponentLargeColdAercloud() {

	}

	public ComponentLargeColdAercloud(Random random, int chunkX, int chunkY, int chunkZ) {
		this.coordBaseMode = 0;

		this.xTendency = random.nextInt(3) - 1;
		this.zTendency = random.nextInt(3) - 1;

		this.boundingBox = new StructureBoundingBox(chunkX, 0, chunkZ, chunkX + 100, 255, chunkZ + 100);
	}

	@Override
	public boolean generate() {
		this.replaceAir = true;

		if (!this.data.hasKey("initialized")) {
			NBTTagCompound icd;

			for (int n = 0; n < 64; ++n) {
				icd = new NBTTagCompound();

				byte xOffset = (byte) (this.random.nextInt(3) - 1);
				byte yOffset = (byte) (this.random.nextInt(3) - 1);
				byte zOffset = (byte) (this.random.nextInt(3) - 1);

				icd.setByte("xOffset", xOffset);

				if (this.random.nextInt(10) == 0) {
					icd.setByte("yOffset", yOffset);
				}

				icd.setByte("zOffset", zOffset);

				byte xMax = (byte) (this.random.nextInt(4) + 9);
				byte yMax = (byte) (this.random.nextInt(1) + 2);
				byte zMax = (byte) (this.random.nextInt(4) + 9);

				icd.setByte("xMax", xMax);
				icd.setByte("yMax", yMax);
				icd.setByte("zMax", zMax);
				icd.setByte("shapeOffset", (byte) this.random.nextInt(2));

				this.data.setTag("ICD_" + n, icd);
			}

			this.data.setByte("initialized", (byte) 1);
		}

		int x = 0, y = 0, z = 0;
		NBTTagCompound icd;

		this.setStructureOffset(50, 4, 100);

		for (int n = 0; n < 64; ++n) {
			icd = this.data.getCompoundTag("ICD_" + n);

			x += icd.getByte("xOffset") + this.xTendency;

			if (icd.hasKey("yOffset")) {
				y += icd.getByte("yOffset");
			}

			z += icd.getByte("zOffset") + this.zTendency;

			int xMax = icd.getByte("xMax");
			int yMax = icd.getByte("yMax");
			int zMax = icd.getByte("zMax");
			int shapeOffset = icd.getByte("shapeOffset");

			for (int x1 = x; x1 < x + xMax; ++x1) {
				for (int y1 = y; y1 < y + yMax; ++y1) {
					for (int z1 = z; z1 < z + zMax; ++z1) {
						if (this.getBlockStateWithOffset(x1, y1, z1) == Blocks.air) {
							if (Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 12 + shapeOffset) {
								this.setBlockWithOffset(x1, y1, z1, BlocksAether.aercloud, 0);
							}
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	protected void func_143012_a(NBTTagCompound tagCompound) {
		tagCompound.setTag("cloudData", this.data);
	}

	@Override
	protected void func_143011_b(NBTTagCompound tagCompound) {
		this.data = tagCompound.getCompoundTag("cloudData");
	}

}