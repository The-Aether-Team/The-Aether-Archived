package com.legacy.aether.block.natural;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;

public class BlockHolystone extends Block
{

	public static final BooleanProperty DOUBLE_DROP = BooleanProperty.create("double_drop");

	public BlockHolystone()
	{
		super(Properties.create(Material.ROCK).hardnessAndResistance(0.5F).sound(SoundType.STONE));

		this.setDefaultState((IBlockState) this.getDefaultState().with(DOUBLE_DROP, true));
	}

	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context)
	{
		return (IBlockState) super.getStateForPlacement(context).with(DOUBLE_DROP, false);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder)
	{
		builder.add(new IProperty[] { DOUBLE_DROP });
	}

}