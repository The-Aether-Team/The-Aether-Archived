package com.legacy.aether.server.blocks.decorative;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;

import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public class BlockAmbrosiumTorch extends BlockTorch
{

    public BlockAmbrosiumTorch()
    {
        super();
        this.setTickRandomly(true);
        this.setLightLevel(0.9375F);
        this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(AetherCreativeTabs.blocks);
    }

}