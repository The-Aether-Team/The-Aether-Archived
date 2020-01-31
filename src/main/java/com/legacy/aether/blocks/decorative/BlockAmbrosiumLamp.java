package com.legacy.aether.blocks.decorative;

import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockAmbrosiumLamp extends Block
{
	public BlockAmbrosiumLamp()
	{
		super(Material.GLASS);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setHardness(0.3F);
		this.setLightLevel(1.0F);
		this.setSoundType(SoundType.GLASS);
	}
}
