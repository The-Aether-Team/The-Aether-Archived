package com.legacy.aether.world.gen;

import com.legacy.aether.block.BlocksAether;

import net.minecraft.world.gen.ChunkGenSettings;

public class AetherGenSettings extends ChunkGenSettings
{

	public AetherGenSettings()
	{
		this.setDefautBlock(BlocksAether.HOLYSTONE.getDefaultState());
	}

}