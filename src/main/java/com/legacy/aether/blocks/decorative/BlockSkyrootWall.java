package com.legacy.aether.blocks.decorative;

import com.legacy.aether.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSkyrootWall extends BlockAetherWall
{
	public static final PropertyBool PROPERTY_GENERATED = PropertyBool.create("is_generated");

	public BlockSkyrootWall(IBlockState state)
	{
		super(state);
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(PROPERTY_GENERATED, false));
	}

	// If an Aether log wall that has been generated by the world is destroyed, it drops a log, not a wall piece.
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (state.getValue(PROPERTY_GENERATED))
		{
			return Item.getItemFromBlock(BlocksAether.aether_log);
		}
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		if (state.getValue(PROPERTY_GENERATED))
		{
			return 1;
		}

		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		IBlockState neighborState = world.getBlockState(pos.offset(side.getOpposite()));

		return state != neighborState && super.shouldSideBeRendered(state, world, pos, side);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {UP, NORTH, EAST, WEST, SOUTH, PROPERTY_GENERATED});
	}

	@Override
	public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return true;
	}
}
