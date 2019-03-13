package com.legacy.aether.block.natural;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;

public class BlockAetherDirt extends Block
{

	public static final BooleanProperty DOUBLE_DROP = BooleanProperty.create("double_drop");

	public BlockAetherDirt()
	{
		super(Properties.create(Material.GROUND).hardnessAndResistance(0.2F).sound(SoundType.GROUND));

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