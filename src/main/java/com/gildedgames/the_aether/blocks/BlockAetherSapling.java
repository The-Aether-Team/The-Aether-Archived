package com.gildedgames.the_aether.blocks;

import java.util.Random;

import com.gildedgames.the_aether.blocks.natural.BlockAetherFlower;

import com.gildedgames.the_aether.blocks.natural.BlockAetherLeaves;
import com.gildedgames.the_aether.blocks.util.EnumLeafType;
import com.gildedgames.the_aether.world.biome.decoration.*;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BlockAetherSapling extends BlockAetherFlower implements IGrowable
{

	public final EnumLeafType leafType;
	public final WorldGenerator worldGen;

	public static AetherGenSkyrootTree green_tree = null;
	public static AetherGenLargeTree green_large_tree = null;
	public static AetherGenMassiveTree green_massive_tree = null;
	public static AetherGenOakTree golden_oak_tree = null;
	public static AetherGenSkyrootTree blue_tree = null;
	public static AetherGenMassiveTree blue_massive_tree = null;
	public static AetherGenMassiveTree dark_blue_massive_tree = null;
	public static AetherGenFruitTree purple_fruit_tree = null;

	public BlockAetherSapling(EnumLeafType leafType)
	{
		super();
		float f = 0.4F;
		this.FLOWER_AABB = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.leafType = leafType;
		this.worldGen = null;
	}

	//legacy constructor so Lost Aether stays compatible
	public BlockAetherSapling(WorldGenerator worldGen)
	{
		super();
		float f = 0.4F;
		this.FLOWER_AABB = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.leafType = EnumLeafType.Green;
		this.worldGen = worldGen;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (!world.isRemote)
		{
			super.updateTick(world, pos, state, random);

			if (!world.isAreaLoaded(pos, 1)) return;
			if (world.getLight(pos.up()) >= 9 && random.nextInt(30) == 0)
			{
				this.growTree(world, pos, random);
			}
		}
	}

	public void growTree(World world, BlockPos pos, Random rand)
	{
		world.setBlockState(pos, Blocks.AIR.getDefaultState());

		if (!this.getWorldGen(rand).generate(world, world.rand, pos))
		{
			world.setBlockState(pos, this.getDefaultState());
		}
	}

	public WorldGenerator getWorldGen(Random rand)
	{
		//If the legacy constructor with a worldgen was used, just return that.
		if (this.worldGen != null) return this.worldGen;

		//create these objects lazily just in case doing it elsewhere is too early
		if (green_tree == null)
		{
			green_tree = new AetherGenSkyrootTree(true);
			green_large_tree = new AetherGenLargeTree(BlocksAether.aether_log.getDefaultState(), BlocksAether.aether_leaves.getDefaultState(), true);
			green_massive_tree = new AetherGenMassiveTree(BlocksAether.aether_leaves.getDefaultState(), 20, false, true);
			golden_oak_tree = new AetherGenOakTree(true);
			blue_tree = new AetherGenSkyrootTree(BlocksAether.aether_leaves.getDefaultState().withProperty(BlockAetherLeaves.leaf_type, EnumLeafType.Blue), true);
			blue_massive_tree = new AetherGenMassiveTree(BlocksAether.aether_leaves.getDefaultState().withProperty(BlockAetherLeaves.leaf_type, EnumLeafType.Blue), 8, false, true);
			dark_blue_massive_tree = new AetherGenMassiveTree(BlocksAether.aether_leaves.getDefaultState().withProperty(BlockAetherLeaves.leaf_type, EnumLeafType.DarkBlue), 35, true, true);
			purple_fruit_tree = new AetherGenFruitTree(BlocksAether.aether_leaves_2.getDefaultState().withProperty(BlockAetherLeaves.leaf_type, EnumLeafType.Purple),
				BlocksAether.aether_leaves_2.getDefaultState().withProperty(BlockAetherLeaves.leaf_type, EnumLeafType.Purple), 50, 5, true, true);
		}

		switch (leafType)
		{
			case Green:
				int ratio = rand.nextInt(100);
				if (ratio < 10) return green_massive_tree;
				if (ratio < 25) return green_large_tree;
				else return green_tree;

			case Golden:
				return golden_oak_tree;

			case Blue:
				if (rand.nextInt(4) == 0) return blue_massive_tree;
				else return blue_tree;

			case DarkBlue:
				return dark_blue_massive_tree;

			case Purple:
				return purple_fruit_tree;
		}

		return green_tree;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		if (worldIn.rand.nextFloat() < 0.45D)
        {
            this.growTree(worldIn, pos, rand);
        }
	}
}