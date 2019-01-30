package com.legacy.aether.world.gen.components;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.natural.BlockAercloud;
import com.legacy.aether.blocks.util.EnumCloudType;
import com.legacy.aether.world.gen.AetherStructure;

public class ComponentLargeColdAercloud extends AetherStructure
{

	private NBTTagList data = new NBTTagList();

	private int xTendency, zTendency;

    public ComponentLargeColdAercloud()
    {

    }

	public ComponentLargeColdAercloud(Random random, int chunkX, int chunkY, int chunkZ)
	{
        this.setCoordBaseMode(EnumFacing.NORTH);

        this.xTendency = random.nextInt(3) - 1;
        this.zTendency = random.nextInt(3) - 1;

        this.boundingBox = new StructureBoundingBox(chunkX, 0, chunkZ, chunkX + 100, 255, chunkZ + 100);
	}

	@Override
	public boolean generate() 
	{
		this.replaceAir = true;

		if (this.data.hasNoTags())
		{
			NBTTagCompound icd;

	        for (int n = 0; n < 64; ++n)
	        {
	        	icd = new NBTTagCompound();

	        	int xOffset = this.random.nextInt(3);
	        	int yOffset = this.random.nextInt(3);
	        	int zOffset = this.random.nextInt(3);

	        	icd.setByte("xOffset", (byte) xOffset);

	            if (this.random.nextInt(10) == 0)
	            {
		        	icd.setByte("yOffset", (byte) yOffset);
	            }

	        	icd.setByte("zOffset", (byte) zOffset);

	        	int xMax = this.random.nextInt(4) + 9;
	        	int yMax = this.random.nextInt(1) + 2;
	        	int zMax = this.random.nextInt(4) + 9;

	        	icd.setByte("xMax", (byte) xMax);
	        	icd.setByte("yMax", (byte) yMax);
	        	icd.setByte("zMax", (byte) zMax);
	        	icd.setByte("shapeOffset", (byte) this.random.nextInt(2));

	        	this.data.appendTag(icd);
	        }
		}

		int x = 0, y = 0, z = 0;
		NBTTagCompound icd;

        for (int n = 0; n < 64; ++n)
        {
        	icd = this.data.getCompoundTagAt(n);

        	x += icd.getByte("xOffset") - 1 + this.xTendency;

        	if (icd.hasKey("yOffset"))
        	{
        		y += icd.getByte("yOffset") - 1;
        	}

        	z += icd.getByte("zOffset") - 1 + this.zTendency;

        	int xMax = icd.getByte("xMax");
        	int yMax = icd.getByte("yMax");
        	int zMax = icd.getByte("zMax");
        	int shapeOffset = icd.getByte("shapeOffset");

        	for (int x1 = x; x1 < x + xMax; ++x1)
            {
                for (int y1 = y; y1 < y + yMax; ++y1)
                {
                    for (int z1 = z; z1 < z + zMax; ++z1)
                    {
                        if (this.getBlockState(x1 + 50, y1 + 4, z1 + 50).getBlock() == Blocks.AIR)
                        {
                        	if (Math.abs(x1 - x) + Math.abs(y1 - y) + Math.abs(z1 - z) < 12 + shapeOffset)
                        	{
                        		this.setBlock(x1 + 50, y1 + 4, z1 + 50, BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.cloud_type, EnumCloudType.Cold));
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
		tagCompound.setTag("cloudData", this.data);
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_) 
	{
		this.data = tagCompound.getTagList("cloudData", 10);
	}

}