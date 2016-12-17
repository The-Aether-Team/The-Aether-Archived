package com.legacy.aether.server.blocks.natural.ore;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.legacy.aether.server.items.ItemsAether;

public class BlockZaniteOre extends Block
{

	public BlockZaniteOre()
	{
		super(Material.ROCK);

		this.setHardness(3F);
		this.setSoundType(SoundType.STONE);
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemsAether.zanite_gemstone;
    }

}