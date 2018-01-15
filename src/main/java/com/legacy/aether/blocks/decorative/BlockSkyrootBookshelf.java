package com.legacy.aether.blocks.decorative;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSkyrootBookshelf extends Block
{
    public BlockSkyrootBookshelf()
    {
        super(Material.WOOD);
        
        this.setHardness(2F);
		this.setResistance(5F);
		this.setSoundType(SoundType.WOOD);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 3;
    }
    
    @Override
	public float getEnchantPowerBonus(World world, BlockPos pos)
	{
		return 1;
	}

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.BOOK;
    }
}