package com.gildedgames.the_aether.world.biome.decoration;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.natural.BlockAetherPlant;
import com.gildedgames.the_aether.blocks.natural.BlockTallAetherGrass;
import com.gildedgames.the_aether.blocks.util.EnumTallGrassType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class AetherGenTallGrass extends WorldGenerator
{
	private static IBlockState[] states;

	public AetherGenTallGrass()
	{
		if (states == null)
		{
			states = new IBlockState[EnumTallGrassType.getMaxValues()];

			for (int i = 0; i < EnumTallGrassType.getMaxValues(); i++)
			{
				states[i] = BlocksAether.aether_tall_grass.getDefaultState().withProperty(BlockTallAetherGrass.variant, EnumTallGrassType.getType(i));
			}
		}
	}

	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
		{
			position = position.down();
		}

		for (int i = 0; i < 48; ++i)
		{
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
			IBlockState state = states[rand.nextInt(EnumTallGrassType.getMaxValues())];

			if (worldIn.isAirBlock(blockpos) &&
				((BlockAetherPlant) BlocksAether.aether_tall_grass).isSuitableSoilBlock(worldIn.getBlockState(blockpos.down())))
			{
				worldIn.setBlockState(blockpos, state, 2);
			}
		}

		return true;
	}
}
