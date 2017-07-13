package com.legacy.aether.common.blocks.natural.enchanted;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.blocks.natural.BlockAetherDirt;

public class BlockEnchantedAetherGrass extends Block
{

	public BlockEnchantedAetherGrass()
	{
		super(Material.GRASS);

		this.setHardness(0.2F);
		this.setTickRandomly(true);
		this.setSoundType(SoundType.PLANT);
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlocksAether.aether_dirt);
    }

	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (!world.isRemote)
        {
            if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos) > 2)
            {
            	world.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.double_drop, false));
            }
        }
    }

}