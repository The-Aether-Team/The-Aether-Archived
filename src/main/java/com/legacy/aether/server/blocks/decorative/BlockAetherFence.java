package com.legacy.aether.server.blocks.decorative;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockAetherFence extends BlockFence
{

	public BlockAetherFence()
	{
		super(Material.WOOD, Material.WOOD.getMaterialMapColor());
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.WOOD);
	}

}