package com.legacy.aether.block.natural;

import java.util.Random;

import com.legacy.aether.block.BlocksAether;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockAetherGrass extends Block
{

	public static final BooleanProperty DOUBLE_DROP = BooleanProperty.create("double_drop");

	public BlockAetherGrass()
	{
		super(Properties.create(Material.GRASS).needsRandomTick().hardnessAndResistance(0.2F).sound(SoundType.PLANT));

		this.setDefaultState((IBlockState) this.getDefaultState().with(DOUBLE_DROP, true));
	}

	@Override
	public void tick(IBlockState state, World worldIn, BlockPos pos, Random random)
	{
		if (!worldIn.isRemote)
		{
			if (!worldIn.isAreaLoaded(pos, 3))
			{
				return;
			}

			if (!canGrassExist(worldIn, pos))
			{
				worldIn.setBlockState(pos, BlocksAether.AETHER_DIRT.getDefaultState());
			}
			else if (worldIn.getLight(pos.up()) >= 9)
			{
				for (int i = 0; i < 4; ++i)
				{
					BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);

					if (!worldIn.isBlockPresent(blockpos))
					{
						return;
					}

					if (worldIn.getBlockState(blockpos).getBlock() == BlocksAether.AETHER_DIRT && canGrassGrow(worldIn, blockpos))
					{
						worldIn.setBlockState(blockpos, (IBlockState) this.getDefaultState().with(DOUBLE_DROP, (Boolean) worldIn.getBlockState(blockpos).get(BlockAetherDirt.DOUBLE_DROP)));
					}
				}
			}
		}
	}

	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context)
	{
		return (IBlockState) super.getStateForPlacement(context).with(DOUBLE_DROP, false);
	}

	@Override
	protected void fillStateContainer(Builder<Block, IBlockState> builder)
	{
		builder.add(new IProperty[] { DOUBLE_DROP });
	}

	@Override
	public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune)
	{
		return BlocksAether.AETHER_DIRT;
	}

	private static boolean canGrassExist(IWorldReaderBase world, BlockPos pos)
	{
		BlockPos blockpos = pos.up();

		return world.getLight(blockpos) >= 4 || world.getBlockState(blockpos).getOpacity(world, blockpos) < world.getMaxLightLevel();
	}

	private static boolean canGrassGrow(IWorldReaderBase world, BlockPos pos)
	{
		BlockPos blockpos = pos.up();

		return world.getLight(blockpos) >= 4 && world.getBlockState(blockpos).getOpacity(world, blockpos) < world.getMaxLightLevel() && !world.getFluidState(blockpos).isTagged(FluidTags.WATER);
	}

}