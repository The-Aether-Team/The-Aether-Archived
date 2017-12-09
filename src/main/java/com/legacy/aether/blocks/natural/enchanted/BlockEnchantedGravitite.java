package com.legacy.aether.blocks.natural.enchanted;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import com.legacy.aether.blocks.util.BlockFloating;

public class BlockEnchantedGravitite extends BlockFloating 
{

	public BlockEnchantedGravitite()
	{
		super(Material.IRON, true);

		this.setHardness(5F);
		this.setSoundType(SoundType.METAL);
	}

}