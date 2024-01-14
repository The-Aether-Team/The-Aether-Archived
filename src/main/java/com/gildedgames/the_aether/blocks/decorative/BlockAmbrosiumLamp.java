package com.gildedgames.the_aether.blocks.decorative;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
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
