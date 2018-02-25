package com.legacy.aether.world.gen.components;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.legacy.aether.blocks.util.EnumCloudType;
import com.legacy.aether.world.gen.AetherGenUtils;
import com.legacy.aether.world.gen.AetherStructure;

public class ComponentGoldenAercloud extends AetherStructure
{

	private int xTendency, zTendency;

    public ComponentGoldenAercloud()
    {

    }

	public ComponentGoldenAercloud(int chunkX, int chunkY, int chunkZ, int xTendency, int zTendency)
	{
        this.setCoordBaseMode(EnumFacing.NORTH);

        this.xTendency = xTendency;
        this.zTendency = zTendency;
        this.boundingBox = new StructureBoundingBox(chunkX, chunkY, chunkZ, chunkX + 100, chunkY + 10, chunkZ + 100);
	}

	@Override
	public boolean generate() 
	{
		this.replaceAir = true;

		AetherGenUtils.generateClouds(this, EnumCloudType.Golden, false, 4, 15, 5, 15, this.xTendency, this.zTendency);

		return true;
	}

}