package com.legacy.aether.blocks.natural.enchanted;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.natural.BlockAetherDirt;

public class BlockEnchantedAetherGrass extends Block
{
    public static final PropertyBool SNOWY = PropertyBool.create("snowy");

	public BlockEnchantedAetherGrass()
	{
		super(Material.GRASS);

		this.setHardness(0.2F);
		this.setTickRandomly(true);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(this.getDefaultState().withProperty(SNOWY, Boolean.FALSE));
	}

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return state.withProperty(SNOWY, block == Blocks.SNOW || block == Blocks.SNOW_LAYER);
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

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, SNOWY);
    }
}