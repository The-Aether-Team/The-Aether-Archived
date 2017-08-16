package com.legacy.aether.common.blocks.natural.ore;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import com.legacy.aether.common.items.ItemsAether;

public class BlockZaniteOre extends Block
{

	public BlockZaniteOre()
	{
		super(Material.ROCK);

		this.setHardness(3F);
		this.setSoundType(SoundType.STONE);
	}

	@Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemsAether.zanite_gemstone;
    }

}