package com.legacy.aether.world.gen.components;

import java.util.Random;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.natural.BlockAercloud;
import com.legacy.aether.blocks.util.EnumCloudType;
import com.legacy.aether.world.gen.AetherStructure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class ComponentPinkAercloud extends AetherStructure
{

	private NBTTagCompound data = new NBTTagCompound();

	private int xTendency, zTendency;

    public ComponentPinkAercloud()
    {

    }

	public ComponentPinkAercloud(Random random, int chunkX, int chunkY, int chunkZ)
	{
        this.setCoordBaseMode(EnumFacing.NORTH);

        this.xTendency = random.nextInt(3) - 1;
        this.zTendency = random.nextInt(3) - 1;

        this.boundingBox = new StructureBoundingBox(chunkX, chunkY, chunkZ, chunkX + 100, chunkY + 10, chunkZ + 100);
	}

	@Override
	public boolean generate() 
	{
		this.replaceAir = true;

		if (!this.data.hasKey("initialized"))
		{
			NBTTagCompound icd;

	        for (int n = 0; n < 1; ++n)
	        {
	        	icd = new NBTTagCompound();

	        	int xOffset = this.random.nextInt(3) - 1;
	        	int yOffset = this.random.nextInt(3) - 1;
	        	int zOffset = this.random.nextInt(3) - 1;

	        	icd.setInteger("xOffset", xOffset);

	            if (this.random.nextBoolean())
	            {
		        	icd.setInteger("yOffset", yOffset);
	            }

	        	icd.setInteger("zOffset", zOffset);

	        	int xMax = this.random.nextInt(4) + 3;
	        	int yMax = this.random.nextInt(1) + 2;
	        	int zMax = this.random.nextInt(4) + 3;

	        	icd.setInteger("xMax", xMax);
	        	icd.setInteger("yMax", yMax);
	        	icd.setInteger("zMax", zMax);
	        	icd.setInteger("shapeOffset", this.random.nextInt(2));

	            this.data.setTag("ICD_" + n, icd);
	        }

	        this.data.setBoolean("initialized", true);
		}

		int x = 0, y = 0, z = 0;
		NBTTagCompound icd;

		this.setStructureOffset(20, 2, 20);

        for (int n = 0; n < 4; ++n)
        {
        	icd = this.data.getCompoundTag("ICD_" + n);

        	x += icd.getInteger("xOffset") + this.xTendency;

        	if (icd.hasKey("yOffset"))
        	{
        		y += icd.getInteger("yOffset");
        	}

        	z += icd.getInteger("zOffset") + this.zTendency;

        	int xMax = icd.getInteger("xMax");
        	int yMax = icd.getInteger("yMax");
        	int zMax = icd.getInteger("zMax");
        	int shapeOffset = icd.getInteger("shapeOffset");

        	for (int x1 = x; x1 < x + xMax; ++x1)
            {
                for (int y1 = y; y1 < y + yMax; ++y1)
                {
                    for (int z1 = z; z1 < z + zMax; ++z1)
                    {
                        if (this.getBlockStateWithOffset(x1, y1, z1).getBlock() == Blocks.AIR)
                        {
                        	if (Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 4 + shapeOffset)
                        	{
                        		this.setBlockWithOffset(x1, y1, z1, BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.cloud_type, EnumCloudType.Pink));
                        	}
                        }
                    }
                }
            }
        }

		return true;
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) 
	{
        tagCompound.setInteger("xTendency", this.xTendency);
        tagCompound.setInteger("zTendency", this.zTendency);
		tagCompound.setTag("cloudData", this.data);
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) 
	{
        this.xTendency = tagCompound.getInteger("zTendency");
        this.zTendency = tagCompound.getInteger("zTendency");
		this.data = tagCompound.getCompoundTag("cloudData");
	}

}